package play.utils;

import play.content.Gender;
import play.content.Movie;
import play.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    private static final String FILE_NAME = "content.txt";
    private static final String PIPE = "|";

    public static List<Movie> loadMovies() {
        try {
            List<Movie> moviesLoad = new ArrayList<>();
            List<String> fileLines = Files.readAllLines(Paths.get(FILE_NAME));
            fileLines.forEach(line -> {
                String [] data = line.split("\\" + PIPE);
                if(data.length == 5){
                    String title = data[0];
                    int duration = Integer.parseInt(data[1]);
                    Gender genero = Gender.valueOf(data[2]);
                    double rankin = data[3].isBlank() ? 0 : Double.parseDouble(data[3]);
                    LocalDate year = LocalDate.parse(data[4]);
                    Movie newMovie = new Movie(title, duration, genero, rankin);
                    newMovie.setYear(year);

                    moviesLoad.add(newMovie);
                }
            });
            return moviesLoad;
        } catch (IOException e) {
            System.out.println("Error to load file!: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void writeMovie(Movie movie){
        String line = String.join(PIPE,
                movie.getTitle(),
                String.valueOf(movie.getDuration()),
                movie.getGender().name(),
                String.valueOf(movie.getRankin()),
                movie.getYear().toString());
        try {
            Files.writeString(
                    Paths.get(FILE_NAME),
                    line  + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error to write file!: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
