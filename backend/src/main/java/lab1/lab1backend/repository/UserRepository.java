package lab1.lab1backend.repository;

import java.util.List;

import lab1.lab1backend.model.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;


public interface UserRepository extends CassandraRepository<User, Integer> {

        @AllowFiltering
        @Query("SELECT * FROM user WHERE name = ?0 ALLOW FILTERING")
        List<User> findByName(String name);

        @AllowFiltering
        @Query("SELECT * FROM user WHERE surname = ?0 ALLOW FILTERING")
        List<User> findBySurname(String surname);

        @AllowFiltering
        @Query("SELECT * FROM user WHERE email = ?0 ALLOW FILTERING")
        User findByEmail(String email);

        @AllowFiltering
        @Query("SELECT * FROM user WHERE id = ?0 ALLOW FILTERING")
        User findById(String id);
}