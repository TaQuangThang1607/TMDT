package com.example.Custom.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.CartItem;
import com.example.Custom.domain.Product;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{
        boolean existsByCartAndProduct(Cart cart, Product product);
        CartItem findByCartAndProduct(Cart cart, Product product);

        @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart = :cart")
        int countByCart(@Param("cart") Cart cart);
        

        @Modifying
        @Query("DELETE FROM CartItem ci WHERE ci.cart = :cart")
        void deleteByCart(@Param("cart") Cart cart);

        
}
