package com.scholanova.ecommerce.order.entity;

import org.junit.jupiter.api.Test;

import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.order.exception.NotAllowedException;
import com.scholanova.ecommerce.product.entity.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.sql.Date;


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
    public void createOrder_ShouldSetTheCartInTheOrder() throws NotAllowedException{
    	//given
        Orders order = new Orders();
        Cart cart = new Cart();
        
        //when
        order.createOrder(cart);
        
        //then
        assertThat(order.getCart()).isEqualTo(cart);
    }

    @Test
    public void createOrder_ShouldSetStatusToCreated() throws NotAllowedException{
    	//given
        Orders order = new Orders();
        Cart cart = new Cart();
        
        //when
        order.createOrder(cart);
        
        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    public void getDiscount_shouldReturnZEROIFCartTotalPriceIsLessThan100() throws NotAllowedException{
    	//given
        Orders order = new Orders();
        
        Cart cart = new Cart();

        //when
        order.setCart(cart);
        
        //then
        assertThat(order.getDiscount()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    public void getDiscount_shouldReturn5percentIfCartTotalPriceIsMoreOrEqual100() throws NotAllowedException{
    	//given
        Orders order = new Orders();
        
        Cart cart = new Cart();
        
        Product product1 = Product.create("product1", "target", 10.0f, 0.2f, "EUR");
        Product product2 = Product.create("product2", "target", 92.0f, 0.2f, "EUR");
       
        cart.addProduct(product1, 2);
        cart.addProduct(product2, 1);
        
        //when
        order.setCart(cart);
        
        //then
        assertThat(order.getDiscount()).isEqualTo(BigDecimal.valueOf(5));
    }

    @Test
    public void getOrderPrice_shouldReturnTotalPriceWithDiscount() throws NotAllowedException{
    	//given
        Orders order = new Orders();
        
        Cart cart = new Cart();
        
        Product product1 = Product.create("product1", "target", 10.0f, 0.2f, "EUR");
        Product product2 = Product.create("product2", "target", 92.0f, 0.2f, "EUR");
       
        //when
        cart.addProduct(product1, 2);
        cart.addProduct(product2, 1);
        
        order.setCart(cart);
        
        BigDecimal totalPrice = cart.getTotalPrice();
        BigDecimal discountPrice = totalPrice.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(5));
        
        //then
        assertThat(order.getOrderPrice()).isEqualTo(discountPrice);
    }

    @Test
    public void close_ShouldSetStatusToClose(){
    	//given
        Orders order = new Orders();
        
        //when
        order.close();
        
        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CLOSED);
    }

}