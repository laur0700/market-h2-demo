package com.example.h2demo.service;

import com.example.h2demo.model.Cart;
import com.example.h2demo.model.OrderedProduct;
import com.example.h2demo.model.Product;
import com.example.h2demo.model.User;
import com.example.h2demo.repository.OrderedProductRepository;
import com.example.h2demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    ProductService productService;

    public void addProductsToCart(Integer userId, Integer productId, Integer quantity){
        User updatedUser = this.getById(userId);

        if(updatedUser.getCart().getProducts() != null) {
            for (OrderedProduct orderedProduct : updatedUser.getCart().getProducts()) {
                if (orderedProduct.getProduct().getId().equals(productId)) {
                    updatedUser.getCart().setQuantityOfProducts(updatedUser.getCart().getQuantityOfProducts() + quantity);

                    orderedProductRepository.save(orderedProduct);
                    break;
                } else {
                    Product product = productService.getById(productId);

                    OrderedProduct productToAdd = new OrderedProduct();
                    productToAdd.setProduct(product);
                    productToAdd.setName(product.getName());
                    productToAdd.setPrice(product.getPrice());
                    productToAdd.setQuantity(quantity);

                    orderedProductRepository.save(productToAdd);

                    updatedUser.getCart().getProducts().add(productToAdd);
                    updatedUser.getCart().setQuantityOfProducts(updatedUser.getCart().getQuantityOfProducts() + quantity);
                    break;
                }
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

        cartService.create(newCart);
        userRepository.save(newUser);
    }

    public void update(User updatedUser){
        userRepository.save(updatedUser);
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }
}
