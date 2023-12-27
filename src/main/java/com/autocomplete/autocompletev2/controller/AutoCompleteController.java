package com.autocomplete.autocompletev2.controller;

import com.autocomplete.autocompletev2.exceptions.InvalidNameException;
import com.autocomplete.autocompletev2.model.Name;
import com.autocomplete.autocompletev2.service.AutoCompleteService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.naming.NameNotFoundException;
import java.util.List;

@RestController
public class AutoCompleteController {

    private static final Logger logger = LogManager.getLogger(AutoCompleteController.class);
    public static final String PREFIX_MAX_ITEM_LIMIT = "Too many results";
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
    @GetMapping("v1/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam String prefix) {
        logger.info("Received autocomplete request for prefix: {}", prefix);
        List<String> suggestions = autoCompleteService.getSuggestions(prefix);
        logger.info("Returning {} suggestions", suggestions.size());
        // Check for special too many results message
        if (suggestions.size() == 1 && suggestions.get(0).startsWith(PREFIX_MAX_ITEM_LIMIT)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(suggestions);
        }

        return ResponseEntity.ok(suggestions);
    }

    /**
     * Endpoint for creating a new name.
     * @param name the name to be saved.
     * @return a response entity with the created name or an error message.
     */
    @PostMapping("v1/names")
    public ResponseEntity<String> createName(@RequestBody String name) throws InvalidNameException {
            logger.info("Attempting to save new name: {}", name);
            autoCompleteService.saveName(name);
            logger.info("Successfully saved name: {}", name);
            return ResponseEntity.status(HttpStatus.CREATED).body(name);
    }

    /**
     * Endpoint for deleting an existing name.
     * @param name the name to be deleted.
     * @return a response entity indicating the result of the deletion operation.
     */
    @DeleteMapping("v1/names/by-name/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable String name) throws NameNotFoundException {
            logger.info("Attempting to delete name: {}", name);
            var deletedItems = autoCompleteService.deleteName(new Name(name));
            logger.info("Successfully deleted name: {}", name);
            String result = String.format("%d items deleted for names: %s", deletedItems, name);
            return ResponseEntity.status(HttpStatus.OK).body(result); // 200 with message OR 204 No Content is typically used for successful delete
    }

    /**
     * Endpoint for deleting an existing name by its ID.
     * @param id  the name to be deleted.
     * @return a response entity indicating the result of the deletion operation.
     */
    @DeleteMapping("v1/names/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NameNotFoundException {
            logger.info("Attempting to delete name with ID: {}", id);
            var deletedItems = autoCompleteService.deleteNameById(id); // Modify your service method accordingly
            logger.info("Successfully deleted name with ID: {}", id);
            String result = String.format("Name with ID %d deleted.", deletedItems);
            return ResponseEntity.ok(result);
    }

    /**
     * Endpoint for deleting an existing name by its prefix.
     * @param prefix the input prefix for auto-completion.
     * @return a response entity indicating the result of the deletion operation.
     */
    @DeleteMapping("v1/names/prefix/{prefix}")
    public ResponseEntity<String> deleteByPrefix(@PathVariable String prefix) {
        logger.info("Attempting to delete names by prefix {}", prefix);
        var deletedItems =  autoCompleteService.deleteNameByPrefix(prefix); // Modify your service method accordingly
        logger.info("Successfully deleted name by prefix: {}", prefix);
        String result = String.format("Name with %d items deleted.", deletedItems);
        return ResponseEntity.ok(result);
    }
}
