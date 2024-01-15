package lab1.lab1backend.repository;

import lab1.lab1backend.model.Review;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface ReviewRepository extends CassandraRepository<Review, String> {
    @AllowFiltering
    @Query("SELECT * FROM review WHERE user_name = ?0 ALLOW FILTERING")
    Review findByUserName(String userName);

    @AllowFiltering
    @Query("SELECT * FROM review WHERE movieId = ?0 ALLOW FILTERING")
    List<Review> findByMovieId(int movieId);
}
