package com.automotive.eis.repository;

import com.automotive.eis.entity.Recall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecallRepository extends JpaRepository<Recall, String> {
    List<Recall> findByPartId(String partId);
    List<Recall> findByStatus(String status);
}

