
# Audio Library PAO

This project is a Spring Boot application designed for managing audio tracks, playlists, and user authentication. It includes features such as logging HTTP requests, user registration, user login, and JWT token generation.

## Features

- **Track Management**: Add and manage tracks.
- **Playlist Management**: Create and manage playlists, add tracks to playlists.
- **User Authentication**: Register and authenticate users using JWT tokens.
- **Request Logging**: Log HTTP requests and responses.

## Prerequisites
- Maven
- Docker
- Docker Compose

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/victorstefan28/audiolibrarypao.git
cd audiolibrarypao
```

### Configure the Application

1. Copy the example properties file:

    ```bash
    cp src/main/resources/application.example.properties src/main/resources/application.properties
    ```

2. Edit the `application.properties` file to configure your database and other settings.

### Running the Database with Docker Compose

Ensure you have Docker and Docker Compose installed. Then, use the provided `docker-compose.yml` file to set up the database:

```bash
docker-compose up -d
```

This will start a PostgreSQL database container with the configuration specified in the `docker-compose.yml` file.

### Build and Run the Application

#### Using Maven

1. Build the project:

    ```bash
    mvn clean install
    ```

2. Run the application:

    ```bash
    mvn spring-boot:run
    ```

#### `PlaylistService`

- **listPlaylists(Pageable pageable)**: Retrieves a paginated list of playlists.
- **createPlaylist(String name, String description, User user)**: Creates a new playlist.
- **addTrackToPlaylist(Long playlistId, Long trackId, User user)**: Adds a track to a playlist.
- **addTracksToPlaylist(Long playlistId, List<Long> trackIds, User user)**: Adds multiple tracks to a playlist.

#### `LoggingService`

- **logRequest(String requestURI, String user, int responseCode, String body, String payload)**: Logs a request.
- **getAuditLogs(Pageable pageable, String username)**: Retrieves a paginated list of request logs.

#### `JwtService`

- **extractUsername(String token)**: Extracts the username from a JWT token.
- **extractUser(String token)**: Extracts the user details from the JWT token.
- **isTokenValid(String token, UserDetails userDetails)**: Validates the JWT token against the user details.
- **generateToken(UserDetails userDetails, User user)**: Generates a JWT token for the specified user.
- **generateToken(UserDetails userDetails)**: Generates a JWT token for the specified user details.

#### `AuthService`

- **registerUser(User user)**: Registers a new user.
- **loginUser(LoginRequest loginRequest)**: Logs in a user.
- **generateToken(User user)**: Generates a JWT token for the specified user.
- **saveUserToken(User user, String jwtToken)**: Saves the user's token to the repository.

#### `LoggingFilter`

- **init(FilterConfig filterConfig)**: Initializes the filter configuration.
- **doFilter(ServletRequest request, ServletResponse response, FilterChain chain)**: Filters the incoming requests and logs the request details.
- **destroy()**: Destroys the filter.
- **logRequestDetails(HttpServletRequest request, HttpServletResponse response)**: Logs the details of the request.
- **extractUserFromJWT(HttpServletRequest request)**: Extracts the user from the JWT token in the request.
- **parseJWT(String token)**: Parses the JWT token to extract the user details.
- **saveLogToDatabase(String requestURI, String user, int responseCode, String body, String payload)**: Saves the log details to the database.

