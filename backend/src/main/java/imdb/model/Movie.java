package imdb.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.SASI;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.Map;

@Table
@Getter
@Setter
public class Movie {

        @PrimaryKey
        private Integer movieId;
        private String moviePoster;

        @Indexed
        @SASI(indexMode = SASI.IndexMode.CONTAINS)
        @SASI.StandardAnalyzed
        private String title;

        private List<String> genres;
        private String releaseDate;
        private int duration;
        private String description;
        private float rating;
        private int votes;
        private double userProbability;
        private String director;
        private String directorPictureURL;
        private Map<String, String> actors;
        private Boolean isLiked;

        @Override
        public String toString() {
            return "Movie{" +
                    "movieId=" + movieId +
                    ", title='" + title + '\'' +
                    ", genres=" + genres +
                    ", releaseDate='" + releaseDate + '\'' +
                    ", duration=" + duration +
                    ", description='" + description + '\'' +
                    ", rating=" + rating +
                    ", votes=" + votes +
                    '}';
        }
}
