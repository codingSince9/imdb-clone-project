package lab1.lab1backend.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbReviews;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.ResultsPage;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import lab1.lab1backend.model.*;
import lab1.lab1backend.repository.MoviePreferenceRepository;
import lab1.lab1backend.repository.MovieRepository;
import lab1.lab1backend.repository.PopularMovieRepository;
import lab1.lab1backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MoviePreferenceRepository moviePreferenceRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PopularMovieRepository popularMovieRepository;

    TmdbMovies movies = new TmdbApi("65210b0eb218b694f5047ae80a00ba2f").getMovies();
    private boolean initial = true;

    public Movie initializeMovie(MovieDb movie) {
        Movie movieToStore = new Movie();
        movieToStore.setMovieId(movie.getId());
        movieToStore.setMoviePoster("https://img.sfilm.hu/w300/" + movie.getPosterPath());
        movieToStore.setTitle(movie.getTitle());
        movieToStore.setReleaseDate(movie.getReleaseDate());
        movieToStore.setDescription(movie.getOverview());
        movieToStore.setRating(movie.getVoteAverage());
        movieToStore.setVotes(movie.getVoteCount());
        movieToStore.setDuration(movies.getMovie(movieToStore.getMovieId(), "en").getRuntime());
        movieToStore.setIsLiked(false);

        // genres
        List<Genre> genres = movies.getMovie(movieToStore.getMovieId(), "en").getGenres();
        if (genres != null) {
            List<String> genreNames = new ArrayList<>();
            genres.forEach(genre -> genreNames.add(genre.getName()));
            movieToStore.setGenres(genreNames);
        } else {
            movieToStore.setGenres(new ArrayList<>(Collections.singletonList("No genres")));
        }

        // director and cast
        Credits credits = movies.getCredits(movieToStore.getMovieId());
        if (credits != null) {
            List<PersonCast> cast = credits.getCast();
            for (PersonCast person : cast) {
                if (person.getCharacter() != null && person.getProfilePath().length() != 0) {
                    String pictureUrl = "https://img.sfilm.hu/w300/" + person.getProfilePath();
                    Map<String, String> actors = movieToStore.getActors() != null ? movieToStore.getActors() : new HashMap<>();
                    actors.put(person.getName(), pictureUrl);
                    movieToStore.setActors(actors);
                }
            }
            List<PersonCrew> crew = credits.getCrew();
            for (PersonCrew person : crew) {
                if (person.getJob().equals("Director")) {
                    movieToStore.setDirector(person.getName());
                    movieToStore.setDirectorPictureURL("https://img.sfilm.hu/w300/" + person.getProfilePath());
                    break;
                }
            }
        }

        // reviews
        List<Reviews> reviews1 = movies.getMovie(movieToStore.getMovieId(), "en", TmdbMovies.MovieMethod.reviews).getReviews();
        if (reviews1 != null) {
            for (Reviews review : reviews1) {
                Review reviewToStore = new Review();
                reviewToStore.setUserName(review.getAuthor());
                reviewToStore.setComment(review.getContent());
                reviewToStore.setMovieId(movieToStore.getMovieId());
                reviewRepository.save(reviewToStore);
            }
        }
        return movieToStore;
    }

    @PostConstruct
    public void generateData() {
        System.out.println("Hello, World!");

        // check if there are any movies in the repository
        if (movieRepository.count() == 0) {
            List<Movie> moviesToStore = new ArrayList<>();
            // initiate counter for time
            long startTime = System.currentTimeMillis();
            for (int i = 1; i <= 100; i++) {
                System.out.println(i);
                MovieResultsPage movieResultsPage = movies.getTopRatedMovies("en", i);
                System.out.println(i + ",1 generateData");

                List<MovieDb> moviesResult = movieResultsPage.getResults();
                for(int j = 0; j < moviesResult.size(); j++) {
                    Movie movieToStore = initializeMovie(moviesResult.get(j));
                    moviesToStore.add(movieToStore);
                }
                /*for (MovieDb movie : moviesResult) {
                    Movie movieToStore = initializeMovie(movie);
                    moviesToStore.add(movieToStore);
//                    movieRepository.save(movieToStore);
                }*/
                System.out.println(i + ",2 generateData");

            }
            System.out.println("Prije spremanja generatedata");

            movieRepository.saveAll(moviesToStore);
            long endTime = System.currentTimeMillis();
            System.out.println("Time to generate data: " + (endTime - startTime) + "ms");
        }
    }

    @GetMapping("/search/{globalSearch}")
    public List<Movie> getMoviesBySearch(@PathVariable String globalSearch) {
        String globalSearchForDB = "%" + globalSearch + "%";
        return movieRepository.getMoviesBySearch(globalSearchForDB);
    }

    @GetMapping("/search/popular/{globalSearch}")
    public List<PopularMovie> getPopularMoviesBySearch(@PathVariable String globalSearch) {
        String globalSearchForDB = "%" + globalSearch + "%";
        return popularMovieRepository.getPopularMoviesBySearch(globalSearchForDB);
    }

    @PostConstruct
    public void savePopularMovies() {
        if(popularMovieRepository.count()!=0) return;
        List<MovieDb> popularMovies = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            System.out.println(i);
            popularMovies.addAll(movies.getPopularMovies("en-US", i).getResults());
        }
        List<PopularMovie> popularMoviesToStore = new ArrayList<>();
        int k = 0;
        for (MovieDb movieDb : popularMovies) {
            System.out.println("Mapper " + k++);
            Movie movie = initializeMovie(movieDb);
            PopularMovie popularMovie = MovieMapper.toPopularMovie(movie);
            popularMoviesToStore.add(popularMovie);
//            popularMovieRepository.save(popularMovie);
        }
        System.out.println("Prije spremanja popularmovies");
        popularMovieRepository.saveAll(popularMoviesToStore);
        System.out.println("Poslije spremanja popularmovies");

    }

    @GetMapping("/popular")
    public List<PopularMovie> getPopularMovies() {
        return popularMovieRepository.findAll();
    }

    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/similarMovies/{movieId}")
    public List<Movie> getSimilarMovies(@PathVariable int movieId) {
        List<MovieDb> similarMovies = movies.getSimilarMovies(movieId, "en", 1).getResults();
        similarMovies = similarMovies.subList(0, 5);
        List<Movie> movieList = new ArrayList<>();
        for (MovieDb movieDb: similarMovies) {
            Movie movie = initializeMovie(movieDb);
            if (!movieRepository.findById(movie.getMovieId()).isPresent()) {
                movieRepository.save(movie);
            }
            movieList.add(movie);
        }
        return movieList;
    }

    @GetMapping("/reviews/{movieId}")
    public List<Review> getReviews(@PathVariable int movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    @GetMapping("/movieVideos/{movieId}")
    public List<String> getMovieVideos(@PathVariable int movieId) {
        List<String> videos = new ArrayList<>();
        for (Video video : movies.getVideos(movieId, "en")) {
            if (video.getSite().equals("YouTube") && video.getName().toLowerCase().contains("trailer")) {
                videos.add("https://www.youtube.com/watch?v=" + video.getKey());
            }
        }
        if (videos.size() == 0) {
            String[] movieName = movies.getMovie(movieId, "en").getTitle().split(" ");
            videos.add("https://www.youtube.com/results?search_query=" + String.join("+", movieName) + "trailer");
        }
        return videos;
    }

    @GetMapping("/getRandomMovies")
    public List<Movie> getRandomMovies() {
        List<Movie> allMovies = movieRepository.findAll();
        Collections.shuffle(allMovies);
        return allMovies.subList(0, 20);
    }

    @PostMapping("/updateSuggestions/{userId}")
    public void updateSuggestions(@PathVariable(value = "userId") String userId, @RequestBody Movie movieDetails) {
        Movie movie = movieRepository.findById(movieDetails.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        List<String> genres = movie.getGenres();
        if (moviePreferenceRepository.findByUserId(userId) == null) {
            MoviePreference moviePreference = new MoviePreference();
            moviePreference.setUserId(userId);
            moviePreference.setRating(movieRepository.findById(movieDetails.getMovieId()).get().getRating());
            Map<String, Integer> genreMap = new HashMap<>();
            genres.forEach(genre -> genreMap.put(genre, 1));
            moviePreference.setGenrePreference(genreMap);
            Map<String, Integer> directorMap = new HashMap<>();
            directorMap.put(movie.getDirector(), 1);
            moviePreference.setDirectorPreference(directorMap);
            moviePreferenceRepository.save(moviePreference);
        } else {
            // rating update
            MoviePreference moviePreference = moviePreferenceRepository.findByUserId(userId);
            double currentRating = moviePreference.getRating();
            double newRating = (currentRating + movieRepository.findById(movieDetails.getMovieId()).get().getRating()) / 2;
            moviePreference.setRating(newRating);

            // genre preference
            Map<String, Integer> genreMap = moviePreference.getGenrePreference();
            for (String genre : genres) {
                if (genreMap.containsKey(genre)) {
                    genreMap.put(genre, movieDetails.getIsLiked() ? genreMap.get(genre) + 1 : genreMap.get(genre) - 1);
                } else {
                    genreMap.put(genre, 1);
                }
            }
            moviePreference.setGenrePreference(genreMap);

            // director preference
            Map<String, Integer> directorMap = moviePreference.getDirectorPreference();
            if (directorMap.containsKey(movie.getDirector())) {
                directorMap.put(movie.getDirector(), movieDetails.getIsLiked() ? directorMap.get(movie.getDirector()) + 1 : directorMap.get(movie.getDirector()) - 1);
            } else {
                directorMap.put(movie.getDirector(), 1);
            }
            moviePreference.setDirectorPreference(directorMap);
            moviePreferenceRepository.save(moviePreference);
        }
    }

    private double calculateProbability(Movie movie, MoviePreference moviePreference) {

        Map<String, Integer> genreMap = moviePreference.getGenrePreference();
        Map<String, Integer> directorMap = moviePreference.getDirectorPreference();
        double probability = 0;
//        System.out.println(movie.getGenres());
        if (!Objects.equals(movie.getGenres().get(0), "No genres")) {
            for (String genre : movie.getGenres()) {
                if (genreMap.containsKey(genre)) {
                    // if the genre is the highest rated, increase the probability 3 times
                    if (Objects.equals(genreMap.get(genre), Collections.max(genreMap.values()))) {
                        probability += 2;
                    }
                    // for second-highest rated genre, increase the probability by 2
                    else if (Objects.equals(genreMap.get(genre), Collections.max(genreMap.values()) - 1)) {
                        probability += 1;
                    }
                    // for third-highest rated genre, increase the probability by 1
                    else if (Objects.equals(genreMap.get(genre), Collections.max(genreMap.values()) - 2)) {
                        probability += 0.5;
                    } else {
                        probability += 0.25;
                    }
                } else {
                    probability -= 0.125;
                }
            }
        }

        // sort directorMap by value
        List<Map.Entry<String, Integer>> sortedDirectorMap = new ArrayList<>(directorMap.entrySet());
        sortedDirectorMap.sort(Map.Entry.comparingByValue());
        Collections.reverse(sortedDirectorMap);

        // if the director is the highest rated, increase the probability 3 times
        if (Objects.equals(directorMap.get(movie.getDirector()), sortedDirectorMap.get(0).getValue())) {
            probability += 3;
        }
        // for second-highest rated director, increase the probability by 2
        else if (sortedDirectorMap.size() >= 2 && Objects.equals(directorMap.get(movie.getDirector()), sortedDirectorMap.get(1).getValue())) {
            probability += 2;
        }
        // for third-highest rated director, increase the probability by 1
        else if (sortedDirectorMap.size() >= 3 && Objects.equals(directorMap.get(movie.getDirector()), sortedDirectorMap.get(2).getValue())) {
            probability += 1;
        }
        else {
            probability += 0.5;
        }

        if (movie.getRating() > moviePreference.getRating()) {
            probability += 0.5;
        }
        return probability;
    }

    @GetMapping("/getSuggestions/{userId}")
    public List<Movie> getSuggestions(@PathVariable(value = "userId") String userId) {
        if(moviePreferenceRepository.count() == 0) return new ArrayList<>();
        MoviePreference moviePreference = moviePreferenceRepository.findByUserId(userId);
        List<Movie> movieList = movieRepository.findAll();
        List<Movie> suggestedMovies = new ArrayList<>();

        for (Movie movie : movieList) {
            if (!movie.getIsLiked() && movie.getGenres() != null) {
                double probability = calculateProbability(movie, moviePreference);
                movie.setUserProbability(probability);
                suggestedMovies.add(movie);
            }
        }
        suggestedMovies.sort((Movie m1, Movie m2) -> Double.compare(m2.getUserProbability(), m1.getUserProbability()));
        return suggestedMovies;
    }

    @GetMapping("/getFacebookMovies/{userId}/{authToken}")
    public List<Movie> getFacebookMovies(@PathVariable String userId, @PathVariable String authToken) throws JsonProcessingException {
        List<Movie> facebookMovies = new ArrayList<>();
        String accessToken = "EAANcQWsbgSYBAEvCxE6k9DYhKykGKscQdGGoZCZAoJkUObRPCbUNbpmfcyxXMlXRt2ZCuuchFVi0RdydBDbHHTZCXqMp28FqM6AcNZBPcqh6Mw2wZBiScUE6GTpkqSiS5onbJEh37ya68Rwh7YVYWZC5bFxfDFthf8p3QHLCp28zAZDZD";
        String url = "https://graph.facebook.com/" + userId + "/movies?access_token=" + accessToken;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode data = root.path("data");
        for (JsonNode node : data) {
            long id = node.path("id").asLong();
            String name = node.path("name").asText();
            Movie movie = new Movie();
            movie.setMovieId((int) id);
            movie.setTitle(name);
            facebookMovies.add(movie);
        }
        System.out.println(facebookMovies);
        return facebookMovies;
    }

    @GetMapping("/suggestionExist/{userId}")
    public boolean suggestionExist(@PathVariable(value = "userId") String userId) {
        return moviePreferenceRepository.findByUserId(userId) != null ? true : false;
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable(value = "id") int movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie " + "id " + movieId));
    }

    @GetMapping("/popular/{id}")
    public PopularMovie getPopularMovieById(@PathVariable(value = "id") int movieId) {
        return popularMovieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie " + "id " + movieId));
    }

    @GetMapping("/title/{title}")
    public List<Movie> getMoviesByTitle(@PathVariable(value = "title") String title) {
        return movieRepository.findByTitle(title);
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable(value = "genre") String genre) {
        return movieRepository.findByGenre(genre);
    }

    @GetMapping("/rating/{rating}")
    public List<Movie> getMoviesByRating(@PathVariable(value = "rating") float rating) {
        return movieRepository.findByRating(rating);
    }

    @GetMapping("/director/{director}")
    public List<Movie> getMoviesByDirector(@PathVariable(value = "director") String director) {
        return movieRepository.findByDirector(director);
    }

    @PostMapping("/add")
    public Movie createMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @PutMapping("/update/{id}")
    public Movie updateMovie(@PathVariable(value = "id") int movieId, @RequestBody Movie movieDetails) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie " + "id " + movieId));
        movie.setIsLiked(movieDetails.getIsLiked());

        return movieRepository.save(movie);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable(value = "id") int movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie " + "id " + movieId));

        movieRepository.delete(movie);
    }
}