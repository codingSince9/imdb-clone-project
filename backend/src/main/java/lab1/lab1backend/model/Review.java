package lab1.lab1backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Getter
@Setter
public class Review {

    @PrimaryKey
    private String userName;
    private int movieId;
    private double rating;
    private String comment;
    private String pictureURL;

}