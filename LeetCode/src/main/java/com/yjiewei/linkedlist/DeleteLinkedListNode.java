package com.yjiewei.linkedlist;

/**
 * 237. 删除链表中的节点
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 * 请编写一个函数，用于 删除单链表中某个特定节点 。在设计函数时需要注意，你无法访问链表的头节点head ，只能直接访问 要被删除的节点 。
 *
 * 题目数据保证需要删除的节点 不是末尾节点 。
 * 思维被禁锢了，不要当前节点，也可以将当前节点替换成下一个节点啊。
 * @author yjiewei
 * @date 2021/11/2
 */
public class DeleteLinkedListNode {


    public static void main(String[] args) {
        ListNode node0 = new ListNode(5);
        ListNode node1 = new ListNode(4);
        ListNode node2 = new ListNode(8);

        // 0-->1-->2
        node0.next = node1;
        node1.next = node2;
        deleteNode(node0);
        while (node0 != null) {
            System.out.println(node0.val);
            node0 = node0.next;
        }
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void deleteNode(ListNode node) {

        // 直接替换为下一个节点值，下一个节点去掉
        node.val = node.next.val;
        node.next = node.next.next;
    }
}


