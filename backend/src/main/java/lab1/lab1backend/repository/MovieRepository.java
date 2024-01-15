package lab1.lab1backend.repository;

import lab1.lab1backend.model.Movie;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends CassandraRepository<Movie, Integer> {

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE title = ?0 ALLOW FILTERING")
    List<Movie> findByTitle(String title);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE director = ?0 ALLOW FILTERING")
    List<Movie> findByDirector(String director);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE genre = ?0 ALLOW FILTERING")
    List<Movie> findByGenre(String genre);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE year = ?0 ALLOW FILTERING")
    List<Movie> findByYear(int year);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE duration = ?0 ALLOW FILTERING")
    List<Movie> findByDuration(int duration);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE description = ?0 ALLOW FILTERING")
    List<Movie> findByDescription(String description);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE rating > ?0 ALLOW FILTERING")
    List<Movie> findByRating(float rating);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE movieId = ?0 ALLOW FILTERING")
    Optional<Movie> findById(Integer id);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE title LIKE ?0 ALLOW FILTERING")
    List<Movie> getMoviesBySearch(String globalSearch);

    @AllowFiltering
    @Query("SELECT * FROM movie WHERE title LIKE ?0 AND isLiked = false ALLOW FILTERING")
    List<Movie> getNotLikedMoviesBySearch(String globalSearch);
}
