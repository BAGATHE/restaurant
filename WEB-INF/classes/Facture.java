package gestion;

public class Facture{
    String idFacture; 
    String idClient;
    String idIntermediaire; 
    String dateFacturation;
    double montantTotal;




    public Facture(String idClient, String idIntermediaire, String dateFacturation,double montantTotal)throws Exception{
     this.setMontantTotal(montantTotal);
     this.setIdClient(idClient);
     this.setIdIntermediaire(idIntermediaire);
     this.setDateFacturation(dateFacturation);
    }

    public String getIdFacture() {
        return this.idFacture;
    }

    public void setIdFacture(String idFacturation) {
        this.idFacture = idFacturation;
    }

    public double getMontantTotal() {
        return this.montantTotal;
    }

    public void setMontantTotal(double montantTotal)throws Exception{
        if (montantTotal < 0) {
            throw new Exception("le montant total de la commande ne doit pas etre negative il y a erreur de calcul");
        }
        this.montantTotal = montantTotal;
    }

    public String getDateFacture() {
        return this.dateFacturation;
    }

    public void setDateFacturation(String dateFacturation) {
        this.dateFacturation = dateFacturation;
    }




   public String getIdClient() {
        return this.idClient;
    }

    public void setIdClient(String idClient) throws Exception{
        if (idClient == null || idClient.trim().isEmpty()) {
            throw new Exception("l'identifient client ne doit pas etre null");
        }
        this.idClient = idClient;
    }

    public String getIdIntermediaire() {
        
        return this.idIntermediaire;
    }

    public void setIdIntermediaire(String idIntermediaire) {
        this.idIntermediaire = idIntermediaire;
    }




}



