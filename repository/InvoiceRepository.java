package com.automotive.eis.repository;

import com.automotive.eis.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findByPoId(String poId);
    List<Invoice> findBySupplierId(String supplierId);
    List<Invoice> findByStatus(String status);
    Optional<Invoice> findByInvoiceId(String invoiceId);
}

