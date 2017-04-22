package com.statemachine.constant;

/**
 * Created by zn.wang on 17/4/21.
 */
public enum EnumPaymentEvent {

    PAY("PAY" , "支付"),
    RECEIVE("RECEIVE" , "收货");

    private String code;
    private String name;

    private EnumPaymentEvent(String code ,String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
