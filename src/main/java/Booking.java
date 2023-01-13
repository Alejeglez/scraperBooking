import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class Booking implements Scraper {

    private String url;
    private String urlReviews;
    private String urlCiudades;

    private Gson gson = new Gson();

    public Booking(String name) {
        this.url = "https://www.booking.com/hotel/es/" + name + ".es.html";
        this.urlReviews = "https://www.booking.com/reviewlist.es.html?cc1=es;dist=1;pagename=" + name + ";type=total&&offset=0;rows=10";
        this.urlCiudades = "https://www.booking.com/city/es/" + name + ".es.html";
    }

    public String getUbi() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements direcciones = doc.getElementsByClass("\nhp_address_subtitle\njs-hp_address_subtitle\njq_tooltip\n");
        Element coordenadas = doc.getElementById("hotel_sidebar_static_map");
        Map<String, Object> mapResponse = new HashMap<>();
        mapResponse.put("Dirección", direcciones.text());
        mapResponse.put("Coordenadas (lat, lng)", coordenadas.attr("data-atlas-latlng"));

        return gson.toJson(mapResponse);
    }


    public String getServices() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements listServicios = doc.select("div.hotel-facilities-group");
        List<String> serviciosExtendidos = new ArrayList<>();
        Map<String, List<String>> mapServicios = new HashMap<>();

        for (Element servicio : listServicios) {
            String title = servicio.select("div.bui-title__text.hotel-facilities-group__title-text").text();
            try {
                serviciosExtendidos = new ArrayList<>(List.of(servicio.select("ul.bui-list.bui-list--text.bui-list--icon.hotel-facilities-group__list").text().split(" (?=\\p{Upper})", 0)));

            } catch (Exception e) {

            }
            try {
                String descripciónAdicional = servicio.select("div.bui-spacer--medium.hotel-facilities-group__policy").text();
                if (!descripciónAdicional.equals("")) {
                    serviciosExtendidos.add(servicio.select("div.bui-spacer--medium.hotel-facilities-group__policy").text());
                }

            } catch (Exception e) {

            }

            mapServicios.put(title, serviciosExtendidos);
        }

        return gson.toJson(mapServicios);
    }


    public String getRatings() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements ratings = doc.select("span.d6d4671780");
        Elements ratingsPuntuacion = doc.select("div.ee746850b6.b8eef6afe1");
        Element puntuaciónGeneral = doc.select("div.b5cd09854e.d10a6220b4").first();

        Map<String, Double> mapRatingsCategorías = new HashMap<>();

        mapRatingsCategorías.put("Valoración general", Double.parseDouble(puntuaciónGeneral.text().replace(",", ".")));

        for (int i = 0; i < (ratings.size() / 2); i++) {
            mapRatingsCategorías.put(ratings.get(i).text(), Double.parseDouble(ratingsPuntuacion.get(i).text().replace(",", ".")));
        }
        return gson.toJson(mapRatingsCategorías);
    }

    public String getHotels() throws IOException {
        Document docBúsquedaCiudad = Jsoup.connect(urlCiudades).get();
        Elements hoteles = docBúsquedaCiudad.select("h3.bui-spacer--smaller");
        List<String> hotelesText = new ArrayList<>();

        for (Element hotel : hoteles){
            String nameHotel = hotel.select("span.bui-card__title").get(0).text();
            hotelesText.add(nameHotel);
        }


        return gson.toJson(hotelesText);
    }

    public String getComments() throws IOException {
        Document docReviews = Jsoup.connect(urlReviews).get();
        Elements usuarios = docReviews.select("span.bui-avatar-block__title");
        Elements países = docReviews.select("span.bui-avatar-block__subtitle");
        Elements habitaciones = docReviews.select("a.c-review-block__room-link");
        Elements bloqueReviews = docReviews.select("div.c-review-block");
        Elements fechas = docReviews.select("ul.bui-list.bui-list--text.bui-list--icon.bui_font_caption.c-review-block__row.c-review-block__stay-date");
        Elements notas = docReviews.select("div.bui-review-score__badge");
        Elements titulosReview = docReviews.select("h3.c-review-block__title.c-review__title--ltr  ");
        Elements fechaComentarios = docReviews.select("div.c-review-block__row").select("span.c-review-block__date");
        Elements bloqueCompañías = docReviews.select("ul.bui-list.bui-list--text.bui-list--icon.bui_font_caption.review-panel-wide__traveller_type.c-review-block__row");

        String reviewPosText = "";
        String reviewNegText = "";

        List<Comentario> comentarios = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            Elements reviews = bloqueReviews.get(i).select("p.c-review__inner.c-review__inner--ltr").select("span.c-review__body");

            if (reviews.size() == 2) {
                reviewPosText = reviews.get(0).text();
                reviewNegText = reviews.get(1).text();
            } else if (reviews.size() == 1) {

                Elements positiveCheck = bloqueReviews.get(i).select("span.c-review__prefix.c-review__prefix--color-green");

                if (positiveCheck.size() == 1) {
                    reviewPosText = reviews.get(0).text();
                } else {
                    reviewNegText = reviews.get(0).text();
                }
            }


            String noche = List.of(fechas.get(i).text().split("·")).get(0).replaceAll("[^0-9]", "");
            String fechaEstancia = List.of(fechas.get(i).text().split("·")).get(1).substring(1);
            String compañía = bloqueCompañías.get(i).select("div.bui-list__body").text();
            Comentario comentario = new Comentario(usuarios.get(i).text(), países.get(i).text(), habitaciones.get(i).text(), fechaEstancia, Integer.parseInt(noche), compañía, Double.parseDouble(notas.get(i).text().replace(",", ".")), titulosReview.get(i).text(), fechaComentarios.get(i).text().substring(12), reviewPosText, reviewNegText);
            comentarios.add(comentario);
        }
        return gson.toJson(comentarios);
    }

}




