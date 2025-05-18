package com.example.Custom.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.Product;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{
        boolean existsByCartAndProduct(Cart cart, Product product);
        CartItem findByCartAndProduct(Cart cart, Product product);

}
