package com.example.h2demo.service;

import com.example.h2demo.model.Wishlist;
import com.example.h2demo.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    public Wishlist getById(Integer id){
        Optional<Wishlist> wishlistOptional = wishlistRepository.findById(id);

        return wishlistOptional.orElse(null);
    }

    public void create(Wishlist newWishlist){
        wishlistRepository.save(newWishlist);
    }

    public void update(Wishlist updatedWishlist){
        wishlistRepository.save(updatedWishlist);
    }

    public void delete(Integer id){
        wishlistRepository.deleteById(id);
    }
}
