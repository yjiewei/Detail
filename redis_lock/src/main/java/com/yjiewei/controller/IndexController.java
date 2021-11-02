package com.yjiewei.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author yjiewei
 * @date 2021/8/5
 */
@RestController
public class IndexController {

    @Resource
    private Redisson redisson;

    @Resource
    private StringRedisTemplate stringRedisTemplate; // 居然根据名字来找的，不是根据类型。。。

    /**
     * 代码有什么问题？
     *      1.线程安全问题，多个线程同时访问就可能出现错误，用synchronized可以解决但是性能不好
     *          synchronized在高并发情况下还是有bug出现，会出现超卖，可以用jmeter压测
     *      2.设置redis锁解决分布式场景之后，超时时间设置10s合理吗？适合场景问题？如果10s中之内没有处理完，处理到一半呢？
     *          15s--10s后释放锁--还需要5s，5s后释放了其他人的锁
     *          8s--5s后我的锁被人释放了，其他线程又来了
     *          循环下去，锁的是别人....这不就完全乱套了，这锁完全没用啊
     *        解决方法：你不是可能存在释放别人的锁的情况吗？那就设置识别号，识别到只能是自己的才能被释放
     *        这只是解决了释放别人的锁的问题，你自己没有执行完就已经超时的问题呢？
     *        答案：开启子线程定时器来延长超时时间咯，子线程每隔一段时间就去查看是否完成，没完成就加时，那这个一段时间要多长呢？
     *             三分之一过期时间，其他人的实践经验。
     *        所以：我们现在又要造轮子了吗？是否有其他人已经考虑过这个问题并做开源了呢？
     *        那肯定有啊，不然我写这个干吗。redisson，比jedis强大，专对分布式
     *
     *      3.redisson
     *          大概阐述一下这个锁的操作：
     *          当一个redisson线程过来获取到锁时，后台会有其他线程去检查是否还持有锁，
     *          还持有说明还没执行结束，就会继续延长锁的时间，大概10s去轮询。（三分之一）
     *          另外一个线程过来，如果没有获取锁成功，就会while自旋尝试加锁。
     *          clientId他在底层实现了。
     *
     *          3.1如果使用的是Redis主从架构呢，主节点宕了，从节点怎么处理？但这是锁还没有被同步过去，其他线程就过来访问了呢？
     *          3.2另外如何提升性能呢？
     *              - 商品库存分段存储，key不一样，每个段的数量越小性能不就越高嘛，而且锁定不同的key值
     *
     * @return
     */
    @RequestMapping("/deduct_stock")
    public String deductStock() {

        // 0.标识号
        String clientID = UUID.randomUUID().toString();

        // 1.这个相当于一把锁，控制只能一个人来
        String lockKey = "product001";
        RLock lock = redisson.getLock(lockKey); // 3.1
        long startMillis = 0;
        long endMillis = 0;
        try{
/*
            // 2.获取锁
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "yjiewei");// jedis.setnx(key, value);
            // 3.如果突发宕机，锁没有释放掉怎么办，key过期处理(10s)，但是如果在获取锁之后就出问题呢，这一步也没有成功，大招：二合一
            stringRedisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);
*/

            // 2-3
/*          Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientID, 10, TimeUnit.SECONDS);
            if (!result){
                return "error";
            }
*/

            // 3.1 解决过期时间内还未完成操作的问题
            lock.lock(30, TimeUnit.SECONDS); // 先拿锁，再设置超时时间
            startMillis = System.currentTimeMillis();

            // 4.真正操作商品库存
            synchronized (this){
                int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock")); // jedis.get("stock");
                if (stock > 0){
                    int realStock = stock - 1;
                    stringRedisTemplate.opsForValue().set("stock", realStock + ""); // jedis.set(key, value);
                    System.out.println("扣减成功，剩余库存：" + realStock);
                }else {
                    System.out.println("扣减失败，库存不足！");
                }
            }
            endMillis = System.currentTimeMillis();
        }finally {
            lock.unlock(); // 释放锁
/*
            if (clientID.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                // 5.释放锁，放在finally块中防止没有释放锁导致死锁问题
                stringRedisTemplate.delete(lockKey);
            }
*/
            System.out.println("200个库存 同步时，耗时: " + (endMillis - startMillis));
        }
        return "end";
    }
}
