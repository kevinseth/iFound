package com.iFound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.iFound.model.LostDocument;
import com.iFound.model.User;
import com.iFound.repository.LostDocumentRepository;
import com.iFound.repository.UserRepository;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

@Controller
public class LostDocumentController {

    private final LostDocumentRepository lostDocumentRepository;
    private final UserRepository userRepository;

    @Autowired
    public LostDocumentController(LostDocumentRepository lostDocumentRepository, UserRepository userRepository) {
        this.lostDocumentRepository = lostDocumentRepository;
        this.userRepository = userRepository;
    }

    // Show the report form
    @GetMapping("/report-lost")
    public String showLostForm(Model model) {
        model.addAttribute("lostDocument", new LostDocument());
        return "report-lost";
    }

    // Handle form submission
    @PostMapping("/report-lost")
    public String submitLostForm(@ModelAttribute LostDocument lostDocument,
                                 @RequestParam("imageFile") MultipartFile imageFile,
                                 Model model) throws IOException {

        if (!imageFile.isEmpty() && imageFile.getSize() > 5 * 1024 * 1024) {
            model.addAttribute("error", "Image too large! Max size is 5MB.");
            model.addAttribute("lostDocument", lostDocument);
            return "report-lost";
        }

        if (!imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            String fileName = UUID.randomUUID() + ".jpg";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
            if (originalImage == null) {
                throw new IOException("Unsupported image format.");
            }

            int targetWidth = 100;
            int targetHeight = 100;

            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, targetWidth, targetHeight);
            g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g.dispose();

            File outputFile = uploadPath.resolve(fileName).toFile();
            ImageIO.write(resizedImage, "jpg", outputFile);

            lostDocument.setImagePath("/uploads/" + fileName);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        lostDocument.setReporterUsername(username);
        lostDocument.setActionStatus("PENDING");
        lostDocument.setFoundDate(null);

        lostDocumentRepository.save(lostDocument);
        model.addAttribute("lostDocument", lostDocument);
        return "success-lost";
    }

    @GetMapping("/success-lost")
    public String showSuccessPage() {
        return "success-lost";
    }

    // ✅ Show documents, with admin filter
    @GetMapping("/lost-documents")
    public String viewUserLostDocuments(@RequestParam(required = false) String keyword, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username);
        String position = (user != null && user.getPosition() != null) ? user.getPosition().toUpperCase() : "";

        List<LostDocument> lostDocuments;

        if ("ADMIN".equals(position)) {
            if (keyword != null && !keyword.isEmpty()) {
                lostDocuments = lostDocumentRepository
                    .findByFullNameContainingIgnoreCaseOrDocumentTypeContainingIgnoreCaseOrDocumentNumberContainingIgnoreCaseOrContactInfoContainingIgnoreCase(
                        keyword, keyword, keyword, keyword);
            } else {
                lostDocuments = lostDocumentRepository.findAll();
            }
        } else {
            if (keyword != null && !keyword.isEmpty()) {
                lostDocuments = lostDocumentRepository
                    .findByReporterUsernameAndFullNameContainingIgnoreCaseOrDocumentTypeContainingIgnoreCaseOrDocumentNumberContainingIgnoreCaseOrContactInfoContainingIgnoreCase(
                        username, keyword, keyword, keyword, keyword);
            } else {
                lostDocuments = lostDocumentRepository.findByReporterUsername(username);
            }
        }

        model.addAttribute("lostDocuments", lostDocuments);
        model.addAttribute("keyword", keyword);
        model.addAttribute("position", position);
        return "lost-documents";
    }

    @PostMapping("/mark-found/{id}")
    public String markAsFound(@PathVariable Long id) {
        LostDocument doc = lostDocumentRepository.findById(id).orElse(null);
        if (doc != null && "PENDING".equals(doc.getActionStatus())) {
            doc.setActionStatus("FOUND");
            doc.setFoundDate(LocalDate.now());
            lostDocumentRepository.save(doc);
        }
        return "redirect:/lost-documents";
    }
    
    @PostMapping("/undo-mark-found/{id}")
    public String undoMarkAsFound(@PathVariable Long id) {
        LostDocument doc = lostDocumentRepository.findById(id).orElse(null);
        if (doc != null && "FOUND".equals(doc.getActionStatus())) {
            doc.setActionStatus("PENDING");
            doc.setFoundDate(null);
            lostDocumentRepository.save(doc);
        }
        return "redirect:/lost-documents";
    }
    
}
