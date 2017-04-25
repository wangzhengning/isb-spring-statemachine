package com.statemachine.examples.showcase;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.Map;

/**
 * Created by zn.wang on 17/4/24.
 */
public class FooAction implements Action<EnumStates4ShowCase , EnumEvents4ShowCase>{

    private static final Logger logger = LoggerFactory.getLogger(FooAction.class);

    @Override
    public void execute(StateContext<EnumStates4ShowCase, EnumEvents4ShowCase> stateContext) {
        Map<Object ,Object > variables = Maps.newConcurrentMap();
        Integer foo = null;
        if(stateContext!= null && stateContext.getExtendedState()!= null){
            variables = stateContext.getExtendedState().getVariables();
            foo = stateContext.getExtendedState().get("foo" , Integer.class);
        }

        if(null == foo){
            logger.info("Init foo to 0");
            variables.put("foo" , 0);
        }
        else if(foo == 0){
            logger.info("Switch foo to 1");
            variables.put("foo" , 1);
        }
        else if(foo == 1) {
            logger.info("Switch foo to 0");
            variables.put("foo" , 0);
        }

    }
}
