package com.jarvis.rxjavaexamples.model.user;
/**
 * Created by Sachin
 */

public class User {

    String name;
    String gender;
    String email;
    String address;


    public User(String name, String gender, String email , String address) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address=address;
    }

    public User(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
