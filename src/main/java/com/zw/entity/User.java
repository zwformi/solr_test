package com.zw.entity;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by Administrator on 2017/6/9.
 */
public class User {
    @Field
    private int id;

    @Field
    private String user_name;

    @Field
    private String password;

    @Field
    private int roleid;

    @Field
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "【"+this.user_name + "," + this.age + "," + this.roleid+"】";
    }
}
