package gestion;

public class Commande {
  String idCommande;
  String idMenu;
  String idFacture;
  double prixUnitaire;
  double prixIntermediaire;
  double prixReviens;
  int quantiter;
  String dateCommande;
  String idClient;
  String idIntermediaire;


    public Commande(String idMenu,String idFacture,double prixUnitaire, double prixIntermediaire, double prixReviens, int quantiter, String dateCommande,String idClient,String idIntermediaire)throws Exception{
     this.setIdMenu(idMenu);
     this.setIdFacture(idFacture);
     this.setPrixUnitaire(prixUnitaire);
     this.setPrixIntermediaire(prixIntermediaire);
     this.setPrixReviens(prixReviens);
     this.setQuantiter(quantiter);
     this.setDateCommande(dateCommande);
     this.setIdClient(idClient);
     this.setIdIntermediaire(idIntermediaire);
    }




    public String getIdFacture() {
        return this.idFacture;
    }

    public void setIdFacture(String idFacture) {
        this.idFacture = idFacture;
    }

    public String getDateCommande() {
        return this.dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getIdCommande() {
        return this.idCommande;
    }

    public void setIdCommande(String idCommande) {
        this.idCommande = idCommande;
    }

    public String getIdMenu() {
        return this.idMenu;
    }

    public void setIdMenu(String idMenu)throws Exception{
        if (idMenu == null || idMenu.trim().isEmpty()) {
            throw new Exception("lidentifiantd de se menu ne peut pas etre null");
        }
        this.idMenu = idMenu;
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

    public int getQuantiter() {
        return this.quantiter;
    }

    public void setQuantiter(int quantiter)throws Exception{
        if (quantiter <= 0) {
            throw new Exception("le quantiter du produit selectionner doit etre au moins 1");
        }
        this.quantiter = quantiter;
    }
    

    public String getIdClient() {
        return this.idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdIntermediaire() {
        return this.idIntermediaire;
    }

    public void setIdIntermediaire(String idIntermediaire) {
        this.idIntermediaire = idIntermediaire;
    }
    



}
