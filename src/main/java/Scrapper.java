import java.io.IOException;

public interface Scrapper {

    String getUbi() throws IOException;
    String getHotels() throws IOException;
    String getRatings() throws IOException;
    String getServices() throws IOException;
    String getComments() throws IOException;


}
