package com.synexis.management_service.controller;

import com.synexis.management_service.service.FileStorageService;
import com.synexis.management_service.service.UserProfileService;
import com.synexis.management_service.dto.ProfilePictureDto;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ProfileController {

    private final FileStorageService fileStorageService;
    private final UserProfileService userProfileService;

    public ProfileController(FileStorageService fileStorageService, UserProfileService userProfileService) {
        this.fileStorageService = fileStorageService;
        this.userProfileService = userProfileService;
    }

    @PostMapping(value = "/upload/profile-pic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadProfilePicture(
            @RequestParam("file") MultipartFile file) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String keycloakId = auth == null ? null : auth.getName();
            if (keycloakId == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthenticated"));
            }

            byte[] bytes;
            try {
                bytes = file.getBytes();
            } catch (java.io.IOException ioe) {
                return ResponseEntity.internalServerError().body(Map.of("error", ioe.getMessage()));
            }

            String contentType = file.getContentType();

            userProfileService.saveProfilePicture(keycloakId, bytes, contentType);

            return ResponseEntity.ok(Map.of("profilePictureUrl", "/api/users/me/profile-picture"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping(value = "/users/me/profile-picture")
    public ResponseEntity<byte[]> getMyProfilePicture() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String keycloakId = auth == null ? null : auth.getName();
        if (keycloakId == null) {
            return ResponseEntity.status(401).build();
        }

        ProfilePictureDto pic = userProfileService.getProfilePicture(keycloakId);
        if (pic == null || pic.data() == null || pic.data().length == 0) {
            return ResponseEntity.noContent().build();
        }

        String contentType = pic.contentType() == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : pic.contentType();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(pic.data());
    }
}
