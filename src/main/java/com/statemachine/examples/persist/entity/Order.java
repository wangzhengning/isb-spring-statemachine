package com.statemachine.examples.persist.entity;

import java.io.Serializable;

/**
 * Created by zn.wang on 17/4/26.
 */
public class Order implements Serializable{
    private int id;
    private String state;

    public Order(int id, String state) {
        this.id = id;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", state=" + state + "]";
    }

}
