package com.statemachine.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by zn.wang on 17/4/21.
 */
public class User implements Serializable {

    private static final long serialVersionUID = -3692886867843787605L;
    private String userName = "wzn";

    private String passWord = "123456";

    public String getUserNameAndPwd(String userName){
        this.userName = userName;
        return this.userName;
    }

    public String getUserNameAndPwd(String userName , String passWord){
        this.userName = userName;
        this.passWord = passWord;
        return this.userName +"_"+ this.passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
