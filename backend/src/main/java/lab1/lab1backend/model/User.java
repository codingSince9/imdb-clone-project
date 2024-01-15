package lab1.lab1backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Getter
@Setter
public class User {

    @PrimaryKey
    private String id;
    private String authToken;
    private String email;
    private String firstName;
    private String lastName;
    private String name;
    private String photoUrl;
    private String provider;

}