package com.iFound.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
//@Table(name = "lost_document")
public class LostDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String documentType;
    private String documentNumber;
    private String contactInfo;
    private LocalDate lostDate;
    private String imagePath;

    // ✅ New fields
    private String reporterUsername;

    @Column(nullable = false)
    private String actionStatus = "PENDING";  // PENDING, FOUND, etc.

    private LocalDate foundDate; // nullable until found

    // Constructors
    public LostDocument() {}

    public LostDocument(String fullName, String documentType, String documentNumber,
                        String contactInfo, LocalDate lostDate, String imagePath,
                        String reporterUsername, String actionStatus, LocalDate foundDate) {
        this.fullName = fullName;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.contactInfo = contactInfo;
        this.lostDate = lostDate;
        this.imagePath = imagePath;
        this.reporterUsername = reporterUsername;
        this.actionStatus = actionStatus;
        this.foundDate = foundDate;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDocumentType() { return documentType; }

    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getDocumentNumber() { return documentNumber; }

    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public String getContactInfo() { return contactInfo; }

    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public LocalDate getLostDate() { return lostDate; }

    public void setLostDate(LocalDate lostDate) { this.lostDate = lostDate; }

    public String getImagePath() { return imagePath; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getReporterUsername() { return reporterUsername; }

    public void setReporterUsername(String reporterUsername) { this.reporterUsername = reporterUsername; }

    public String getActionStatus() { return actionStatus; }

    public void setActionStatus(String actionStatus) { this.actionStatus = actionStatus; }

    public LocalDate getFoundDate() { return foundDate; }

    public void setFoundDate(LocalDate foundDate) { this.foundDate = foundDate; }
}
