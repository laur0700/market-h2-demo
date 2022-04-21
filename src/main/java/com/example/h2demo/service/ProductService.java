package com.example.h2demo.service;

import com.example.h2demo.model.Product;
import com.example.h2demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getById(Integer id){
        Optional<Product> productOptional = productRepository.findById(id);

        return productOptional.orElse(null);
    }

    public void create(Product newProduct){
        productRepository.save(newProduct);
    }

    public void update(Product updatedProduct){
        productRepository.save(updatedProduct);
    }

    public void delete(Integer id){
        productRepository.deleteById(id);
    }
}
