import java.io.IOException;

public class Controller {

    private ApiBooking apiBooking;
    private Booking booking;

    public Controller(){
        apiBooking = new ApiBooking(this);
    }

    public void run() throws IOException, InterruptedException {

        ApiBooking apiBooking = new ApiBooking(this);
        apiBooking.launch();

    }

    public String getUbi(String name) throws IOException {
        booking = new Booking(name);
        return booking.getUbi();
    }

    public String getServices(String name) throws IOException {
        booking = new Booking(name);
        return booking.getServices();
    }

    public String getComments(String name) throws IOException {
        booking = new Booking(name);
        return booking.getComments();
    }

    public String getRatings(String name) throws IOException {
        booking = new Booking(name);
        return booking.getRatings();
    }

    public String getHotels(String name) throws IOException {
        booking = new Booking(name);
        return booking.getHotels();
    }
}
