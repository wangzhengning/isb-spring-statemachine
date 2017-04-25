package com.statemachine.examples.cdplayer.annotation;

import com.statemachine.examples.cdplayer.enumpk.EnumStates4CDPlayer;
import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zn.wang on 17/4/25.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
public @interface StatesOnTransition {
    EnumStates4CDPlayer[] source () default {};
    EnumStates4CDPlayer[] target () default {};
}
