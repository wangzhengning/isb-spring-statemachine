package com.statemachine.examples.showcase;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

/**
 * Created by zn.wang on 17/4/24.
 */
public class FooGuard implements Guard<EnumStates4ShowCase , EnumEvents4ShowCase> {

    private final int match;

    public FooGuard(int match){
        this.match = match;
    }

    @Override
    public boolean evaluate(StateContext<EnumStates4ShowCase, EnumEvents4ShowCase> stateContext) {

        Object foo = stateContext.getExtendedState().getVariables().get("foo");

        return !(null == foo || !foo.equals(match));
    }
}
