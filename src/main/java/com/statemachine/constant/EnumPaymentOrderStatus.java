package com.statemachine.constant;

/**
 * Created by zn.wang on 17/4/21.
 */
public enum  EnumPaymentOrderStatus {

    UNPAID("UNPAID" , "待支付"),
    WAITING_FOR_RECEIVE("WAITING_FOR_RECEIVE" , "待收货"),
    DONE("DONE" , "结束");

    private String code;
    private String name;

    private EnumPaymentOrderStatus(String code ,String name){
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
