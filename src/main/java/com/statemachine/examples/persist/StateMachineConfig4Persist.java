package com.statemachine.examples.persist;

import com.statemachine.examples.persist.enumpk.EnumEvents4Persist;
import com.statemachine.examples.persist.enumpk.EnumStates4Persist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import javax.sql.DataSource;

import static com.statemachine.examples.persist.enumpk.EnumStates4Persist.*;
import static jline.console.internal.ConsoleRunner.property;

/**
 * Created by zn.wang on 17/4/25.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig4Persist extends StateMachineConfigurerAdapter<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(StateMachineConfig4Persist.class);

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) {
        try {
            //可以添加listener
//                config.withConfiguration()
//                        .listener(listener());
        } catch (Exception e) {
            buildException(config.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) {
        try {
            buildStateMachineStateConfigurer(states);
        } catch (Exception e) {
            buildException(states.getClass().getName(), e);
        }
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) {
        try {
            buildStateMachineTransitionConfigurer(transitions);
        } catch (Exception e) {
            buildException(transitions.getClass().getName(), e);
        }
    }


    private void buildStateMachineStateConfigurer(
            StateMachineStateConfigurer<String, String> states) throws Exception {
        states
                .withStates()
                .initial(EnumStates4Persist.PLACED.name())
                .state(PROCESSING.name())
                .state(SENT.name())
                .state(DELIVERED.name());
    }


    private void buildStateMachineTransitionConfigurer(
            StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source(EnumStates4Persist.PLACED.name()).target(EnumStates4Persist.PROCESSING.name())
                .event(EnumEvents4Persist.PROCESS.name())
                .and()
                .withExternal()
                .source(EnumStates4Persist.PROCESSING.name()).target(EnumStates4Persist.SENT.name())
                .event(EnumEvents4Persist.SEND.name())
                .and()
                .withExternal()
                .source(EnumStates4Persist.SENT.name()).target(EnumStates4Persist.DELIVERED.name())
                .event(EnumEvents4Persist.DELIVER.name());
    }

    private static void buildException(String msg, Exception e) {
        logger.error("[ERRORS-IN-FUNTION] , CLASS-NAME = " + msg, e);
    }

    @Bean
    public JdbcTemplate initJdbcTemplate(){
        DataSource dataSource = initDataSource();
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource initDataSource(){
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1/testdb_01?characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "web";
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }
}










