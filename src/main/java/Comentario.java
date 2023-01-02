public class Comentario {

    private String usuario;
    private String país;
    private String habitación;
    private String fechaEstancia;

    private int noches;
    private String compañía;
    private double rating;
    private String títuloReview;
    private String fechaReview;
    private String positivo;
    private String negativo;

    public Comentario(String usuario, String país, String habitación, String fechaEstancia, int noches, String compañía, double rating, String títuloReview, String fechaReview, String positivo, String negativo) {
        this.usuario = usuario;
        this.país = país;
        this.habitación = habitación;
        this.fechaEstancia = fechaEstancia;
        this.noches = noches;
        this.compañía = compañía;
        this.rating = rating;
        this.títuloReview = títuloReview;
        this.fechaReview = fechaReview;
        this.positivo = positivo;
        this.negativo = negativo;
    }

}
