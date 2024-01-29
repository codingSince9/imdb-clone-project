package imdb.repository;

import imdb.model.MoviePreference;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

public interface MoviePreferenceRepository extends CassandraRepository<MoviePreference, String> {
    @AllowFiltering
    @Query("SELECT * FROM moviepreference WHERE userId = ?0 ALLOW FILTERING")
    MoviePreference findByUserId(String userId);
}
