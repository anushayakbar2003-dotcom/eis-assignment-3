package com.automotive.eis.repository;

import com.automotive.eis.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {
    List<PurchaseOrder> findBySupplierId(String supplierId);
    List<PurchaseOrder> findByStatus(String status);
}

