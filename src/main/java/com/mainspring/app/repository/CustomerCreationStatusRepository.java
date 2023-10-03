package com.mainspring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mainspring.app.entity.CustomerCreationStatus;

@Repository
public interface CustomerCreationStatusRepository extends JpaRepository<CustomerCreationStatus, Integer>{

}
