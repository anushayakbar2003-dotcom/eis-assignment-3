package com.automotive.eis.repository;

import com.automotive.eis.entity.ASN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ASNRepository extends JpaRepository<ASN, String> {
    List<ASN> findByPoId(String poId);
    List<ASN> findBySupplierId(String supplierId);
}

