import java.io.IOException;

public interface Scraper {

    String getUbi() throws IOException;
    String getHotels() throws IOException;
    String getRatings() throws IOException;
    String getServices() throws IOException;
    String getComments() throws IOException;


}
