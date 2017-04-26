package com.statemachine.examples.persist.Service;

import com.statemachine.examples.persist.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zn.wang on 17/4/26.
 */
@Service
public class PersistService {
    private final PersistStateMachineHandler handler;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final PersistStateMachineHandler.PersistStateChangeListener listener
            = new LocalPersistStateChangeListener();

    public PersistService(PersistStateMachineHandler handler) {
        this.handler = handler;
        this.handler.addPersistStateChangeListener(listener);
    }

    public String listDbEntries() {
        List<Order> orders = jdbcTemplate.query(
                "select id, state from orders",
                new RowMapper<Order>() {
                    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Order(rs.getInt("id"), rs.getString("state"));
                    }
                });
        StringBuilder buf = new StringBuilder();
        for (Order order : orders) {
            buf.append(order);
            buf.append("\n");
        }
        return buf.toString();
    }


    public void change(int order, String event) {
        Order o = jdbcTemplate.queryForObject("select id, state from orders where id = ?", new Object[]{order},
                new RowMapper<Order>() {
                    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Order(rs.getInt("id"), rs.getString("state"));
                    }
                });
        handler.handleEventWithState(MessageBuilder.withPayload(event).setHeader("order", order).build(),
                o.getState());
    }


    private class LocalPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {

        @Override
        public void onPersist(State<String, String> state, Message<String> message,
                              Transition<String, String> transition, StateMachine<String, String> stateMachine) {
            if (message != null && message.getHeaders().containsKey("order")) {
                Integer order = message.getHeaders().get("order", Integer.class);
                jdbcTemplate.update("update orders set state = ? where id = ?", state.getId(), order);
            }
        }
    }

}
