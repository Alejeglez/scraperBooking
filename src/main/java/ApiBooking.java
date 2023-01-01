import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.get;

public class ApiBooking implements WebService{

    private Controller controller;

    public ApiBooking(Controller controller) {
        this.controller = controller;
    }

    private String name;
    private String json;

    @Override
    public void launch() {
        get("/hotels/:name", ((request, response) -> getHotelUbi(request, response)));
        get("/hotels/:name/services", ((request, response) -> getHotelServices(request, response)));
        get("/hotels/:name/comments", ((request, response) -> getHotelComments(request, response)));
        get("/hotels/:name/ratings", ((request, response) -> getHotelRatings(request, response)));
    }

    private String getHotelRatings(Request request, Response response) {
        return null;
    }

    private String getHotelComments(Request request, Response response) throws IOException {

        response.header("content-type", "application/json");
        name = request.params("name");
        json = controller.getComments(name);
        return json;
    }

    private String getHotelServices(Request request, Response response) throws IOException {
        response.header("content-type", "application/json");
        name = request.params("name");
        json = controller.getServices(name);
        return json;
    }

    private String getHotelUbi(Request request, Response response) throws InterruptedException, IOException {
        response.header("content-type", "application/json");
        name = request.params("name");
        json = controller.getUbi(name);
        return json;

    }





}
