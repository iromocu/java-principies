package play.content;

import java.time.LocalDate;
import java.util.List;

public abstract class Content {
    private String title;
    private String description;
    private int duration;
    private Gender gender;
    private double rankin;
    private boolean available;
    private LocalDate year;
    private List<Lenguage> lenguageList;
    private List<Quality> qualities;


    public Content(String title, int duration, Gender gender){
        this.title = title;
        this.duration = duration;
        this.gender = gender;
        this.year = LocalDate.now();
        this.available = true;
    }

    public Content(String title, int duration, Gender gender, double rankin){
        this(title, duration, gender);
        this.rank(rankin);
    }

    public abstract void play();

    public String getInfo(){
        return "🍿" + title  + "(" + year.getYear() + ") \n" +
                "Gender: " + gender + "\n"+
                "Ranking: " + rankin + "/5.0";
    }

    public void rank(double ranking){
        if(ranking < 0 || ranking > 5 ) return;
        this.rankin = ranking;
    }

    public boolean isPopular(){
        return rankin >= 4;
    }

    public String getTitle() {
        return title;
    }

    public Gender getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getRankin() {
        return rankin;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }
}
