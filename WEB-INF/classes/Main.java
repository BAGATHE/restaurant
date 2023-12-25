package affichage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.sql.Connection;
import java.sql.SQLException;
import utilitaire.*;
import gestion.*;
import produit.*;
import vivant.*;
import java.util.*;



public class Main {
    public static void main(String[] args) {
        try {
            
            RequestDataBase request = new RequestDataBase();
            List<Menu> menus = new ArrayList<>();
            List<Categorie> categories = new ArrayList<>();
                        //menus = request.getMenuByIdCategorie("categorie-1");
            //categories = request.getAllCategorie();
             
            //request.insertClient("postgres");
            //System.out.println(request.getLastIdINtable("postgres","idfacture","Facture"));
            //Facture facture = new Facture("Client-3", "", "2023-12-13", 0);
           // request.insert(facture,"postgres");
            /* 
            Connect connecteur = new Connect();
            Connection con = connecteur.dbConnect("postgres");
            if (con!=null) {
                System.out.println("sucess");
            }else{
                System.err.println("lo");
            }*/

        /* 
        request.insertClient("postgres");
        //on recupere l'id recement inserer;
        String idClient = request.getLastIdINtable("postgres","idClient","Client");

        String date ="2023-03-03";
        Facture facture = new Facture(idClient,"",date,0);
        request.insert(facture,"postgres");
        //je recupere l'idfacture recement inserer
        String idfacture = request.getLastIdINtable("postgres","idfacture","Facture");
        Commande commande = new Commande("menu-1",idfacture,2000, 1500, 1000, 5, date,idClient,"");
      
        List <Commande> commandes = new ArrayList<>();
        commandes = request.getCommandeInAllMonth("1");

        */
       /*  JSONArray intermediaireArray = new JSONArray();
        List<String> iddemarcheur = request.getIntermediairework("12"); // Obtient la liste des intermédiaires pour le mois
    
        if (iddemarcheur != null) {
          
            for (String idinter : iddemarcheur) {
                  JSONArray commandesArray = new JSONArray();
                List<Commande> commandeRatacher = request.getCommandeInAllMonthIntermediaire("12",idinter);
                for (Commande com : commandeRatacher) {
                    JSONObject commandeObj = new JSONObject();
                    commandeObj.put("Menu",request.getMenuById(com.getIdMenu()).getNom());
                    commandeObj.put("idFacture",com.getIdFacture());
                    commandeObj.put("prixUnitaire",com.getPrixUnitaire());
                    commandeObj.put("prixIntermediaire",com.getPrixIntermediaire());
                    commandeObj.put("prixReviens",com.getPrixReviens());
                    commandeObj.put("quantiter",com.getQuantiter());
                    commandeObj.put("dateCommande",com.getDateCommande());
                    commandeObj.put("idClient",com.getIdClient());
                    commandeObj.put("idIntermediaire",com.getIdIntermediaire());
                    commandesArray.add(commandeObj);    
                }
                intermediaireArray.add(commandesArray);
            }

            System.out.println(intermediaireArray.toJSONString());
              
       
    }*/
    //System.out.println(request.getCommandeInAllMonth("1", "2023").size());
   //System.out.println(request.getIntermediairework("12","2023").size()); 
    System.out.println(request.getCumulleCommandeIntermediaire("13","2023","intermediaire-1"));

}catch (Exception err) {
            System.out.println(err.getMessage());
        }

}

}

