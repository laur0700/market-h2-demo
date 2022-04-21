package com.example.h2demo.service;

import com.example.h2demo.model.Cart;
import com.example.h2demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public List<Cart> getAll(){
        return cartRepository.findAll();
    }

    public Cart getById(Integer id){
        Optional<Cart> cartOptional = cartRepository.findById(id);

        return cartOptional.orElse(null);
    }

    public void create(Cart newCart){
        newCart.setQuantityOfProducts(0);
        cartRepository.save(newCart);
    }

    public void update(Cart updatedCard){
        cartRepository.save(updatedCard);
    }

    public void delete(Integer id){
        cartRepository.deleteById(id);
    }
}
