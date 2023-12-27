package com.autocomplete.autocompletev2.repository;

import com.autocomplete.autocompletev2.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NameRepository extends JpaRepository<Name, Long> {
    Optional<List<Name>> findByFirstName(String firstName);
}
