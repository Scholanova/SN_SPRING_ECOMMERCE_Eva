package com.scholanova.ecommerce.order.entity;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.cart.entity.CartItem;
import com.scholanova.ecommerce.order.exception.NotAllowedException;
import com.scholanova.ecommerce.product.entity.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;


class OrdersTest {

    @Test
    public void checkout_ShouldSetTheDateAndTimeOfTodayInTheOrder() throws NotAllowedException{
    	//given
        Orders order = new Orders();
        Date currentDate = new Date(System.currentTimeMillis());
        
        //when
        order.checkout();
        //then
        assertThat(order.getIssueDate()).isEqualTo(currentDate);
    }

    @Test
    public void checkout_ShouldSetOrderStatusToPending() throws NotAllowedException{
    	//given
        Orders order = new Orders();
        
        //when
        order.checkout();
        
        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    public void checkout_ShouldThrowNotAllowedExceptionIfStatusIsClosed(){
    	//given
        Orders order = new Orders();
        
        //when
        order.setStatus(OrderStatus.CLOSED);
        
        //then
        assertThrows(NotAllowedException.class, () -> {
        	order.checkout();
        });
    }

    @Test
    public void checkout_ShouldThrowIllegalArgExceptionIfCartTotalItemsQuantityIsZERO() throws NotAllowedException{
    	//given
        Orders order = new Orders();
        
        Cart cart = new Cart();

        order.setCart(cart);
        
        //then
        assertThrows(IllegalArgumentException.class, () -> {
        	order.checkout();
        });
    }

    @Test
    public void setCart_ShouldThrowNotAllowedExceptionIfStatusIsClosed(){
    	//given
        Orders order = new Orders();
        
        Cart cart = new Cart();
        
        order.setStatus(OrderStatus.CLOSED);

        //then
        assertThrows(NotAllowedException.class, () -> {
        	order.setCart(cart);
        });
    }

    @Test
    @Disabled
    public void createOrder_ShouldSetTheCartInTheOrder(){

    }

    @Test
    @Disabled
    public void createOrder_ShouldSetStatusToCreated(){

    }

    @Test
    @Disabled
    public void getDiscount_shouldReturnZEROIFCartTotalPriceIsLessThan100(){

    }

    @Test
    @Disabled
    public void getDiscount_shouldReturn5percentIfCartTotalPriceIsMoreOrEqual100(){

    }

    @Test
    @Disabled
    public void getOrderPrice_shouldReturnTotalPriceWithDiscount(){

    }

    @Test
    @Disabled
    public void close_ShouldSetStatusToClose(){

    }

}