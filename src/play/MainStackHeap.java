package play;

import play.content.Gender;
import play.content.Movie;

public class MainStackHeap {
    public static void main(String[] args) {
        Movie reyleon = new Movie("rey leon", 120, Gender.ANIME);
        Movie harryPotter = new Movie("Harry Potter", 150, Gender.SCI_FY);

        System.out.println(reyleon.getTitle());
        System.out.println(harryPotter.getTitle());
    }
}
