package play.platform;

import play.content.Gender;
import play.content.Movie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Platform {
    private String name;
    private List<Movie> content;

    public Platform(String name) {
        this.name = name;
        this.content = new ArrayList<>();
    }

    public void add(Movie movie){
        this.content.add(movie);
    }
    public List<String> showMovies(){
        return content.stream()
                .map(Movie::getTitle).toList();
    }
    public void delete(Movie movie){
        this.content.remove(movie);
    }

    public Movie searchByTitle(String title){
       return content.stream().filter(
               movie -> movie.getTitle().equalsIgnoreCase(title)
                ).findFirst()
               .orElse(null);
    }

    public List<Movie> searchByGender(Gender gender){
        return content.stream().filter(
                movie -> movie.getGender().equals(gender)
        ).toList();
    }

    public int getTotalDuration(){
        return content.stream()
                .mapToInt(Movie::getDuration)
                .sum();
    }

    public List<Movie> getPopularMovies(int limit){
        return content.stream()
                .sorted(Comparator.comparingDouble(Movie::getRankin).reversed())
                .limit(limit)
                .toList();
    }
    public String getName() {
        return name;
    }

    public List<Movie> getContent() {
        return content;
    }
}
