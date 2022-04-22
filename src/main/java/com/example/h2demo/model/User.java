package com.example.h2demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Comparable<User>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer orderHistory;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToOne(cascade = CascadeType.ALL)
    private Wishlist wishlist;

    @Override
    public int compareTo(User o) {
        return this.orderHistory - o.getOrderHistory();
    }
}
