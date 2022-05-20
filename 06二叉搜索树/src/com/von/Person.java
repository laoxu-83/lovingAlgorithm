package com.von;

/**
 * @author xwx
 * @create 2020/6/1 16:21
 **/
public class Person implements Comparable<Person>{
    private int age;


    public Person(int age) {
        this.age = age;

    }

    @Override
    public int compareTo(Person o) {
        return this.age-o.age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "{" +
                 + age +
                '}';
}
    //    @Override
//    public int compareTo(Object o) {
//        Person person = (Person)o;
////        if(person.age < this.age){
////            return -1;
////        } if(person.age < this.age) {
////            return 1;
////        } else {
////            return 0;
////        }
//        return this.age-person.age;
//    }


}
