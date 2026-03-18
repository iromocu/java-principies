package play.content;

public class Movie extends Content{

    public Movie(String title, int duration, Gender gender) {
        super(title, duration, gender);
    }

    public Movie(String title, int duration, Gender gender, double rankin) {
        super(title, duration, gender, rankin);
    }

    @Override
    public void play() {
        System.out.println("Playing movie: " + getTitle());
    }
}
