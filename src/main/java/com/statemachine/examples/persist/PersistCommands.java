package com.statemachine.examples.persist;

import com.statemachine.examples.persist.Service.PersistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by zn.wang on 17/4/26.
 */
@Component
public class PersistCommands implements CommandMarker{

    @Autowired
    private PersistService persist;

    @CliCommand(value = "persist db", help = "List entries from db")
    public String listDbEntries() {
        return persist.listDbEntries();
    }

    @CliCommand(value = "persist process", help = "Process order")
    public void process(@CliOption(key = {"", "id"}, help = "Order id") int order) {
        persist.change(order, "PROCESS");
    }

    @CliCommand(value = "persist send", help = "Send order")
    public void send(@CliOption(key = {"", "id"}, help = "Order id") int order) {
        persist.change(order, "SEND");
    }

    @CliCommand(value = "persist deliver", help = "Deliver order")
    public void deliver(@CliOption(key = {"", "id"}, help = "Order id") int order) {
        persist.change(order, "DELIVER");
    }

}
