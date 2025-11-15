package com.automotive.eis.repository;

import com.automotive.eis.entity.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReturnRepository extends JpaRepository<Return, String> {
    Optional<Return> findByRmaId(String rmaId);
    List<Return> findByOrderId(Long orderId);
    List<Return> findByStatus(String status);
}

