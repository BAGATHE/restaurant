package utilitaire;
import gestion.Facture;
import java.util.List;

public class Function {

public double getSommeCumul(List<Facture> fact)throws Exception{
  double somme = 0;

  for (Facture facture : fact) {
    somme+= facture.getMontantTotal();
  }

  return somme;

}




}