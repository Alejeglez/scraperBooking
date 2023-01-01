import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
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
        String url = "https://www.booking.com/hotel/es/" + name + ".es.html";
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

       //Comentarios clientes;
        String urlReviews = "https://www.booking.com/reviewlist.es.html?cc1=es;dist=1;pagename=" + name + ";type=total&&offset=0;rows=10";
        Document docReviews = Jsoup.connect(urlReviews).get();
        Elements usuarios = docReviews.select("span.bui-avatar-block__title");
        Elements países = docReviews.select("span.bui-avatar-block__subtitle");
        Elements habitaciones =  docReviews.select("a.c-review-block__room-link");
        Elements bloqueReviews = docReviews.select("div.c-review");
        Elements fechas = docReviews.select("ul.bui-list.bui-list--text.bui-list--icon.bui_font_caption.c-review-block__row.c-review-block__stay-date");
        Elements notas = docReviews.select("div.bui-review-score__badge");
        Elements titulosReview = docReviews.select("h3.c-review-block__title.c-review__title--ltr  ");
        Elements fechaComentarios = docReviews.select("span.c-review-block__date");
        Elements compañías = docReviews.select("div.bui-list__body");

        String reviewPosText = "";
        String reviewNegText = "";
        List<List<String>> reviewsContent = new ArrayList<>();
        List<Comentario> comentarios = new ArrayList<>();
        for(int i = 0; i < usuarios.size(); i++) {

            try {
                Element reviewPos = bloqueReviews.get(i).select("span.c-review__prefix.c-review__prefix--color-green").first().select("span.c-review__body").first();
                reviewPosText = reviewPos.text();
            }
            catch (Exception e){

            }

            try {
                Element reviewNeg = bloqueReviews.get(i).select("span.c-review__prefix").first().select("span.c-review__body").first();
                reviewNegText = reviewNeg.text();
            }
            catch (Exception e){

            }



            String noche = List.of(fechas.get(i).text().split("·")).get(0);
            String fechaEstancia = List.of(fechas.get(i).text().split("·")).get(1);

            Comentario comentario = new Comentario(usuarios.get(i).text(), países.get(i).text(), habitaciones.get(i).text(), fechaEstancia, noche,compañías.get(i).text(), Double.parseDouble(notas.get(i).text().replace(",",".")), titulosReview.get(i).text(), fechaComentarios.get(i).text(), reviewPosText, reviewNegText);
            comentarios.add(comentario);
        }

        String jsonComentario = gson.toJson(comentarios);
        return jsonComentario;
    }

    //Dirección -
    //Localización -
    //Ratings -

    //Servicios
    //Comentarios clientes

}
