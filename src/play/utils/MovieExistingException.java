package play.utils;

public class MovieExistingException extends RuntimeException{
    public MovieExistingException(String title){
        super("Error: The movie "+ title +" existing.");
    }
}
