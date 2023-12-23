package config;

import com.autocomplete.autocompletev2.model.Name;
import com.autocomplete.autocompletev2.repository.NameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class DataInitializer {
    private final NameRepository nameRepository;

    @Autowired
    public DataInitializer(final NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @PostConstruct
    public void loadData() {
        try {
            // Assuming the file is placed in the resources folder
            InputStream in = getClass().getResourceAsStream("/BoyNames.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // Read each line from the file and save it to the database
            String name;
            while ((name = reader.readLine()) != null) {
                Name nameEntity = new Name();
                nameEntity.setFirstName(name);
                nameRepository.save(nameEntity);
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
        }
    }
}