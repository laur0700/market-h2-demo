package com.example.h2demo.controller;

import com.example.h2demo.model.Product;
import com.example.h2demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("get/all")
    public List<Product> getAll(){
        return productService.getAll();
    }

    @GetMapping("get/id/{id}")
    public Product get(@PathVariable Integer id){
        return productService.getById(id);
    }

    @PostMapping("create")
    public void create(@RequestBody Product newProduct){
        productService.create(newProduct);
    }

    @PostMapping("update")
    public void update(@RequestBody Product updatedProduct){
        productService.update(updatedProduct);
    }

    @DeleteMapping("delete/id/{id}")
    public void delete(@PathVariable Integer id){
        productService.delete(id);
    }
}
