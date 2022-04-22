package com.example.h2demo.controller;

import com.example.h2demo.model.Wishlist;
import com.example.h2demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wishlists")
public class WishlistController {
    @Autowired
    WishlistService wishlistService;

    @GetMapping("get/all")
    public List<Wishlist> getAll(){
        return wishlistService.getAll();
    }

    @GetMapping("get/id/{id}")
    public Wishlist get(@PathVariable Integer id){
        return wishlistService.getById(id);
    }

    @PostMapping("create")
    public void create(@RequestBody Wishlist newWishlist){
        wishlistService.create(newWishlist);
    }

    @PostMapping("update")
    public void update(@RequestBody Wishlist updatedWishlist){
        wishlistService.update(updatedWishlist);
    }

    @DeleteMapping("delete/id/{id}")
    public void delete(@PathVariable Integer id){
        wishlistService.delete(id);
    }
}
