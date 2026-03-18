package play.platform;

import play.content.Gender;
import play.content.Movie;
import play.content.ResumeContent;
import play.utils.FileUtils;
import play.utils.MovieExistingException;

import java.util.*;

public class Platform {
    private String name;
    private List<Movie> content;
    private Map<Movie, Integer> views;

    public Platform(String name) {
        this.name = name;
        this.content = new ArrayList<>();
        this.views = new HashMap<>();
    }

    public void add(Movie movie){
        Movie searchMovie = this.searchByTitle(movie.getTitle());
        if(searchMovie != null) throw new MovieExistingException(movie.getTitle());
        FileUtils.writeMovie(movie);
        this.content.add(movie);
    }

    public void playMovie(Movie movie){
        int count = views.getOrDefault(movie, 0);
        System.out.println("The movie has " + count + " views");
        this.countViews(movie);
        movie.play();
    }

    private void countViews(Movie movie){
        views.put(movie, views.getOrDefault(movie, 0) + 1);
    }

    public List<String> showMovies(){
        return content.stream()
                .map(Movie::getTitle).toList();
    }

    public List<ResumeContent> getResumenes(){
        return content.stream()
                .map(movie -> new ResumeContent(movie.getTitle(), movie.getDuration(), movie.getGender()))
                .toList();
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
