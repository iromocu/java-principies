package play;

import play.content.Gender;
import play.content.Movie;
import play.content.ResumeContent;
import play.platform.Platform;
import play.utils.FileUtils;
import play.utils.MovieExistingException;
import play.utils.ScannerUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static final String VERSION = "1.0.0";
    public static final String PLATFORM = "JAVA PLAY";

    public static final int ADD = 1;
    public static final int SHOW_ALL = 2;
    public static final int SEARCH_TITLE = 3;
    public static final int SEARCH_GENDER = 4;
    public static final int POPULAR = 5;
    public static final int PLAYING = 6;
    public static final int REMOVE = 9;
    public static final int EXIT = 10;

    public static void main(String[] args) {
        System.out.println("🚀 "+ PLATFORM +"  🚀 V:" + VERSION);
        Platform platform = new Platform(PLATFORM);
        loadMovies(platform);
        System.out.println("More that " + platform.getTotalDuration() + "  content minutes." );
        while (true){
            int opcionElegida = ScannerUtils.captureInt("""
                    Select a option:
                    1. add element
                    2. Show all
                    3. search for title
                    4. search by gender
                    5. popular
                    6. play movie
                    9. delete
                    10. exit
                    """);
            switch (opcionElegida){
                case ADD ->{
                    String movieTitle = ScannerUtils.captureText("Movie Title");
                    Gender movieGender = ScannerUtils.captureGender("Movie Gender");
                    int movieDuration = ScannerUtils.captureInt("Movie Duration");
                    double movieRankin = ScannerUtils.captureDouble("Movie Rankin");
                    Movie movie1 = new Movie(movieTitle, movieDuration, movieGender, movieRankin);
                    try{
                        platform.add(movie1);
                    }catch (MovieExistingException ex){
                        System.out.println(ex.getMessage());
                    }
                }
                case SHOW_ALL ->{
                    List<ResumeContent> movies  = platform.getResumenes();
                    movies.forEach(movie -> System.out.println(movie.title()));
                }
                case SEARCH_GENDER -> {
                    Gender genderSearch = ScannerUtils.captureGender("Search by gender");
                    List<Movie> movies = platform.searchByGender(genderSearch);
                    if(movies.isEmpty()) {
                        System.out.println("The gender " + genderSearch + " has not coincidences.");
                        break;
                    }
                    System.out.println("The gender " +  genderSearch + " has " + movies.size()  + " coincidences.");
                    movies.forEach(movie -> System.out.println(movie.getInfo() + "\n"));
                }
                case SEARCH_TITLE -> {
                    String movieNameTitle = ScannerUtils.captureText("Search movie:");
                    Movie movie = platform.searchByTitle(movieNameTitle);
                    if(movie == null) {
                        System.out.println("The movie " + movieNameTitle + " has not coincidences.");
                        break;
                    }
                    System.out.println(movie.getInfo());
                }
                case POPULAR -> {
                    int limit = ScannerUtils.captureInt("Select the number of movies: ");
                    List<Movie> popular = platform.getPopularMovies(limit);
                    popular.forEach(movie -> System.out.println(movie.getInfo() + "\n"));
                }
                case PLAYING -> {
                    String movieNameTitle = ScannerUtils.captureText("Search movie:");
                    Movie movie = platform.searchByTitle(movieNameTitle);
                    if(movie == null) {
                        System.out.println("The movie " + movieNameTitle + " has not coincidences.");
                        break;
                    }
                    platform.playMovie(movie);

                }
                case REMOVE ->{
                    String movieNameTitle = ScannerUtils.captureText("Search movie to delete:");
                    Movie movie = platform.searchByTitle(movieNameTitle);
                    if(movie == null) {
                        System.out.println("The movie " + movieNameTitle + " has not coincidences.");
                        break;
                    }
                    platform.delete(movie);
                    System.out.println("The movie " + movieNameTitle + " has deleted.");
                }
                case EXIT -> System.exit(0);
            }
        }

    }

    private static void loadMovies(Platform platform) {
        platform.getContent().addAll(FileUtils.loadMovies());
    }
}
