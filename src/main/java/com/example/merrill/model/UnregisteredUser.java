package com.example.merrill.model;

public class UnregisteredUser extends User{
    private String registrationId;
    private String registrationIdGeneratedTime;

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationIdGeneratedTime() {
        return registrationIdGeneratedTime;
    }

    public void setRegistrationIdGeneratedTime(String registrationIdGeneratedTime) {
        this.registrationIdGeneratedTime = registrationIdGeneratedTime;
    }
}
