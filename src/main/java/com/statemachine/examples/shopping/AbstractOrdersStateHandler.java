package com.statemachine.examples.shopping;

import org.springframework.statemachine.support.LifecycleObjectSupport;

/**
 * Created by zn.wang on 17/5/10.
 */
public abstract class AbstractOrdersStateHandler extends LifecycleObjectSupport {

    protected void onInit() throws Exception {}

    protected void doDestroy() {};

}
