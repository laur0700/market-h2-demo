package com.example.h2demo.service;

import com.example.h2demo.model.*;
import com.example.h2demo.repository.OrderedProductRepository;
import com.example.h2demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderedProductRepository orderedProductRepository;

    @Autowired
    CartService cartService;

    @Autowired
    WishlistService wishlistService;

    @Autowired
    ProductService productService;

    public void sendOrder(Integer userId){
        User updatedUser = this.getById(userId);

        for(OrderedProduct orderedProduct : updatedUser.getCart().getProducts()){
            Product updatedProduct = productService.getById(orderedProduct.getProduct().getId());
            updatedProduct.setStock(updatedProduct.getStock() - orderedProduct.getQuantity());

            productService.update(updatedProduct);
        }

        updatedUser.getCart().getProducts().removeAll(updatedUser.getCart().getProducts());
        updatedUser.setOrderHistory(updatedUser.getOrderHistory() + 1);
        updatedUser.getCart().setQuantityOfProducts(0);

        this.update(updatedUser);
    }

    public void addProductsToCart(Integer userId, Integer productId, Integer quantity){
        User updatedUser = this.getById(userId);
        boolean productInCart = false;

        if(!updatedUser.getCart().getProducts().isEmpty()) {
            for (OrderedProduct orderedProduct : updatedUser.getCart().getProducts()) {
                if (orderedProduct.getProduct().getId().equals(productId)) {
                    productInCart = true;

                    updatedUser.getCart().setQuantityOfProducts(updatedUser.getCart().getQuantityOfProducts() + quantity);
                    orderedProduct.setQuantity(orderedProduct.getQuantity() + quantity);

                    orderedProductRepository.save(orderedProduct);
                    break;
                }
            }

            if(!productInCart){
                Product product = productService.getById(productId);

                OrderedProduct productToAdd = new OrderedProduct();
                productToAdd.setProduct(product);
                productToAdd.setName(product.getName());
                productToAdd.setPrice(product.getPrice());
                productToAdd.setQuantity(quantity);

                orderedProductRepository.save(productToAdd);

                updatedUser.getCart().getProducts().add(productToAdd);
                updatedUser.getCart().setQuantityOfProducts(updatedUser.getCart().getQuantityOfProducts() + quantity);
            }
        }
        else{
            Product product = productService.getById(productId);

            OrderedProduct productToAdd = new OrderedProduct();
            productToAdd.setProduct(product);
            productToAdd.setName(product.getName());
            productToAdd.setPrice(product.getPrice());
            productToAdd.setQuantity(quantity);

            orderedProductRepository.save(productToAdd);

            updatedUser.getCart().getProducts().add(productToAdd);
            updatedUser.getCart().setQuantityOfProducts(updatedUser.getCart().getQuantityOfProducts() + quantity);
        }

        this.update(updatedUser);
    }

    public void removeProductsFromCart(Integer userId, Integer productId, Integer quantity){
        User updatedUser = this.getById(userId);

        for(OrderedProduct orderedProduct : updatedUser.getCart().getProducts()){
            if(orderedProduct.getProduct().getId().equals(productId) && orderedProduct.getQuantity().equals(quantity)){
                updatedUser.getCart().getProducts().remove(orderedProduct);
                updatedUser.getCart().setQuantityOfProducts(updatedUser.getCart().getQuantityOfProducts() - quantity);

                orderedProductRepository.delete(orderedProduct);
                break;
            }
            else if(orderedProduct.getProduct().getId().equals(productId) && orderedProduct.getQuantity() > quantity){
                orderedProduct.setQuantity(orderedProduct.getQuantity() - quantity);
                updatedUser.getCart().setQuantityOfProducts(updatedUser.getCart().getQuantityOfProducts() - quantity);

                orderedProductRepository.save(orderedProduct);
                break;
            }
        }

        this.update(updatedUser);
    }

    public void addProductsToWishlist(Integer userId, Integer productId){
        User updatedUser = this.getById(userId);
        boolean productInWishlist = false;

        if(!updatedUser.getWishlist().getProducts().isEmpty()) {
            for (OrderedProduct orderedProduct : updatedUser.getWishlist().getProducts()) {
                if (orderedProduct.getProduct().getId().equals(productId)) {
                    productInWishlist = true;
                    break;
                }
            }

            if(!productInWishlist){
                Product product = productService.getById(productId);

                OrderedProduct productToAdd = new OrderedProduct();
                productToAdd.setProduct(product);
                productToAdd.setName(product.getName());
                productToAdd.setPrice(product.getPrice());

                updatedUser.getWishlist().getProducts().add(productToAdd);

                wishlistService.update(updatedUser.getWishlist());
            }
        }
        else{
            Product product = productService.getById(productId);

            OrderedProduct productToAdd = new OrderedProduct();
            productToAdd.setProduct(product);
            productToAdd.setName(product.getName());
            productToAdd.setPrice(product.getPrice());

            updatedUser.getWishlist().getProducts().add(productToAdd);

            wishlistService.update(updatedUser.getWishlist());
        }

        this.update(updatedUser);
    }

    public void removeProductsFromWishlist(Integer userId, Integer productId){
        User updatedUser = this.getById(userId);

        for(OrderedProduct orderedProduct : updatedUser.getWishlist().getProducts()){
            if(orderedProduct.getProduct().getId().equals(productId)){
                updatedUser.getWishlist().getProducts().remove(orderedProduct);

                wishlistService.update(updatedUser.getWishlist());

                break;
            }
        }

        this.update(updatedUser);
    }

    public List<User> getAllSorted(){
        List<User> users = userRepository.findAll();
        users.sort(Collections.reverseOrder());
        return users;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(Integer id){
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.orElse(null);
    }

    public void create(User newUser){
        Cart newCart = new Cart();
        newCart.setUser(newUser);
        newUser.setCart(newCart);

        Wishlist newWishlist = new Wishlist();
        newWishlist.setUser(newUser);
        newUser.setWishlist(newWishlist);

        cartService.create(newCart);
        wishlistService.create(newWishlist);
        userRepository.save(newUser);
    }

    public void update(User updatedUser){
        userRepository.save(updatedUser);
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }
}
