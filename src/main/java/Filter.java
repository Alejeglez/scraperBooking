import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    private List<Element> review;

    public Filter(List<Element> review) {
        this.review = review;
    }

    public List<String> filterReview(){
        List<String> resultado = new ArrayList<>();
        String positiva = "";
        String negativa = "";

        if (review.size() == 2) {
            positiva = review.get(0).text();
            negativa = review.get(1).text();

        }

        else {
            if (review.get(0).text().indexOf("Gustó") == 0) {
                positiva = review.get(0).text();
                negativa = "";
            } else {
                positiva = "";
                negativa = review.get(0).text();
            }
        }

        resultado.add(positiva);
        resultado.add(negativa);

        return resultado;
    }

}
