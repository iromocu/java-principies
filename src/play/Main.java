package play;

import play.content.Gender;
import play.content.Movie;
import play.platform.Platform;
import play.utils.ScannerUtils;

import java.util.List;

public class Main {

    public static final String VERSION = "1.0.0";
    public static final String PLATFORM = "JAVA PLAY";

    public static final int ADD = 1;
    public static final int SHOW_ALL = 2;
    public static final int SEARCH_TITLE = 3;
    public static final int SEARCH_GENDER = 4;
    public static final int POPULAR = 5;
    public static final int REMOVE = 6;
    public static final int EXIT = 7;

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
                    6. delete
                    7. exit
                    """);
            switch (opcionElegida){
                case ADD ->{
                    String movieTitle = ScannerUtils.captureText("Movie Title");
                    String movieGender = ScannerUtils.captureText("Movie Gender");
                    int movieDuration = ScannerUtils.captureInt("Movie Duration");
                    double movieRankin = ScannerUtils.captureDouble("Movie Rankin");
                    Movie movie1 = new Movie(movieTitle, movieDuration, Gender.ANIME, movieRankin);
                    platform.add(movie1);
                }
                case SHOW_ALL ->{
                    List<String> titles = platform.showMovies();
                    titles.forEach(System.out::println);
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

    private static void loadMovies(Platform plataforma) {
        plataforma.add(new Movie("Shrek", 90, Gender.ANIME));
        plataforma.add(new Movie("Inception", 148, Gender.SCI_FY));
        plataforma.add(new Movie("Titanic", 195, Gender.DRAMA, 4.6));
        plataforma.add(new Movie("John Wick", 101, Gender.ACTION));
        plataforma.add(new Movie("El Conjuro", 112, Gender.TERROR, 3.0));
        plataforma.add(new Movie("Coco", 105, Gender.ANIME, 4.7));
        plataforma.add(new Movie("Interstellar", 169, Gender.SCI_FY, 5));
        plataforma.add(new Movie("Joker", 122, Gender.DRAMA));
        plataforma.add(new Movie("Toy Story", 81, Gender.ANIME, 4.5));
        plataforma.add(new Movie("Avengers: Endgame", 181, Gender.ACTION, 3.9));
    }
}
