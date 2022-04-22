package com.example.h2demo.controller;

import com.example.h2demo.model.User;
import com.example.h2demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("order/id/{id}")
    public String order(@PathVariable Integer id){
        userService.sendOrder(id);

        return "Order received";
    }

    @PostMapping("add-product-cart/user-id/{userId}/product-id/{productId}/quantity/{quantity}")
    public void addProductsToCart(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable Integer quantity){
        userService.addProductsToCart(userId, productId, quantity);
    }

    @PostMapping("remove-product-cart/user-id/{userId}/product-id/{productId}/quantity/{quantity}")
    public void removeProductsFromCart(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable Integer quantity){
        userService.removeProductsFromCart(userId, productId, quantity);
    }

    @PostMapping("add-product-wishlist/user-id/{userId}/product-id/{productId}")
    public void addProductsToWishlist(@PathVariable Integer userId, @PathVariable Integer productId){
        userService.addProductsToWishlist(userId, productId);
    }

    @PostMapping("remove-product-wishlist/user-id/{userId}/product-id/{productId}")
    public void removeProductsFromWishlist(@PathVariable Integer userId, @PathVariable Integer productId){
        userService.removeProductsFromWishlist(userId, productId);
    }

    @GetMapping("get/all-sorted")
    public List<User> getAllSorted(){
        return userService.getAllSorted();
    }

    @GetMapping("get/all")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("get/id/{id}")
    public User get(@PathVariable Integer id){
        return userService.getById(id);
    }

    @PostMapping("create")
    public void create(@RequestBody User newUser){
        userService.create(newUser);
    }

    @PostMapping("update")
    public void update(@RequestBody User updatedUser){
        userService.update(updatedUser);
    }

    @DeleteMapping("delete/id/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }
}
