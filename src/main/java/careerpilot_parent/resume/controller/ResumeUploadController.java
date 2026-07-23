package careerpilot_parent.resume.controller;


import careerpilot_parent.resume.dto.request.UpdateResumeUploadRequest;
import careerpilot_parent.resume.dto.response.ResumeUploadResponse;
import careerpilot_parent.resume.service.ResumeUploadService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/student/resume-uploads")
@RequiredArgsConstructor
public class ResumeUploadController {


    private final ResumeUploadService resumeUploadService;



    /**
     * Upload Resume
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeUploadResponse> uploadResume(@RequestPart("file") MultipartFile file) {
        return new ResponseEntity<>(
                resumeUploadService.uploadResume(file),
                HttpStatus.CREATED
        );

    }





    /**
     * Get All Uploaded Resumes
     */
    @GetMapping
    public ResponseEntity<List<ResumeUploadResponse>> getAllResumes() {


        return ResponseEntity.ok(resumeUploadService.getAllResumes());

    }





    /**
     * Get Resume By Id
     */
    @GetMapping("/{uploadId}")
    public ResponseEntity<ResumeUploadResponse> getResumeById(@PathVariable Long uploadId) {


        return ResponseEntity.ok(resumeUploadService.getResumeById(uploadId));

    }





    /**
     * Replace Resume File
     */
    @PutMapping(value = "/{uploadId}/replace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeUploadResponse> replaceResume(@PathVariable Long uploadId, @RequestParam("file") MultipartFile file) {


        return ResponseEntity.ok(resumeUploadService.replaceResume(uploadId, file));

    }





    /**
     * Activate / Deactivate Resume
     */
    @PutMapping("/{uploadId}")
    public ResponseEntity<ResumeUploadResponse> updateResume(@PathVariable Long uploadId, @Valid @RequestBody UpdateResumeUploadRequest request) {


        return ResponseEntity.ok(resumeUploadService.updateResume(uploadId, request));

    }





    /**
     * Download Resume
     */
    @GetMapping("/{uploadId}/download")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Long uploadId
    ) {


        Resource resource =
                resumeUploadService.downloadResume(uploadId);



        return ResponseEntity.ok()
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + resource.getFilename()
                                + "\""
                )
                .body(resource);

    }





    /**
     * Delete Resume
     */
    @DeleteMapping("/{uploadId}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable Long uploadId
    ) {


        resumeUploadService.deleteResume(uploadId);


        return ResponseEntity.noContent()
                .build();

    }

}