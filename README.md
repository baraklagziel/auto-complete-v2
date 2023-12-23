# Auto-Complete Service (auto-complete-v2)

Auto-Complete Service is a Spring Boot application providing fast and efficient auto-complete suggestions for names. It's designed to preload a list of names from a file into an H2 database at startup and then offer an endpoint for querying auto-complete suggestions.

## Features

- **Fast Auto-Complete**: Utilizes a Trie data structure for efficient prefix-based searches.
- **Spring Boot Application**: Easy to run and configure with the robustness of Spring Boot.
- **H2 Database Integration**: Preloads names into an in-memory H2 database for persistence and easy querying.

## Installation

Before installing, ensure you have the following prerequisites:

- Java JDK 17 or later
- Maven (for building the project)

To set up the auto-complete service, follow these steps:

1. Clone the repository to your local machine.
2. Navigate into the project directory.
3. Use Maven to build the project: `mvn clean install`
4. Run the application: `java -jar target/auto-complete-v2-0.0.1-SNAPSHOT.jar`

## Usage

Once the application is running, it exposes an endpoint for auto-complete suggestions:


Replace `{prefix}` with the term you want suggestions for.

## Contributing

Contributions to the Auto-Complete Service are welcome. Please follow these steps to contribute:

1. Fork the repository and create your branch from `master`.
2. Make your changes and test them thoroughly.
3. Submit a pull request with a comprehensive description of changes.

## License

Please ensure to update the license section with the actual license of the project.

## Contact

Barak Lagziel - br.lagziel@gmail.com

