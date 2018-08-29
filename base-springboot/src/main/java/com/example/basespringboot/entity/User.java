package com.example.basespringboot.entity;

import java.util.Arrays;

public class User {
    public String name;

    public int age;

    public String[] sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getSex() {
        return sex;
    }

    public void setSex(String[] sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + Arrays.toString(sex) +
                '}';
    }
}
