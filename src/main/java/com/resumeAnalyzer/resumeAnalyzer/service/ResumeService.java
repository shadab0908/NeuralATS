package com.resumeAnalyzer.resumeAnalyzer.service;

import com.resumeAnalyzer.resumeAnalyzer.util.PdfUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    // Define a hardcoded list of target skills for the analyzer
    private final List<String> TARGET_SKILLS = Arrays.asList("Java", "Spring Boot", "Python", "SQL", "Docker", "AWS", "React");

    public Map<String, Object> analyzeResume(MultipartFile file) throws Exception {
        String text = com.resumeAnalyzer.resumeAnalyzer.util.PdfUtil.extractText(file).toUpperCase();

        List<String> found = TARGET_SKILLS.stream()
                .filter(skill -> text.contains(skill.toUpperCase()))
                .collect(Collectors.toList());

        List<String> missing = TARGET_SKILLS.stream()
                .filter(skill -> !found.contains(skill))
                .collect(Collectors.toList());

        // Calculate score
        double score = (double) found.size() / TARGET_SKILLS.size() * 100;

        // Generate simple recommendation
        String recommendation = score > 70 ? "Strong Match" :
                score > 40 ? "Potential Match - Needs Upskilling" : "Weak Match";

        return Map.of(
                "found", found,
                "missing", missing,
                "matchPercentage", Math.round(score) + "%",
                "recommendation", recommendation
        );
    }

}