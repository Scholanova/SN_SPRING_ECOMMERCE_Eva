package com.scholanova.ecommerce.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.order.exception.NotAllowedException;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity(name="orders")
public class Orders {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column
    private String number;

    @Column
    private Date issueDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id", referencedColumnName = "id")
    private Cart cart;

    public Orders() {
    }

    public void createOrder(Cart cart) throws NotAllowedException{
        this.setCart(cart);
        this.setStatus(OrderStatus.CREATED);
    }

    public void checkout() throws NotAllowedException, IllegalArgumentException{
    	if (this.status == OrderStatus.CLOSED){
            throw new NotAllowedException("Order is CLOSED");
        }
        if ( this.cart != null && cart.getTotalQuantity() == 0) {
            throw new IllegalArgumentException("Order contain 0 quantity of item");
        }
    	
    	Date date = new Date(System.currentTimeMillis());
        this.setIssueDate(date);
        this.setStatus(OrderStatus.PENDING);
    }

    public BigDecimal getDiscount(){
    	if(BigDecimal.valueOf(100).compareTo(cart.getTotalPrice()) == 1) {
    		return BigDecimal.valueOf(0);
    	}
    	return BigDecimal.valueOf(5);
    }

    public BigDecimal getOrderPrice(){
        if(this.getDiscount().compareTo(BigDecimal.valueOf(0)) == 0){
        	return cart.getTotalPrice();
        }
        return cart.getTotalPrice().divide(BigDecimal.valueOf(100)).multiply(this.getDiscount());
        
    }

    public void close(){
        setStatus(OrderStatus.CLOSED);
    }


    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {return number;}

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getIssueDate() {return issueDate;}

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public OrderStatus getStatus() {return status;}

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Cart getCart() {
    	return cart;
    }

    public void setCart(Cart cart)  throws NotAllowedException{
    	if (status == OrderStatus.CLOSED) throw new NotAllowedException("Status can't be closed");
        this.cart = cart;
    }
}
