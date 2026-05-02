package com.resumeAnalyzer.resumeAnalyzer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    public String getAiFeedback(String resumeText) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        // Structured prompt for better results
        String prompt = "Analyze this resume text and provide 3 professional improvement tips. Keep it concise: " + resumeText;

        Map<String, Object> body = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );

        return restClient.post()
                .uri(url)
                .body(body)
                .retrieve()
                .body(String.class);
    }
}