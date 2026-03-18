package play.platform;

import play.content.Movie;

import java.time.LocalDateTime;

public class User {
    private String name;
    private String email;
    private LocalDateTime registerDate;

    public User(){}

    public User(String name){
        this.name = name;
        this.registerDate = LocalDateTime.now();
    }
    public void watch(Movie movie){
        System.out.println(name + " is watching .... " + movie.getTitle());
        movie.play();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
