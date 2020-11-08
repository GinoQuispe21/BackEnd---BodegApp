package com.bodegapp.demo.repository;

import com.bodegapp.demo.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Page<OrderDetail> findByOrderId(Long orderId, Pageable pageable);
    List<OrderDetail> findByOrderId(Long orderId);
}
