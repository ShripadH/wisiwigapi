package com.example.wisiwigapi.wisiwigapi.controllers;

import com.example.wisiwigapi.wisiwigapi.service.TemplateMergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/template")
public class TemplateController {
    private final TemplateMergeService templateMergeService;

    @Autowired
    public TemplateController(TemplateMergeService templateMergeService) {
        this.templateMergeService = templateMergeService;
    }

    @PostMapping(value = "/merge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> mergeTemplate(@RequestBody Map<String, Object> request) {
        try {
            Object htmlObj = request.get("html");
            Object dataObj = request.get("data");
            if (htmlObj == null || dataObj == null || !(dataObj instanceof Map)) {
                return ResponseEntity.badRequest().body("Request must contain 'html' (string) and 'data' (object)");
            }
            String html = htmlObj.toString();
            Map<String, Object> data = (Map<String, Object>) dataObj;
            String result = templateMergeService.mergeHtmlString(html, data);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
} 