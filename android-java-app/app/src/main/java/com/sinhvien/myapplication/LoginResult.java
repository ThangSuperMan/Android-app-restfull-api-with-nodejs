package com.sinhvien.myapplication;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    // Convert the key name json to the name variable of java
    @SerializedName("name")
    private String name;

    // Convert the key email json to the email variable of java
    @SerializedName("email")
    private String email;

}
