package com.example.wisiwigapi.wisiwigapi.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Map;

@Service
public class TemplateMergeService {
    private final TemplateEngine templateEngine;

    @Autowired
    public TemplateMergeService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String merge(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }

    public String mergeHtmlString(String html, Map<String, Object> variables) {
        try {
            StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
            TemplateEngine stringTemplateEngine = new TemplateEngine();
            stringTemplateEngine.setTemplateResolver(stringTemplateResolver);
            Context context = new Context();
            context.setVariables(variables);
            return stringTemplateEngine.process(html, context);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing template: " + e.getMessage();
        }
    }
} 