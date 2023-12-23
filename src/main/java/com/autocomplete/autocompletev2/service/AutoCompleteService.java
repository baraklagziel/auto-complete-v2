package com.autocomplete.autocompletev2.service;

import com.autocomplete.autocompletev2.model.Name;
import com.autocomplete.autocompletev2.model.Trie;
import com.autocomplete.autocompletev2.repository.NameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoCompleteService {

    private final Trie trie;
    @Autowired
    private final NameRepository nameRepository;

    public AutoCompleteService(Trie trie, NameRepository nameRepository) {
        this.trie = trie;
        this.nameRepository = nameRepository;
    }

    @PostConstruct
    private void preloadNames()  {
        List<Name> names = nameRepository.findAll();
        for (Name name : names) {
            trie.insert(name.getFirstName());
        }
    }


    public void saveName(String name) {
        Name newName = new Name();
        newName.setFirstName(name);
        nameRepository.save(newName);
    }
    public List<String> getSuggestions(String prefix) {
        return trie.findAllWithPrefix(prefix);
    }
}
