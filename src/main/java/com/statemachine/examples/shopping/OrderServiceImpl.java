package com.statemachine.examples.shopping;

import org.springframework.stereotype.Service;

/**
 * Created by zn.wang on 17/5/10.
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Override
    public void save ( Order order ) {
        System.out.println ("插入成功");
    }

    @Override
    public Order findOne ( String id ) {
        Order order = new Order ();
        order.setId ( id );
        order.setName ( "phone" );
        order.setPrice ( "50000" );
        order.setState ( States.ASSEMBLED.name () );
        return order;
    }
}
