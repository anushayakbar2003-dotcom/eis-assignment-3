package com.automotive.eis.repository;

import com.automotive.eis.entity.DemandForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DemandForecastRepository extends JpaRepository<DemandForecast, Long> {
    List<DemandForecast> findByPartId(String partId);
    Optional<DemandForecast> findFirstByPartIdOrderByCreatedAtDesc(String partId);
}

