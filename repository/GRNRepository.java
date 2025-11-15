package com.automotive.eis.repository;

import com.automotive.eis.entity.GRN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GRNRepository extends JpaRepository<GRN, String> {
    List<GRN> findByPoId(String poId);
}

