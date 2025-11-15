package com.automotive.eis.repository;

import com.automotive.eis.entity.QualityInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QualityInspectionRepository extends JpaRepository<QualityInspection, String> {
    List<QualityInspection> findByGrnId(String grnId);
    List<QualityInspection> findByPartId(String partId);
    List<QualityInspection> findByResult(String result);
}

