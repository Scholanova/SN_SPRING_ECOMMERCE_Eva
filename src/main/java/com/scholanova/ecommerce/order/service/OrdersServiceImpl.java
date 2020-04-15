package com.scholanova.ecommerce.order.service;

import org.springframework.stereotype.Component;

import com.scholanova.ecommerce.order.entity.Orders;
import com.scholanova.ecommerce.order.exception.NotAllowedException;

@Component
public class OrdersServiceImpl implements OrdersService{

	@Override
	public Orders checkout(Orders order) throws NotAllowedException, IllegalArgumentException {
		try {
			order.checkout();
			return order;
        }catch (NotAllowedException e){
            throw  new NotAllowedException("Status is closed");
        }catch (IllegalArgumentException e) {
        	throw  new IllegalArgumentException("Total quantity is equal to 0");
        }
	}

	@Override
	public void close(Orders order) {
		// TODO Auto-generated method stub
		
	}

}
