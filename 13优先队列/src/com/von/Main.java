package com.von;

import com.von.queue.PriorityQueue;

/**
 * @author xwx
 * @create 2020/6/12 16:02
 **/
public class Main {

    public static void main(String[] args) {
        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.enQueue(new Person("Jack", 2));
        queue.enQueue(new Person("Rose", 10));
        queue.enQueue(new Person("Jake", 5));
        queue.enQueue(new Person("James", 15));

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }
}
