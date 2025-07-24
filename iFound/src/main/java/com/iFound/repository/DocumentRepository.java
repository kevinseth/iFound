package com.iFound.repository;

import com.iFound.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByDocumentNumber(String documentNumber);
}
