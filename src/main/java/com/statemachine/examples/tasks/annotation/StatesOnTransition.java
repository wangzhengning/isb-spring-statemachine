package com.statemachine.examples.tasks.annotation;

import com.statemachine.examples.tasks.enumpk.EnumStates4Tasks;
import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zn.wang on 17/4/26.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
public @interface StatesOnTransition {

    EnumStates4Tasks[] source() default {};

    EnumStates4Tasks[] target() default {};

}
