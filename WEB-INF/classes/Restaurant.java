package gestion;
public class Restaurant {
    String idRestaurant;
    String nomRestaurant;



    public Restaurant(String idRestaurant, String nomRestaurant) {
       this.setIdRestaurant(idRestaurant);
       this.setNomRestaurant(nomRestaurant);
    }



    public String getIdRestaurant() {
        return this.idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getNomRestaurant() {
        return this.nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

}
