package com.autocomplete.autocompletev2.repository;

import com.autocomplete.autocompletev2.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NameRepository extends JpaRepository<Name, Long> {
    // Custom query methods if needed
}
