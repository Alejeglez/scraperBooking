import static spark.Spark.get;

public class ApiBooking implements WebService{

    private String json;

    public ApiBooking(String json) {
        this.json = json;
    }

    @Override
    public void launch() {

    }
}
