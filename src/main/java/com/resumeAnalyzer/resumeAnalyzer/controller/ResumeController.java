package com.resumeAnalyzer.resumeAnalyzer.controller;

import com.resumeAnalyzer.resumeAnalyzer.service.ResumeService;
import com.resumeAnalyzer.resumeAnalyzer.service.AiService;
import com.resumeAnalyzer.resumeAnalyzer.util.PdfUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    private AiService aiService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyze(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jd") String jd) { // Accepts the Job Description text
        try {
            // 1. Extract the raw text from the uploaded Resume PDF
            String resumeText = PdfUtil.extractText(file);

            // 2. Construct a sophisticated prompt for the AI to compare Resume vs JD
            String dynamicPrompt = String.format(
                    "You are an expert Technical Recruiter. Analyze the following Resume against the Job Description provided.\n\n" +
                            "JOB DESCRIPTION:\n%s\n\n" +
                            "RESUME CONTENT:\n%s\n\n" +
                            "Provide a detailed response in Markdown format including:\n" +
                            "1. **Match Percentage** (e.g., 85%%)\n" +
                            "2. **Key Skills Found** (List them)\n" +
                            "3. **Missing Critical Skills** (List them based on JD requirements)\n" +
                            "4. **Professional Recommendation** (3 bullet points for improvement).",
                    jd, resumeText
            );

            // 3. Get the raw response from the Gemini API
            String rawAiResponse = aiService.getAiFeedback(dynamicPrompt);

            // 4. Parse the JSON to extract the text content for the UI
            String cleanFeedback;
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(rawAiResponse);
                cleanFeedback = root.path("candidates").get(0)
                        .path("content")
                        .path("parts").get(0)
                        .path("text").asText();
            } catch (Exception parseError) {
                cleanFeedback = "Error decrypting AI transmission: " + parseError.getMessage();
            }

            // 5. Package the results for the frontend
            Map<String, Object> finalResponse = new HashMap<>();
            finalResponse.put("fileName", file.getOriginalFilename());
            finalResponse.put("aiFeedback", cleanFeedback);
            finalResponse.put("status", "success");

            return ResponseEntity.ok(finalResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "error", "Neural Link Failure: " + e.getMessage()
            ));
        }
    }
}