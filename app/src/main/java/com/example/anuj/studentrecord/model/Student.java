package com.example.anuj.studentrecord.model;


import org.greenrobot.greendao.annotation.*;

import java.util.List;

@Entity
public class Student {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;



    @Generated(hash = 808380004)
    public Student(Long id, String firstName, String lastName, Integer age,
            String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
