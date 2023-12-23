package com.autocomplete.autocompletev2.controller;

import com.autocomplete.autocompletev2.service.AutoCompleteService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class AutoCompleteController {

    private static final Logger logger = LogManager.getLogger(AutoCompleteController.class);
    private final AutoCompleteService autoCompleteService;

    @Autowired
    public AutoCompleteController(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    /**
     * Endpoint for auto-completion suggestions.
     * @param prefix the input prefix for auto-completion.
     * @return a list of suggested completions.
     */
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam String prefix) {
        try {
            logger.info("Received autocomplete request for prefix: {}", prefix);
            List<String> suggestions = autoCompleteService.getSuggestions(prefix);
            logger.info("Returning {} suggestions", suggestions.size());
            return ResponseEntity.ok(suggestions);
        } catch (Exception e) {
            // Log the exception details and return an appropriate error response
            logger.error("Error occurred while processing autocomplete request", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
