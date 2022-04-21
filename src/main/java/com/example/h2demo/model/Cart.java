package com.example.h2demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantityOfProducts;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderedProduct> products;
}
