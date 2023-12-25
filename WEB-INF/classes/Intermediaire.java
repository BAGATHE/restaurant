package vivant;

public class Intermediaire {
     String idIntermediaire;
     String cin;
     String mdp;
     double quotaMax;
     double quotaMin;
    



    public Intermediaire(String idIntermediaire,String cin,String password ,double quotaMax, double quotaMin)throws Exception{
        this.setIdIntermediaire(idIntermediaire);
        this.setCin(cin);
        this.setMdp(password);
        this.setQuotaMax(quotaMax);
        this.setQuotaMin(quotaMin);
       
    }
    

    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String password) {
        this.mdp = password;
    }




    public String getIdIntermediaire() {
        return this.idIntermediaire;
    }

    public void setIdIntermediaire(String idIntermediaire) {
        this.idIntermediaire = idIntermediaire;
    }

    public String getCin() {
        return this.cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }


    public double getQuotaMax() {
        return this.quotaMax;
    }

    public void setQuotaMax(double quotaMax) throws Exception {
        if (quotaMax <= 0 || this.getQuotaMin() > quotaMax) {
            throw new Exception("Le quota maximum doit être positif et supérieur ou égal au quota minimum");
        }
        this.quotaMax = quotaMax;
    }
    

    public double getQuotaMin() {
        return this.quotaMin;
    }

    public void setQuotaMin(double quotaMin)throws Exception {
        if (quotaMin < 0) {
            throw new Exception("Le quota minimum doit etre positif");
        }
        this.quotaMin = quotaMin;
    }

}