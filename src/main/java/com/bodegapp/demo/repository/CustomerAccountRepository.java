package com.bodegapp.demo.repository;

import com.bodegapp.demo.model.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount,Long> {
    CustomerAccount findByCustomerId(Long customerId);
}
