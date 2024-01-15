package lab1.lab1backend.repository;

import lab1.lab1backend.model.MoviePreference;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

public interface MoviePreferenceRepository extends CassandraRepository<MoviePreference, String> {
    @AllowFiltering
    @Query("SELECT * FROM moviepreference WHERE userId = ?0 ALLOW FILTERING")
    MoviePreference findByUserId(String userId);
}
