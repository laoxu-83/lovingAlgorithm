package com.xwx;

import com.xwx.union.GenericUnion;

/**
 * @author xwx
 * @create 2020/6/15 14:15
 **/
public class Main {

    public static void main(String[] args) {
        GenericUnion<Student> uf = new GenericUnion<>();
		Student stu1 = new Student(1, "jack");
		Student stu2 = new Student(2, "rose");
		Student stu3 = new Student(3, "jack");
		Student stu4 = new Student(4, "rose");
		uf.makeSet(stu1);
		uf.makeSet(stu2);
		uf.makeSet(stu3);
		uf.makeSet(stu4);

		uf.union(stu1, stu2);
		uf.union(stu3, stu4);

		uf.union(stu1, stu4);
    }
}
