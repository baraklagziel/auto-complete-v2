package com.autocomplete.autocompletev2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import com.autocomplete.autocompletev2.controller.AutoCompleteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
 class CreateNameTest {

    @Mock
    private AutoCompleteService autoCompleteService;

    @InjectMocks
    private AutoCompleteController autoCompleteController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(autoCompleteController).build();
    }

    @Test
    @DisplayName("Create Name with Valid Name Returns Created")
    void createNameValidNameReturnsCreatedSuccess() throws Exception {
        String name = "Barak Lagziel";
        when(autoCompleteService.saveName(anyString())).thenReturn(name);

        mockMvc.perform(post("/v1/names")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + name + "\"")) // ensure the content is a JSON string
                .andExpect(status().isCreated())
                .andExpect(result -> assertEquals(name, result.getResponse().getContentAsString().replace("\"","")));
    }
}

