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


## Running with Docker

You can run this Spring Boot application in a Docker container. Follow these steps:

1. Build the Docker image:

   ```bash
   docker build -t auto-complete -f src/Dockerfile . --no-cache
2. ```bash 
   docker run -p 9093:8080 auto-complete
3. ```bash
   docker stop $(docker ps -q --filter ancestor=autocomplete-v2)

- **Note: Make sure you have Docker installed on your machine to run the application using Docker.**

Feel free to customize the instructions to fit your project's specific needs.
# AutoComplete API

The AutoComplete API provides a set of endpoints for creating, retrieving, and deleting names, especially focusing on auto-completion functionalities. This document outlines the available endpoints and their usage.
 
## API Endpoints

### 1. Get Auto-complete Suggestions

- **Endpoint**: `GET /v1/autocomplete`
- **Description**: Retrieves a list of auto-completion suggestions based on the given prefix. Returns up to 1000 names.
- **Parameters**:
   - `prefix`: The prefix string to search for.

### 2. Create a New Name

- **Endpoint**: `POST /v1/names`
- **Description**: Creates a new name in the system.
- **Body**: A plain text string with the name to be saved.

### 3. Delete Name by Exact Match

- **Endpoint**: `DELETE /v1/names/by-name/{name}`
- **Description**: Deletes a name based on an exact match.
- **Path Variable**:
   - `name`: The exact name to delete.

### 4. Delete Name by ID

- **Endpoint**: `DELETE /v1/names/{id}`
- **Description**: Deletes a name based on its unique identifier.
- **Path Variable**:
   - `id`: The unique identifier of the name to delete.

### 5. Delete Names by Prefix

- **Endpoint**: `DELETE /v1/names/prefix/{prefix}`
- **Description**: Deletes names that start with the specified prefix.
- **Path Variable**:
   - `prefix`: The prefix of the names to delete.


## Contributing

Contributions to the Auto-Complete Service are welcome. Please follow these steps to contribute:

1. Fork the repository and create your branch from `master`.
2. Make your changes and test them thoroughly.
3. Submit a pull request with a comprehensive description of changes.

## License

Please ensure to update the license section with the actual license of the project.

## Contact

Barak Lagziel - br.lagziel@gmail.com

