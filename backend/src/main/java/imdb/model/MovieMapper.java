package imdb.model;

import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public static PopularMovie toPopularMovie(Movie movie) {
        PopularMovie popularMovie = new PopularMovie();

        popularMovie.setMovieId(movie.getMovieId());
        popularMovie.setMoviePoster(movie.getMoviePoster());
        popularMovie.setTitle(movie.getTitle());
        popularMovie.setGenres(movie.getGenres());
        popularMovie.setReleaseDate(movie.getReleaseDate());
        popularMovie.setDuration(movie.getDuration());
        popularMovie.setDescription(movie.getDescription());
        popularMovie.setRating(movie.getRating());
        popularMovie.setVotes(movie.getVotes());
        popularMovie.setUserProbability(movie.getUserProbability());
        popularMovie.setDirector(movie.getDirector());
        popularMovie.setDirectorPictureURL(movie.getDirectorPictureURL());
        popularMovie.setActors(movie.getActors());
        popularMovie.setIsLiked(movie.getIsLiked());

        return popularMovie;
    }
}
