package com.scholanova.ecommerce.order.service;

import com.scholanova.ecommerce.order.entity.Orders;
import com.scholanova.ecommerce.order.exception.NotAllowedException;

public interface OrdersService {
	
	public Orders checkout(Orders order) throws NotAllowedException, IllegalArgumentException;
	
	public void close(Orders order);

}
