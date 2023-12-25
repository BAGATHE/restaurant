package utilitaire;
import gestion.*;
import java.sql.*;
import java.lang.reflect.*;
import produit.*;
import vivant.*;
import java.util.*;


public class RequestDataBase {


    //fonction mamoka liste categorie

    public List<Categorie> getAllCategorie()throws Exception{
        String url = "SELECT * FROM Categorie"; 
        Connect connexion = new Connect();
         Connection connection = connexion.dbConnect("postgres");
         Statement stmt = connection.createStatement();
        ResultSet rep = stmt.executeQuery(url);
        List<Categorie> categories = new ArrayList<>();
        while (rep.next()) {
            Categorie categorie = new Categorie(rep.getString("idCategorie"), rep.getString("nomCategorie"));
                categories.add(categorie);
        }
        connection.close();
        stmt.close();  
        return categories;
    } 



public List<Menu> getMenuByIdCategorie(String idCategorie) throws SQLException,Exception {
    String url ="SELECT * FROM Menu WHERE idCategorie='"+idCategorie+"'";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(url);
    List<Menu> result = new ArrayList<>();
        while (rep.next()) {
            Menu menu = new Menu(rep.getString("idMenu"),rep.getString("nom"),rep.getDouble("prixUnitaire"),rep.getDouble("prixIntermediaire"),rep.getDouble("prixReviens"),rep.getString("idCategorie"));
            result.add(menu);
        }
     connection.close();
     stmt.close();
    return result;
}



public void insert(Object o,String database)throws Exception{
    Class c = o.getClass(); //alaiko class le object
    Field[] fields = c.getDeclaredFields();
    
    StringBuilder nomColonnes = new StringBuilder();
    StringBuilder valueColonnes = new StringBuilder();
     StringBuilder sqlRequete = new StringBuilder();
    
    for(int i=0;i<fields.length;i++){
        fields[i].setAccessible(true);
        Object value = fields[i].get(o);  //maka anle value get anle champ => miretourne object
        
        if(value != null){
            if(valueColonnes.length() > 0){
                nomColonnes.append(","); //separateur 
                valueColonnes.append(",");
            }
            nomColonnes.append(fields[i].getName());
            
            if(value instanceof String){
                 String strvalue = (String) value;
                if(strvalue.matches("\\d{4}-\\d{2}-\\d{2}")){
                    if (database.equals("oracle")) {
                          valueColonnes.append("to_date('").append(strvalue).append("','YYYY-MM-DD')");
                    }else{
                            valueColonnes.append("'"+strvalue.toString()+"'");
                    }
                }else{valueColonnes.append("'").append(strvalue).append("'");}
            }
            else{
                valueColonnes.append(value);
            }
        }			
    }

    sqlRequete.append("INSERT INTO ").append(c.getSimpleName()).append(" VALUES (").append("concat('").append(c.getSimpleName()).append("-',nextval('seq").append(c.getSimpleName()).append("')),").append(valueColonnes.toString()).append(")");
        Connect connexion=new Connect();
        Connection connection=connexion.dbConnect(database);
        connection.setAutoCommit(false);
        Statement stmt=connection.createStatement();

 int rep = stmt.executeUpdate(sqlRequete.toString());
 
 if(rep == 0){
     //System.out.println("insertion non valide dans" + c.getSimpleName();
     connection.rollback();
 }else{
     //System.out.println("insertion reussi dans " + c.getSimpleName());
  
     connection.commit();
    
 }
 connection.close();
 stmt.close();
}







public void insertClient(String database)throws Exception{
    StringBuilder sqlRequete = new StringBuilder();
    sqlRequete.append("INSERT INTO ").append("Client (idClient)").append(" VALUES (").append("concat('").append("Client").append("-',nextval('seq").append("Client").append("'))").append(")");
        Connect connexion=new Connect();
        Connection connection=connexion.dbConnect(database);
        connection.setAutoCommit(false);
        Statement stmt=connection.createStatement();

 int rep = stmt.executeUpdate(sqlRequete.toString());
 
 if(rep == 0){
     connection.rollback();
 }else{
     connection.commit();
 }
 
}


public String getLastIdINtable(String database,String colonneName,String table)throws Exception{
    String response =" ";
    String requette =" SELECT idClient FROM client ORDER BY idClient DESC LIMIT 1";
     StringBuilder sqlRequete = new StringBuilder();
     sqlRequete.append("SELECT *").append(" FROM ").append(table);
        Connect connexion=new Connect();
        Connection connection=connexion.dbConnect(database);
        connection.setAutoCommit(false);
        Statement stmt=connection.createStatement();

        ResultSet result = stmt.executeQuery(sqlRequete.toString());
        while (result.next()) {
            if (result.isLast()==true) {
                 response = result.getString(colonneName);
            }
          
        }
      
        connection.close();
        stmt.close();
        result.close();
        return response; 
}

public Menu getMenuById(String idMenu) throws Exception {
    String sql = "SELECT * FROM Menu WHERE idMenu='" + idMenu + "'";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(sql);
    Menu menu = null; // Initialise le menu en dehors de la boucle

    while (rep.next()) {
        menu = new Menu(
                rep.getString("idMenu"),
                rep.getString("nom"),
                rep.getDouble("prixUnitaire"),
                rep.getDouble("prixIntermediaire"),
                rep.getDouble("prixReviens"),
                rep.getString("idCategorie")
        );
    }

    stmt.close();
    rep.close();
    connection.close();
    return menu;
}


public List<Commande> getCommandeInAllMonth(String month,String year) throws SQLException,Exception {
    String url = "SELECT * FROM Commande WHERE EXTRACT(month FROM dateCommande)='" + month + "' AND EXTRACT(year FROM dateCommande)='" + year + "' ORDER BY dateCommande ASC";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(url);
    List<Commande> result = new ArrayList<>();
        while (rep.next()) {
        Commande commande= new Commande(rep.getString("idMenu"),rep.getString("idFacture"),rep.getDouble("prixUnitaire"),rep.getDouble("prixIntermediaire"),rep.getDouble("prixReviens"),rep.getInt("quantiter"),rep.getString("dateCommande"),rep.getString("idClient"),rep.getString("idIntermediaire"));
        result.add(commande);
        }
     connection.close();
     stmt.close();
    return result;
}





public List<Commande> getCommandeInAllMonthIntermediaire(String month,String year,String idIntermediaire) throws SQLException,Exception {
    String url ="SELECT  * FROM Commande WHERE EXTRACT(month from dateCommande)='"+month+"' AND EXTRACT(year FROM dateCommande)='" + year +"' AND  idIntermediaire='"+idIntermediaire +"'  ORDER BY dateCommande ASC";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(url);
    List<Commande> result = new ArrayList<>();
        while (rep.next()) {
        Commande commande= new Commande(rep.getString("idMenu"),rep.getString("idFacture"),rep.getDouble("prixUnitaire"),rep.getDouble("prixIntermediaire"),rep.getDouble("prixReviens"),rep.getInt("quantiter"),rep.getString("dateCommande"),rep.getString("idClient"),rep.getString("idIntermediaire"));
        result.add(commande);
        }
     connection.close();
     stmt.close();
     rep.close();
    return result;
}

public double getCumulleCommandeIntermediaire(String month,String year,String idInter) throws SQLException,Exception {
    double somme = 0;
    String url ="SELECT  * FROM Commande WHERE EXTRACT(month from dateCommande)<'"+month+"' AND EXTRACT(year FROM dateCommande)='" + year +"' AND  idIntermediaire='"+ idInter +"'  ORDER BY dateCommande ASC";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(url);
        
    
    while (rep.next()) {
          somme += rep.getDouble("prixUnitaire") * rep.getInt("quantiter");
        }
     connection.close();
     stmt.close();
     rep.close();
    return somme;
}




public List<Facture> getFactureCumul(String month,String year) throws SQLException,Exception {
    String url ="SELECT  * FROM Facture WHERE EXTRACT(month from datefacturation )<'"+month+"' AND EXTRACT(year FROM datefacturation )='" + year +"'";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(url);
    List<Facture> result = new ArrayList<>();
        while (rep.next()) {
        Facture facture= new Facture(rep.getString("idClient"),rep.getString("idIntermediaire"),rep.getString("dateFacturation"),rep.getDouble("montanttotal"));
        result.add(facture);
        }
     connection.close();
     stmt.close();
     rep.close();
    return result;
}






public List<String> getIntermediairework(String month,String year) throws SQLException,Exception {
    String url = "SELECT idIntermediaire FROM Commande WHERE   EXTRACT(year FROM dateCommande)='"+year+"' AND  EXTRACT(month FROM dateCommande)='"+month+"'  AND idIntermediaire IS NOT NULL GROUP BY idIntermediaire";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(url);
    List<String> result = new ArrayList<>();
        while (rep.next()) {
            String idIntermediaire = rep.getString("idIntermediaire");
            if (idIntermediaire != null && !idIntermediaire.isEmpty()) {
                result.add(idIntermediaire);        
        }
    }
     connection.close();
     stmt.close();
     rep.close();
    return result;
}

public List<List<Object>> get_commandeby_Month_With_Intermediaire(String month,String year,String inter) throws SQLException,Exception {
    String url ="select idintermediaire,idcommande,prixunitaire*quantiter as prixnormal,prixintermediaire*quantiter as prixintermediaire,datecommande,quotamax,quotamin from commande natural join intermediaire where extract(month from datecommande)='"+ month +"' AND EXTRACT(year FROM dateCommande) = '" + year + "' AND idintermediaire='"+inter+"'";

  Connect connexion = new Connect();
  Connection connection = connexion.dbConnect("postgres");
  Statement stmt = connection.createStatement();
  ResultSet rep = stmt.executeQuery(url);
  List<List<Object>> result = new ArrayList<>();
      while (rep.next()) {
          List<Object> response = new ArrayList<>();
          response.add(rep.getString("idIntermediaire"));
          response.add(rep.getDouble("prixnormal"));
          response.add(rep.getDouble("prixintermediaire"));
           response.add(rep.getString("datecommande"));
          response.add(rep.getDouble("quotamax"));
          response.add(rep.getDouble("quotamin"));
          result.add(response);
      }
   connection.close();
   stmt.close();
   rep.close();
  return result;
}








public List<Intermediaire> getAllIntermediaire()throws SQLException,Exception {
    String url ="SELECT  * FROM Intermediaire";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(url);
    List<Intermediaire> result = new ArrayList<>();
        while (rep.next()) {
         Intermediaire  intermediaire = new Intermediaire(rep.getString("idIntermediaire"),rep.getString("cin"),rep.getString("mdp"), rep.getDouble("quotaMax"), rep.getDouble("quotaMin"));
        result.add(intermediaire);
        }
     connection.close();
     stmt.close();
     rep.close();
    return result;
}




public Intermediaire getIntermediairebyId(String idInter)throws Exception{
         Connect connexion = new Connect();
         Connection connection = connexion.dbConnect("postgres");
         Statement stmt = connection.createStatement(); 
         String url = "SELECT * FROM intermediaire WHERE  idIntermediaire='"+idInter+"'";
         ResultSet rep = stmt.executeQuery(url);
         Intermediaire intermediaire = null;
    while (rep.next()) {
         intermediaire = new Intermediaire(rep.getString("idIntermediaire"),rep.getString("cin"),rep.getString("mdp"), rep.getDouble("quotaMax"), rep.getDouble("quotaMin"));
    }
     connection.close();
     stmt.close();
     rep.close();
    return intermediaire;
}

/*public double getSumCommande(String idfacture)throws Exception{
    String sql = "SELECT  FROM Commande WHERE idMenu='" + idfacture + "'";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(sql);
}*/


public boolean checkIntermediaire(String cin, String password) throws Exception {
    String sql = "SELECT * FROM Intermediaire WHERE cin='" + cin + "' AND mdp='" + password + "'";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(sql);
    
    if (rep.next()) {
        return true;
    } else {
        return false;
    }
   
}


public Intermediaire getIntermediaire(String cin, String password) throws Exception {
    String sql = "SELECT * FROM Intermediaire WHERE cin='" + cin + "' AND mdp='" + password + "'";
    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect("postgres");
    Statement stmt = connection.createStatement();
    ResultSet rep = stmt.executeQuery(sql);
    Intermediaire intermediaire = null;
    if (rep.next()) {
        intermediaire = new Intermediaire(rep.getString("idIntermediaire"), rep.getString("cin"), rep.getString("mdp"), rep.getDouble("quotaMax"), rep.getDouble("quotaMin"));
       
    } 
     stmt.close();
    rep.close();
    connection.close();
     return intermediaire;
}











public void update(Object o, String database) throws Exception {
    Class c = o.getClass();
    Field[] fields = c.getDeclaredFields();

    StringBuilder setClause = new StringBuilder();
    StringBuilder whereClause = new StringBuilder();
    StringBuilder sqlRequete = new StringBuilder();

    for (int i = 0; i < fields.length; i++) {
        fields[i].setAccessible(true);
        Object value = fields[i].get(o);

        if (value != null) {
            if (setClause.length() > 0) {
                setClause.append(",");
            }
            setClause.append(fields[i].getName()).append("=");

            if (value instanceof String) {
                String strvalue = (String) value;
                if (strvalue.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    if (database.equals("oracle")) {
                        setClause.append("to_date('").append(strvalue).append("','YYYY-MM-DD')");
                    } else {
                        setClause.append("'").append(strvalue).append("'");
                    }
                } else {
                    setClause.append("'").append(strvalue).append("'");
                }
            } else {
                setClause.append(value);
            }

            if (fields[i].getName().equals("id"+ c.getSimpleName()) || fields[i].getName().equals("cin")  ) {  // Supposons que l'identifiant est utilisé comme condition WHERE
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                whereClause.append(fields[i].getName()).append("=").append(value);
            }
        }
    }

    sqlRequete.append("UPDATE ").append(c.getSimpleName()).append(" SET ").append(setClause.toString());
    if (whereClause.length() > 0) {
        sqlRequete.append(" WHERE ").append(whereClause.toString());
    }

    Connect connexion = new Connect();
    Connection connection = connexion.dbConnect(database);
    connection.setAutoCommit(false);
    Statement stmt = connection.createStatement();

    int rep = stmt.executeUpdate(sqlRequete.toString());

    if (rep == 0) {
        // Mise à jour non valide
        connection.rollback();
    } else {
        // Mise à jour réussie
        connection.commit();
    }
     stmt.close();
    connection.close();
}
    
}






