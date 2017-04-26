package com.statemachine.examples.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by zn.wang on 17/4/26.
 */
@Component
public class TasksCommands implements CommandMarker {

    @Autowired
    private Tasks tasks;

    @CliCommand(value = "tasks run", help = "Run tasks")
    public void run() {
        tasks.run();
    }

    @CliCommand(value = "tasks list", help = "List tasks")
    public String list() {
        return tasks.toString();
    }

    @CliCommand(value = "tasks fix", help = "Fix tasks")
    public void fix() {
        tasks.fix();
    }

    @CliCommand(value = "tasks fail", help = "Fail task")
    public void fail(@CliOption(key = {"", "task"}, help = "Task id") String task) {
        tasks.fail(task);
    }

    @CliCommand(value = "tasks success" ,help = "Success task")
    public void success(@CliOption(key = {"" ,"task"} , help = "Task id") String task){
        tasks.success(task);
    }

}