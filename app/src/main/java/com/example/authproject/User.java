package com.example.authproject;

public class User {

    public String fname, age, email, vid;

    public User(String fname, String age, String email, String vid) {
        this.fname = fname;
        this.age = age;
        this.email = email;
        this.vid = vid;
    }

    public User(){

    }
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return vid;
    }

    public void setId(String vid) {
        this.vid = vid;
    }

    @Override
    public String toString() {
        return "User{" +
                "fname='" + fname + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", vid='" + vid + '\'' +
                '}';
    }
}


