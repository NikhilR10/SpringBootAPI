package com.mainspring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mainspring.app.entity.LineItem;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Integer>{

}
