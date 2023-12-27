package com.autocomplete.autocompletev2.service;

import com.autocomplete.autocompletev2.exceptions.InvalidNameException;
import com.autocomplete.autocompletev2.model.Name;
import com.autocomplete.autocompletev2.utils.Trie;
import com.autocomplete.autocompletev2.repository.NameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.autocomplete.autocompletev2.exceptions.NameNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
public class AutoCompleteService {

    private static final int MAX_ITEMS = 1000;
    private static final String MAX_ITEM_MESSAGE =
            "Too many results to display. Please refine your search.";
    private final Trie trie;
    private final NameRepository nameRepository;

    public AutoCompleteService(Trie trie, NameRepository nameRepository) {
        this.trie = trie;
        this.nameRepository = nameRepository;
    }


    /**
     * Preloads names from the repository into the Trie upon initialization of the bean.
     * This method is automatically called after the bean's properties have been set and
     * the bean is fully constructed. It retrieves all names from the repository and inserts
     * them into the Trie data structure for efficient auto-completion functionality.
     * This method is marked with @PostConstruct to indicate that it should be run after
     * the bean's construction and dependency injection are complete.
     */
    @PostConstruct
    private void preloadNames()  {
        List<Name> names = nameRepository.findAll();
        for (Name name : names) {
            trie.insert(name.getFirstName());
        }
    }


    /**
     * Saves the provided name into the repository. The name must meet
     * certain criteria to be considered valid, such as length and character content.
     * If the name is valid, it is saved, and its representation is returned.
     *
     * @param name the name to be saved.
     * @throws InvalidNameException if the name provided is invalid according to predefined rules.
     */
    public String saveName(String name) throws InvalidNameException{
        this.validateName(name);

        Name newName = new Name(name);
        newName.setFirstName(name);
        nameRepository.save(newName);

        return newName.getFirstName();
    }
    public List<String> getSuggestions(String prefix) {
        var suggestions =  trie.findAllWithPrefix(prefix);
        // Check if the number of suggestions is greater than 1000 for heavy loading of results
        if (suggestions.size() > MAX_ITEMS) {
            return List.of(MAX_ITEM_MESSAGE);
        }

        return suggestions;
    }

    /**
     * Deletes a name from the repository. If the name exists, it is removed,
     * and the operation completes successfully. If the name does not exist,
     * a NameNotFoundException is thrown.
     *
     * @param name the name to be deleted.
     * @throws NameNotFoundException if the name does not exist in the repository.
     */
    public int deleteName(Name name) throws NameNotFoundException {
        Optional<List<Name>> namesToDelete = nameRepository.findByFirstName(name.getFirstName());
        if (namesToDelete.isPresent()) {
             nameRepository.deleteAll(namesToDelete.get());
        } else {
            throw new NameNotFoundException("Name not found: " + name.getFirstName());
        }

        return namesToDelete.get().size();
    }

    /**
     * Deletes the name associated with the provided ID from the repository.
     * If the name exists, it is removed, and the operation is considered successful.
     * If no name is found with the provided ID, a NameNotFoundException is thrown.
     *
     * @param id the ID of the name to be deleted.
     * @return the count of deleted items, typically 1 if successful, 0 otherwise.
     * @throws NameNotFoundException if no name is found with the provided ID.
     */
    public int deleteNameById(Long id) throws NameNotFoundException {
        // Check if name with such ID exists
        Optional<Name> nameOptional = nameRepository.findById(id);
        if(nameOptional.isPresent()) {
            nameRepository.deleteById(id);
            return 1; // Return 1 to indicate one item deleted
        } else {
            throw new NameNotFoundException("No name found with ID: " + id);
        }
    }

    /**
     * Deletes all names with a given prefix from the repository and returns the total number of names deleted.
     *
     * @param prefix The prefix to match names against for deletion.
     * @return The total number of names deleted.
     */
    public int deleteNameByPrefix(final String prefix) {
        var deletedItems = trie.findAllWithPrefix(prefix);
        int deletedSum = 0;
        for (String name : deletedItems) {
                deletedSum++; // Increment deletedSum by the size of the list
                nameRepository.delete(new Name(name));
        }

        return deletedSum;
    }


    /********************************************************* PRIVATE METHODS ***********************************************************/

    /**
     * Validates a name based on defined criteria such as length and character content.
     * This method is used to ensure that only appropriate names are processed for suggestions.
     *
     * @param name the name to validate.
     */
    public void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException("Invalid name provided: " + name);
        }

        if (name.length() < 2 || name.length() > 50) {
            throw new InvalidNameException("Invalid name provided: " + name);
        }

        if (!name.matches("^[A-Za-z]+$")) {
            throw new InvalidNameException("Invalid name provided: " + name);
        }
    }
}
