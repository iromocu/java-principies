package play.utils;

import play.content.Content;
import play.content.Documentary;
import play.content.Gender;
import play.content.Movie;

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
    public static final String MOVIE = "MOVIE";
    public static final String DOCUMENTARY = "DOCUMENTARY";

    public static List<Content> loadMovies() {
        try {
            List<Content> moviesLoad = new ArrayList<>();
            List<String> fileLines = Files.readAllLines(Paths.get(FILE_NAME));
            fileLines.forEach(line -> {
                String [] data = line.split("\\" + PIPE);
                String typeContent = data[0];
                if((MOVIE.equals(typeContent) && data.length == 6) ||
                        (DOCUMENTARY.equals(typeContent) && data.length == 7)){
                    String title = data[1];
                    int duration = Integer.parseInt(data[2]);
                    Gender genero = Gender.valueOf(data[3]);
                    double rankin = data[4].isBlank() ? 0 : Double.parseDouble(data[4]);
                    LocalDate year = LocalDate.parse(data[5]);
                    Content newContent;
                    if(MOVIE.equals(typeContent)){
                        newContent = new Movie(title, duration, genero, rankin);
                    }else{
                        String announcer = data[6];
                        newContent = new Documentary(title, duration, genero, rankin, announcer);
                    }
                    newContent.setYear(year);

                    moviesLoad.add(newContent);
                }
            });
            return moviesLoad;
        } catch (IOException e) {
            System.out.println("Error to load file!: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void writeMovie(Content content){
        String line = String.join(PIPE,
                content.getTitle(),
                String.valueOf(content.getDuration()),
                content.getGender().name(),
                String.valueOf(content.getRankin()),
                content.getYear().toString());
        String finalLine;
        if(content instanceof Documentary documentary){
            finalLine = DOCUMENTARY + PIPE + line + PIPE + documentary.getAnnouncer();
        }else{
            finalLine = MOVIE + PIPE + line;
        }
        try {
            Files.writeString(
                    Paths.get(FILE_NAME),
                    finalLine  + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error to write file!: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
