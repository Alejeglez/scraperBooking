import java.io.IOException;

public class Controller {

    public Controller(){}

    public void run() throws IOException {
        Scrapper bookingScrapper = new Booking("santa-catalina");
        System.out.println(bookingScrapper.getData());
    }
}
