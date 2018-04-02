package com.example.pc.evergreen.beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by pc on 2018/3/9.
 */

public class Manager extends BmobObject {
    private String phone;
    private String name;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
