package imdb.repository;

import imdb.model.PopularMovie;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PopularMovieRepository extends CassandraRepository<PopularMovie, Integer> {

    @AllowFiltering
    @Query("SELECT * FROM popularmovie WHERE movieId = ?0 ALLOW FILTERING")
    Optional<PopularMovie> findById(Integer id);

    @AllowFiltering
    @Query("SELECT * FROM popularmovie WHERE title LIKE ?0 ALLOW FILTERING")
    List<PopularMovie> getPopularMoviesBySearch(String globalSearch);
}
