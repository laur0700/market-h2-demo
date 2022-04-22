package com.example.h2demo.unit;

import com.example.h2demo.model.OrderedProduct;
import com.example.h2demo.model.Wishlist;
import com.example.h2demo.service.WishlistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class WishlistServiceTest {
    @Autowired
    WishlistService wishlistService;

    @BeforeEach
    public void init(){
        Wishlist newWishlist = new Wishlist();
        wishlistService.create(newWishlist);
    }

    @Test
    public void whenGetById_thenCheckIfNotNull(){
        Assertions.assertNotNull(wishlistService.getById(1));
    }

    @Test
    public void whenUpdate_thenCheckIfUpdated(){
        Wishlist newWishlist = new Wishlist();
        newWishlist.setId(1);

        List<OrderedProduct> products = new ArrayList<>();
        newWishlist.setProducts(products);

        wishlistService.update(newWishlist);

        Assertions.assertNotNull(wishlistService.getById(1).getProducts());
    }

    @Test
    public void whenGetAll_thenCheckIfExists(){
        List<Wishlist> wishlists = wishlistService.getAll();

        Assertions.assertNotNull(wishlists);
    }
}
