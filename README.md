# IMDB Clone

This is an IMDB clone application with a Cassandra database, backend written in Spring Boot and a frontend in Angular.

## Features

- Browse movies and TV shows
- Search for specific movies and TV shows
- Get personalized recommendations
- View the closest movie theaters
- View movie and TV show details

## Technologies Used

- Backend: Spring Boot
- Frontend: Angular

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Node.js
- Angular CLI

### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/codingSince9/imdb-clone-project.git
   ```

2. Database setup:

   - Open bash on your operating system and initiate the cassandra bash

   ```shell
   cqlsh
   > CREATE KEYSPACE imdb WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};
   > use keyspace imdb
   ```

3. Backend setup:

   - Open the `backend` directory in your preferred IDE.
   - Configure the TMDB API and Facebook API keys in the application configuration.
   - Key/Value pairs should be as following:
     key -> tmdbApi; value -> API_KEY
     key -> facebookApi; value -> API_KEY
   - Build and run the Spring Boot application.

4. Frontend setup:

   - Open the `frontend` directory in your preferred code editor.
   - Install the dependencies:

     ```shell
     npm install
     ```

   - Start the Angular development server:

     ```shell
     ng serve
     ```

5. Open your web browser and navigate to `http://localhost:4200` to access the application.

## License

This project is licensed under the [MIT License](./LICENSE).
