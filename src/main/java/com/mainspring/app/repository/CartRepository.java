package com.mainspring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mainspring.app.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

}
