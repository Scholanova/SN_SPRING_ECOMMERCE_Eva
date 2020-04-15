package com.scholanova.ecommerce.cart.service;

import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.cart.entity.CartItem;
import com.scholanova.ecommerce.cart.exception.CartException;
import com.scholanova.ecommerce.product.entity.Product;
import com.scholanova.ecommerce.product.repository.ProductRepository;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartServiceImpl implements CartService{

	@Autowired
	ProductRepository productRepo;
	
	
    @Override
    public Cart addProductToCart(Cart cart, Long productId, int quantity) throws CartException {
    	try {
            cart.addProduct(productRepo.findById(productId).get(), quantity);
            return cart;

        }catch (Exception e){
            throw new CartException("Unable to add a product to the cart");
        }
    }

    @Override
    public Cart changeProductQuantity(Cart cart, Long productId, int quantity) throws CartException {
    	try {
            cart.changeProductQuantity(cart.getCartItemByProductName(productRepo.findById(productId).get().getName()).getProduct(), quantity);
            return cart;
        }catch (Exception e){
            throw  new CartException("Unable to change a product to the cart");
        }
    }
}
