package com.example.bmi_calculator;

import java.io.Serializable;

public class Person implements Serializable {

    private String fname;
    private String lname;
    private  float height;
    private float weight;
    private int age;
    private String gender;

    public Person(String fname, String lname, float height,  float weight, int age, String gender){
        this.fname = fname;
        this.lname = lname;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
