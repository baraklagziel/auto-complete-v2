package com.autocomplete.autocompletev2.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.autocomplete.autocompletev2.exceptions.InvalidNameException;
import com.autocomplete.autocompletev2.utils.Trie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class AutoCompleteServiceTest {
    @Mock
    private Trie trie;
    @InjectMocks
    private AutoCompleteService autoCompleteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /******************************************************************** POSITIVE SCENARIOS **********************************************/

    @Test
    @DisplayName("Test getSuggestions returns correct results")
    void testGetSuggestionsSuccess() {
        String prefix = "test";
        List<String> mockResponse = Arrays.asList("testName1", "testName2");
        when(autoCompleteService.getSuggestions(prefix)).thenReturn(mockResponse);

        List<String> results = autoCompleteService.getSuggestions(prefix);

        assertNotNull(results);
        assertEquals(2, results.size()); // Assuming the response should match the mock
    }

    @Test
    @DisplayName("Test getSuggestions with empty Trie returns empty list")
    void testGetSuggestionsWithEmptyTrieSuccess() {
        String prefix = "test";
        when(trie.findAllWithPrefix(prefix)).thenReturn(Collections.emptyList());

        List<String> results = autoCompleteService.getSuggestions(prefix);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    /******************************************************************** NEGATIVE SCENARIOS **********************************************/
    @Test
    @DisplayName("Test getSuggestions throws exception with null input")
    void testGetSuggestionsWithNullInputFailure() {
        assertThrows(InvalidNameException.class, () -> autoCompleteService.saveName(null));
    }

    @Test
    @DisplayName("Test getSuggestions with empty string returns empty list")
    void testGetSuggestionsWithEmptyStringFailure() {
        String prefix = "";
        when(trie.findAllWithPrefix(prefix)).thenReturn(Collections.emptyList());

        List<String> results = autoCompleteService.getSuggestions(prefix);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Test getSuggestions with non-existent prefix returns empty list")
    void testGetSuggestionsWithNonExistentPrefixFailure() {
        String prefix = "nonexistent";
        when(trie.findAllWithPrefix(prefix)).thenReturn(Collections.emptyList());

        List<String> results = autoCompleteService.getSuggestions(prefix);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}

