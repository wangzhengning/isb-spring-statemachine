package com.statemachine.examples.tasks;


import com.statemachine.examples.tasks.annotation.StatesOnTransition;
import com.statemachine.examples.tasks.enumpk.EnumEvents4Tasks;
import com.statemachine.examples.tasks.enumpk.EnumStates4Tasks;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zn.wang on 17/4/26.
 */
@WithStateMachine
public class Tasks {

    private final static Log log = LogFactory.getLog(Tasks.class);

    @Autowired
    private StateMachine<EnumStates4Tasks, EnumEvents4Tasks> stateMachine;

    private final Map<String, Boolean> tasks = new HashMap<String, Boolean>();

    public Tasks() {
        tasks.put("T1", true);
        tasks.put("T2", true);
        tasks.put("T3", true);
    }

    public void run() {
        stateMachine.sendEvent(EnumEvents4Tasks.RUN);
    }

    public void fix() {
        tasks.put("T1", true);
        tasks.put("T2", true);
        tasks.put("T3", true);
        stateMachine.sendEvent(EnumEvents4Tasks.FIX);
    }

    public void fail(String task) {
        if (tasks.containsKey(task)) {
            tasks.put(task, false);
        }
    }

    public void success(String task) {
        if (tasks.containsKey(task)) {
            tasks.put(task, true);
        }
    }

    @StatesOnTransition(target = EnumStates4Tasks.T1)
    public void taskT1(ExtendedState extendedState) {
        runTask("T1", extendedState);
    }

    @StatesOnTransition(target = EnumStates4Tasks.T2)
    public void taskT2(ExtendedState extendedState) {
        runTask("T2", extendedState);
    }

    @StatesOnTransition(target = EnumStates4Tasks.T3)
    public void taskT3(ExtendedState extendedState) {
        runTask("T3", extendedState);
    }

    @StatesOnTransition(target = EnumStates4Tasks.AUTOMATIC)
    public void automaticFix(ExtendedState extendedState) {
        Map<Object, Object> variables = extendedState.getVariables();
        variables.put("T1", true);
        tasks.put("T1", true);
    }

    private void runTask(String task, ExtendedState extendedState) {
        log.info("run task on " + task);
        sleep(2000);
        extendedState.getVariables().put(task, tasks.get(task));
        log.info("run task on " + task + " done");
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public String toString() {
        return "Tasks " + tasks;
    }


}
