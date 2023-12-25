package produit;
public class Menu {
    String idMenu;
    String nom;
    double prixUnitaire;
    double prixIntermediaire;
    double prixReviens;
    String idCategorie;

    public Menu(String Menu ,String nom, double prixUnitaire, double prixIntermediaire, double prixReviens, String idCategorie) throws Exception{
          this.setIdMenu(Menu);
          this.setNom(nom);
          this.setPrixUnitaire(prixUnitaire);
          this.setPrixIntermediaire(prixIntermediaire);
          this.setPrixReviens(prixReviens);
          this.setIdCategorie(idCategorie);
    }


    public String getIdMenu() {
        return this.idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) throws Exception {
        if (nom == null || nom.trim().isEmpty()) {
            throw new Exception("Le nom ne peut pas etre vide");
        }
        this.nom = nom;
    }

    public double getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire)throws Exception {
        if (prixUnitaire <=0 || prixUnitaire < this.getPrixIntermediaire() || prixUnitaire < this.getPrixReviens()  ) {
            throw new Exception("le prix unitaire doit etre positive et superieur au prix intermediaire et reviens");
        }
        this.prixUnitaire = prixUnitaire;
    }

    public double getPrixIntermediaire() {
        return this.prixIntermediaire;
    }

    public void setPrixIntermediaire(double prixIntermediaire)throws Exception {
        if (prixIntermediaire < 0 || prixIntermediaire > this.getPrixUnitaire() || prixIntermediaire < this.getPrixReviens() ) {
            throw new Exception("le prix intermediaire doit est positive superieur au prix de reviens et inferieur prix unitaire");
        }
        this.prixIntermediaire = prixIntermediaire;
    }

    public double getPrixReviens() {
        return this.prixReviens;
    }

    public void setPrixReviens(double prixReviens)throws Exception{
        if (prixReviens > this.getPrixUnitaire() || prixReviens > this.getPrixIntermediaire()) {
            throw new Exception("le prix de reviens doit etre inferieur a prix unitaire et intermediaire");
        }
        this.prixReviens = prixReviens;
    }

    public String getIdCategorie() {
        return this.idCategorie;
    }

    public void setIdCategorie(String idCategorie) throws Exception {
        if (idCategorie == null || idCategorie.trim().isEmpty()) {
            throw new Exception("L'identifiant de categorie ne peut pas etre vide");
        }
        this.idCategorie = idCategorie;
    }


    
}
