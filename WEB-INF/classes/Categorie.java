package produit;
public class Categorie {
    String idCategorie;
    String nomCategorie;

    public Categorie(String idCategorie,String nomCategorie) {
        this.setIdCategorie(idCategorie);
        this.setNomCategorie(nomCategorie);
    }

    public String getIdCategorie() {
        return this.idCategorie;
    }

    public void setIdCategorie(String id) {
        this.idCategorie = id;
    }

    public String getNomCategorie() {
        return this.nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    
}
