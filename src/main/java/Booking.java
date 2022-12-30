import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Booking implements Scrapper{

    private String name;

    public Booking(String name) {
        this.name = name;
    }

    @Override
    public String getData() throws IOException {
        String url = "https://www.booking.com/hotel/es/" + name  + ".es.html?aid=397594&label=gog235jc-1FCAEoggI46AdIM1gDaEaIAQGYAQq4ARfIAQzYAQHoAQH4AQyIAgGoAgO4Asixtp0GwAIB0gIkY2YzNTM3MWQtNzMwYy00ZGQzLTg4MTUtOTEwYmQ1OTVmNWQz2AIG4AIB&sid=bb5d8c13c097423d59be2bc511229144&dest_id=-388528;dest_type=city;dist=0;group_adults=2;group_children=0;hapos=1;hpos=1;nflt=class%3D5;no_rooms=1;req_adults=2;req_children=0;room1=A%2CA;sb_price_type=total;sr_order=popularity;srepoch=1672321092;srpvid=5f626080a1990037;type=total;ucfs=1&#hotelTmpl";
        Document doc = Jsoup.connect(url).get();

        //CalidadUbicación
        Element descripciónUbi = doc.getElementById("hotel_header");

        //Dirección
        Elements direcciones = doc.getElementsByClass("\nhp_address_subtitle\njs-hp_address_subtitle\njq_tooltip\n");
        for(Element direccion : direcciones){
            String direcciónText = direccion.text();
        }


        //Ratings categorías
        Elements ratings = doc.select("span.d6d4671780");
        Elements ratingsPuntuacion = doc.getElementsByClass("ee746850b6 b8eef6afe1");

        Map<String, String> mapRatingsCategorías = new HashMap<>();

        for(int i = 0; i < (ratings.size()/2) ; i++){
            mapRatingsCategorías.put(ratings.get(i).text(), ratingsPuntuacion.get(i).text());
        }

        Gson gson = new Gson();
        String jsonCategorías = gson.toJson(mapRatingsCategorías);

        //Servicios

        Elements listServicios = doc.select("div.hotel-facilities-group");
        String servicioList = "";
        String servicioDescripción = "";
        List<String> serviciosExtendidos = new ArrayList<>();
        Map<String, List<String>> mapServicios = new HashMap<>();

        for(Element servicio : listServicios){
            String title = servicio.select("div.bui-title__text.hotel-facilities-group__title-text").text();
            try {
                serviciosExtendidos = new ArrayList<>(List.of(servicio.select("ul.bui-list.bui-list--text.bui-list--icon.hotel-facilities-group__list").text().split(" (?=\\p{Upper})[De]*", 0)));

            } catch (Exception e) {

            }
            try{
                String descripciónAdicional = servicio.select("div.bui-spacer--medium.hotel-facilities-group__policy").text();
                if(!descripciónAdicional.equals("")){
                    serviciosExtendidos.add(servicio.select("div.bui-spacer--medium.hotel-facilities-group__policy").text());
                }

            }
            catch (Exception e){

            }

            mapServicios.put(title, serviciosExtendidos);
        }

        String jsonServicios = gson.toJson(mapServicios);


        return jsonServicios;
    }

    //Dirección -
    //Localización -
    //Ratings -

    //Servicios
    //Comentarios clientes

}
