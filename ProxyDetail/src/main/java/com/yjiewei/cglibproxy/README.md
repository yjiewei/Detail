1. 定义一个类；
2. 自定义 MethodInterceptor 并重写 intercept 方法，intercept 用于拦截增强被代理类的方法，和 JDK 动态代理中的 invoke 方法类似；
3. 通过 Enhancer 类的 create()创建代理类；