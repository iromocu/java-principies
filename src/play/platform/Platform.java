package play.platform;

import play.content.*;
import play.utils.FileUtils;
import play.utils.MovieExistingException;

import java.util.*;

public class Platform {
    private String name;
    private List<Content> catalog;
    private Map<Content, Integer> views;

    public Platform(String name) {
        this.name = name;
        this.catalog = new ArrayList<>();
        this.views = new HashMap<>();
    }

    public void add(Content content){
        Content searchContent = this.searchByTitle(content.getTitle());
        if(searchContent != null) throw new MovieExistingException(content.getTitle());
        FileUtils.writeMovie(content);
        this.catalog.add(content);
    }

    public void playMovie(Content content){
        int count = views.getOrDefault(content, 0);
        System.out.println("The catalog has " + count + " views");
        this.countViews(content);
        content.play();
    }

    private void countViews(Content content){
        views.put(content, views.getOrDefault(content, 0) + 1);
    }

    public List<String> showMovies(){
        return catalog.stream()
                .map(Content::getTitle).toList();
    }

    public List<ResumeContent> getResumenes(){
        return catalog.stream()
                .map(movie -> new ResumeContent(movie.getTitle(), movie.getDuration(), movie.getGender()))
                .toList();
    }

    public void delete(Content content){
        this.catalog.remove(content);
    }

    public Content searchByTitle(String title){
       return catalog.stream().filter(
               movie -> movie.getTitle().equalsIgnoreCase(title)
                ).findFirst()
               .orElse(null);
    }

    public List<Content> searchByGender(Gender gender){
        return catalog.stream().filter(
                movie -> movie.getGender().equals(gender)
        ).toList();
    }

    public int getTotalDuration(){
        return catalog.stream()
                .mapToInt(Content::getDuration)
                .sum();
    }

    public List<Content> getPopularMovies(int limit){
        return catalog.stream()
                .sorted(Comparator.comparingDouble(Content::getRankin).reversed())
                .limit(limit)
                .toList();
    }

    public List<Movie> getMovies(){
        return catalog.stream()
                .filter( content -> content instanceof Movie)
                .map( contentFilter -> (Movie) contentFilter)
                .toList();
    }

    public List<Documentary> getDocumentaries(){
        return catalog.stream()
                .filter( content -> content instanceof Documentary)
                .map( contentFilter -> (Documentary) contentFilter)
                .toList();
    }
    public List<Promotional> getContentPromotional(){
        return catalog.stream()
                .filter( content -> content instanceof Promotional)
                .map( contentPromotional -> (Promotional) contentPromotional )
                .toList();
    }

    public String getName() {
        return name;
    }

    public List<Content> getCatalog() {
        return catalog;
    }
}
