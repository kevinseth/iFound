package com.iFound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iFound.model.LostDocument;

public interface LostDocumentRepository extends JpaRepository<LostDocument, Long> {
	
	List<LostDocument> findByReporterUsername(String username);

	List<LostDocument> findByReporterUsernameAndFullNameContainingIgnoreCaseOrDocumentTypeContainingIgnoreCaseOrDocumentNumberContainingIgnoreCaseOrContactInfoContainingIgnoreCase(
	    String username, String fullName, String docType, String docNumber, String contactInfo
	);
	
	
	List<LostDocument> findByFullNameContainingIgnoreCaseOrDocumentTypeContainingIgnoreCaseOrDocumentNumberContainingIgnoreCaseOrContactInfoContainingIgnoreCase(
		    String fullName, String docType, String docNumber, String contactInfo
		);


}

