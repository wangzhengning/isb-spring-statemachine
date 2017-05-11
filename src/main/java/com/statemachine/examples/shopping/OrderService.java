package com.statemachine.examples.shopping;

/**
 * Created by zn.wang on 17/5/10.
 */
public interface OrderService {
    public void save(Order order);
    public Order findOne(Long id);
}
