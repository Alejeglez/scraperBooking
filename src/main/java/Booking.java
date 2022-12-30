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
        String direcciónText = "";
        Document doc = Jsoup.connect(url).get();
        Element descripciónUbi = doc.getElementById("hotel_header");
        Elements direcciones = doc.getElementsByClass("\nhp_address_subtitle\njs-hp_address_subtitle\njq_tooltip\n");
        Elements serviciosPopulares = doc.getElementsByClass("important_facility");
        Elements servicios = doc.getElementsByClass("bui-title__text hotel-facilities-group__title-text");

        Map<String, List<String>> serviciosMap = new HashMap<>();

        for(Element direccion : direcciones){
            direcciónText = direccion.text();
        }

        List<String> serviciosPopularesText = new ArrayList<>();

        for(Element servicioPopular : serviciosPopulares){
            if(!serviciosPopularesText.contains(servicioPopular.text())){
                serviciosPopularesText.add(servicioPopular.text());
                //System.out.println(servicioPopular.text());
            }
        }
        System.out.println(servicios.size());

        return null;
    }

}
