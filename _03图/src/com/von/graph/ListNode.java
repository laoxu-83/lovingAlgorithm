package com.von.graph;


import org.w3c.dom.ls.LSException;

import java.util.List;

public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
  // 4  1  3  2
class Solution {
    public static ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(-5001);
        dummy.next = head;
        ListNode last = head;
        if(head == null || head.next == null)return head;
        //每次拿一个未排序的进行插入
        for(ListNode p = head.next; p != null ; p = p.next){
            for(ListNode q = head,m = dummy;q != head || q.next != p ; q = q.next,m = m.next){
                if(p.val <= q.val){
                    //将k插入
                    m.next = p;
                    p.next = q;
                    break;
                }
            }
            last = p;
        }
        last.next = null;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(3);
        ListNode n3 = new ListNode(2);
        head.next = n1;
        n1.next = n2;
        n2.next = n3;
        insertionSortList(head);

    }
}