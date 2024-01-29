package imdb.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;

@Table
@Getter
@Setter
public class MoviePreference {

    @PrimaryKey
    private String userId;
    private double rating;
    private Map<String, Integer> genrePreference;
    private Map<String, Integer> directorPreference;

}
