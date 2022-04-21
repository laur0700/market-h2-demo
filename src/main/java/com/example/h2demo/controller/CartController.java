package com.example.h2demo.controller;

import com.example.h2demo.model.Cart;
import com.example.h2demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("get/all")
    public List<Cart> getAll(){
        return cartService.getAll();
    }

    @GetMapping("get/id/{id}")
    public Cart get(@PathVariable Integer id){
        return cartService.getById(id);
    }

    @PostMapping("create")
    public void create(@RequestBody Cart newCart){
        cartService.create(newCart);
    }

    @PostMapping("update")
    public void update(@RequestBody Cart updatedCart){
        cartService.update(updatedCart);
    }

    @DeleteMapping("delete/id/{id}")
    public void delete(@PathVariable Integer id){
        cartService.delete(id);
    }
}
