public class Comentario {

    private String usuario;
    private String país;
    private String habitación;
    private String fechaEstancia;

    private String noche;
    private String compañía;
    private double rating;
    private String títuloReview;
    private String fechaReview;
    private String positivo;
    private String negativo;

    public Comentario(String usuario, String país, String habitación, String fechaEstancia, String noche, String compañía, double rating, String títuloReview, String fechaReview, String positivo, String negativo) {
        this.usuario = usuario;
        this.país = país;
        this.habitación = habitación;
        this.fechaEstancia = fechaEstancia;
        this.noche = noche;
        this.compañía = compañía;
        this.rating = rating;
        this.títuloReview = títuloReview;
        this.fechaReview = fechaReview;
        this.positivo = positivo;
        this.negativo = negativo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPaís() {
        return país;
    }

    public String getHabitación() {
        return habitación;
    }

    public String getFechaEstancia() {
        return fechaEstancia;
    }

    public String getCompañía() {
        return compañía;
    }

    public double getRating() {
        return rating;
    }

    public String getTítuloReview() {
        return títuloReview;
    }

    public String getFechaReview() {
        return fechaReview;
    }

    public String getPositivo() {
        return positivo;
    }

    public String getNegativo() {
        return negativo;
    }
}
