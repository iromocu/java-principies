package play;

import play.content.*;
import play.platform.Platform;
import play.utils.FileUtils;
import play.utils.MovieExistingException;
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
    public static final int PLAYING = 6;
    public static final int SEARCH_TYPE = 7;
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
                    6. play content
                    7. search for type
                    9. delete
                    10. exit
                    """);
            switch (opcionElegida){
                case ADD ->{
                    int contentType = ScannerUtils.captureInt("""
                            1. Movie
                            2. Documentary
                            """);
                    String title = ScannerUtils.captureText("Content Title");
                    Gender gender = ScannerUtils.captureGender("Content Gender");
                    int duration = ScannerUtils.captureInt("Content Duration");
                    double rankin = ScannerUtils.captureDouble("Content Rankin");
                    try{
                        if(contentType == 1){
                            platform.add(new Movie(title, duration, gender, rankin));
                        }else if( contentType == 2){
                            String announcer = ScannerUtils.captureText("Announcer");
                            platform.add(new Documentary(title, duration, gender, rankin, announcer));
                        }
                    }catch (MovieExistingException ex){
                        System.out.println(ex.getMessage());
                    }
                }
                case SHOW_ALL ->{
                    List<ResumeContent> movies  = platform.getResumenes();
                    movies.forEach(content -> System.out.println(content.title()));
                }
                case SEARCH_GENDER -> {
                    Gender genderSearch = ScannerUtils.captureGender("Search by gender");
                    List<Content> contents = platform.searchByGender(genderSearch);
                    if(contents.isEmpty()) {
                        System.out.println("The gender " + genderSearch + " has not coincidences.");
                        break;
                    }
                    System.out.println("The gender " +  genderSearch + " has " + contents.size()  + " coincidences.");
                    contents.forEach(content -> System.out.println(content.getInfo() + "\n"));
                }
                case SEARCH_TITLE -> {
                    String movieNameTitle = ScannerUtils.captureText("Search content:");
                    Content content = platform.searchByTitle(movieNameTitle);
                    if(content == null) {
                        System.out.println("The content " + movieNameTitle + " has not coincidences.");
                        break;
                    }
                    System.out.println(content.getInfo());
                }
                case POPULAR -> {
                    int limit = ScannerUtils.captureInt("Select the number of movies: ");
                    List<Content> popular = platform.getPopularMovies(limit);
                    popular.forEach(content -> System.out.println(content.getInfo() + "\n"));
                }
                case PLAYING -> {
                    String movieNameTitle = ScannerUtils.captureText("Search content:");
                    Content content = platform.searchByTitle(movieNameTitle);
                    if(content == null) {
                        System.out.println("The content " + movieNameTitle + " has not coincidences.");
                        break;
                    }
                    platform.playMovie(content);

                }
                case SEARCH_TYPE -> {
                    int contentType = ScannerUtils.captureInt("""
                            1. Movie
                            2. Documentary
                            """);
                    if(contentType == 1){
                        List<Movie> movies = platform.getMovies();
                        movies.forEach(movie -> System.out.println(movie.getInfo()));
                    }else{
                        List<Documentary> documentaries = platform.getDocumentaries();
                        documentaries.forEach(documentary -> System.out.println(documentary.getInfo()));
                    }
                }
                case REMOVE ->{
                    String movieNameTitle = ScannerUtils.captureText("Search content to delete:");
                    Content content = platform.searchByTitle(movieNameTitle);
                    if(content == null) {
                        System.out.println("The content " + movieNameTitle + " has not coincidences.");
                        break;
                    }
                    platform.delete(content);
                    System.out.println("The content " + movieNameTitle + " has deleted.");
                }
                case EXIT -> System.exit(0);
            }
        }

    }

    private static void loadMovies(Platform platform) {
        platform.getCatalog().addAll(FileUtils.loadMovies());
    }
}
