package com.scholanova.ecommerce.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.cart.entity.CartItem;
import com.scholanova.ecommerce.order.exception.NotAllowedException;
import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
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

    public void createOrder(){
        //TODO
    }

    public void checkout() throws NotAllowedException, IllegalArgumentException{
    	if (status == OrderStatus.CLOSED) throw new NotAllowedException("Status can't be closed");
    	
    	int cartQte = 0;
    	
    	
    	if ( this.cart != null && cartQte == 0) {
	    	for(CartItem cartItem : this.getCart().getCartItems()) {
	    		cartQte += cartItem.getQuantity();
	    	}
    	
            throw new IllegalArgumentException("Order contain 0 cart");
        }
        
    	
    	Date date = new Date(System.currentTimeMillis());
        this.setIssueDate(date);
        this.setStatus(OrderStatus.PENDING);
    }

    public void getDiscount(){
        //TODO
    }

    public void getOrderPrice(){
        //TODO
    }

    public void close(){
        //TODO
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
