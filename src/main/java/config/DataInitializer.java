package config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    public void initializeDatabase(DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"),
                new ClassPathResource("data.sql")); // if you have data in a separate file
        resourceDatabasePopulator.execute(dataSource);
    }

}