package play.content;

public class Documentary extends Content implements Promotional{

    private String announcer;

    public Documentary(String title, int duration, Gender gender) {
        super(title, duration, gender);
    }


    public Documentary(String title, int duration, Gender gender, double rankin, String announcer) {
        super(title, duration, gender, rankin);
        this.announcer = announcer;
    }

    @Override
    public String getInfo() {
        return  "🎥" + getTitle()  + "(" + getYear() + ") \n" +
                "Gender: " + getGender() + "\n"+
                "Ranking: " + getRankin() + "/5.0" + "\n" +
                "Announcer" + announcer;
    }

    @Override
    public void play() {
        System.out.println("Playing documentary " + getTitle() + ", announced by "+ getAnnouncer());
    }

    @Override
    public String promotion() {
        return "💫 Discover the documentary "+ getTitle() + " announced by: " + getAnnouncer();
    }

    public String getAnnouncer() {
        return announcer;
    }
}
