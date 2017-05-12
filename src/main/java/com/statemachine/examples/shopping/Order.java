package com.statemachine.examples.shopping;

import java.io.Serializable;

/**
 * Created by zn.wang on 17/5/10.
 */
public class Order implements Serializable{
    private String id;
    private String name;
    private String price;
    private String state;

    public String getPrice ( ) {
        return price;
    }

    public void setPrice ( String price ) {
        this.price = price;
    }

    public String getName ( ) {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getId ( ) {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getState ( ) {
        return state;
    }

    public void setState ( String state ) {
        this.state = state;
    }
}
