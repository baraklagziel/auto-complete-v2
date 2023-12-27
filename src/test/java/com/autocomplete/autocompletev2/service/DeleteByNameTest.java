package com.autocomplete.autocompletev2.service;

import com.autocomplete.autocompletev2.exceptions.NameNotFoundException;
import com.autocomplete.autocompletev2.model.Name;
import com.autocomplete.autocompletev2.repository.NameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DeleteByNameTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NameRepository nameRepository;
    @Mock
    private AutoCompleteService autoCompleteService;


    @BeforeEach
    public void setUp() {
        autoCompleteService.saveName("John Doe"); // Simulating cache population
    }
    /******************************************************************** POSITIVE SCENARIOS **********************************************/

    @Test
    @DisplayName("When Deleting by Name, Succeeds and Returns OK")
    void whenDeleteByNameSuccess() throws Exception {
        String nameToDelete = "John Doe";
        int deletedItemsCount = 1;

        when(nameRepository.findByFirstName(nameToDelete)).thenReturn(Optional.of(List.of(new Name (nameToDelete))));

        mockMvc.perform(delete("/v1/names/by-name/{name}", nameToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("%d items deleted for names: %s", deletedItemsCount, nameToDelete)));
    }

    /******************************************************************** NEGATIVE SCENARIOS **********************************************/

    @Test
    @DisplayName("When Deleting by Name, Fails for Nonexistent Name and Returns Not Found")
    void whenDeleteByNameFailureNotExistNameFailure() throws Exception {
        String nameToDelete = "Not exist";
        String expectedErrorMessage = "Name not found: " + nameToDelete;


        when(autoCompleteService.deleteName(new Name(nameToDelete))).thenThrow(new NameNotFoundException(expectedErrorMessage));

        mockMvc.perform(delete("/v1/names/by-name/{name}", nameToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrorMessage)); // Asserting the exception message
    }
}
