package com.automotive.eis.repository;

import com.automotive.eis.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
    List<Shipment> findByOrderId(Long orderId);
    List<Shipment> findByStatus(String status);
}

