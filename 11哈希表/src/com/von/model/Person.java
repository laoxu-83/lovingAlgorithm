package com.von.model;

/**
 * @author xwx
 * @create 2020/6/8 17:29
 **/
public class Person {
    private int age;
    private float height;
    private String name;

    public Person(int age, float height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Person)) return false;

        //比较成员变量
        Person person = (Person) obj;
        return person.age == this.age && person.height == this.height
                && (person.name == null ? name == null : person.name.equals(name));
    }

    @Override
    public int hashCode() {
        int hashCode = Integer.hashCode(age);
        hashCode = hashCode*31 + Float.hashCode(height);
        hashCode = hashCode*31 + (name != null ? name.hashCode():0);
        return hashCode;
    }
}
