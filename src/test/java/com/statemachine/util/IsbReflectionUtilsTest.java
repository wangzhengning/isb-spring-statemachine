package com.statemachine.util;

import com.statemachine.entity.User;
import org.junit.Test;

/**
 * Created by zn.wang on 17/4/21.
 */
public class IsbReflectionUtilsTest {
    @Test
    public void readField() throws Exception {
        User user = new User();
        user.setUserName("zansan");
        user.setPassWord("lisi");
        String clazz = IsbReflectionUtils.readField("userName", user);
        System.out.println("clazz:{}" + clazz);
    }

    @Test
    public void setField() throws Exception {
        User user = new User();
        System.out.println("Before call:{}" + user);
        IsbReflectionUtils.setField("userName", user, "lisi");
        System.out.println("After call:{}" + user);
    }

    @Test
    public void callMethod() throws Exception {
        User user = new User();
        System.out.println("Before call:{}" + user);
        String username = IsbReflectionUtils.callMethod("getUserName", user);
        System.out.println("userName:{}" + username);
        System.out.println("Aftre call:{}" + user);
    }

    @Test
    public void callMethod1() throws Exception {
        User user = new User();
        System.out.println("Before call:{}" + user);
        String userNameAndPwd = IsbReflectionUtils.callMethod("getUserNameAndPwd", user, new Object[]{"william.wzn"} ,
                String.class);
        System.out.println("userNameAndPwd:{} " + userNameAndPwd);
    }

    @Test
    public void callMethod2() throws Exception{
        User user = new User();
        System.out.println("Before call:{}" + user);
        String userNameAndPwd = IsbReflectionUtils.callMethod("getUserNameAndPwd" , user , new Object[]{"william.wzn" , "56789"},
            String.class ,String.class);
        System.out.println("userNameAndPwd:{}" + userNameAndPwd);
    }
}













