var tbody = document.querySelector("#tbodies");
var month = document.getElementById("choiceMonth");
var volana = 0;
var taona = 0;
var donnergraphChiffreA = [];
var donnergraphChiffreR = [];
var monGraphique;

month.addEventListener("change",()=>{
  volana = month.value;
  var year = document.getElementById("choiceyear");
  taona = year.value;
  getStatistiquebyMonth(month.value.trim(),year.value.trim());
  });

  
  function updateDonnerGraph(chiffreToAdd, datecom, donnergraph) {

    var existingEntry = donnergraph.find(entry => entry.date === datecom);
  
    if (existingEntry) {
      existingEntry.chiffre += chiffreToAdd;
    } else {

      var dataC = {
        date: datecom,
        chiffre: chiffreToAdd
      };
      donnergraph.push(dataC);
    }
  }




function getStatistiquebyMonth(month,year){
  var xhr;
  try {  xhr = new ActiveXObject('Msxml2.XMLHTTP');   }
  catch (e) 
  {
      try {   xhr = new ActiveXObject('Microsoft.XMLHTTP'); }
      catch (e2) 
      {
         try {  xhr = new XMLHttpRequest();  }
         catch (e3) {  xhr = false;   }
       }
  }
  xhr.open("GET","dashboard.jsp?month="+month +"&year="+year);      
  xhr.send(null);

  xhr.onreadystatechange = ()=>{
  if (xhr.readyState == 4 && xhr.status == 200) {  
    donnergraphChiffreA.splice(0,donnergraphChiffreA.length);   
    donnergraphChiffreR.splice(0,donnergraphChiffreR.length);

    tbody.innerHTML="";
    var chiffreAffaire = document.querySelector("#chiffreAffaire");
    var chargeResto = document.querySelector("#chargeResto");
    var chargeIntermediaire = document.querySelector("#intermed");
    var benef =  document.querySelector("#benef");
    var CUMULar = document.querySelector("#chiffreAffaireCumul");
    chiffreAffaire.innerHTML="";
    chargeResto.innerHTML="";
    chargeIntermediaire.innerHTML="";
    benef.innerHTML="";
    CUMULar.innerHTML="";
    var sommechiffreAffaire = 0;
    var chargeresto = 0;
    var totalcharge =0;
    

      var rep = JSON.parse(xhr.responseText);
      var commandes = rep.commande;
      //commandes.sort((a,b) => new Date(a.dateCommande)-new Date(b.dateCommande));
      var intermediaires = rep.intermediaire;
      var chiffreAR = rep.chiffreresto;
      localStorage.removeItem('intermediaire');
      localStorage.setItem('intermediaire', JSON.stringify(intermediaires));
      
      intermediaires.forEach(intermediaire =>{
        var hr = document.createElement("hr");
        var span = document.createElement("span");
        span.textContent=intermediaire.idIntermediaire+" : "+intermediaire.gain+" Ar";
        chargeIntermediaire.append(span); chargeIntermediaire.append(hr);   

        totalcharge+= parseFloat(intermediaire.gain);
      });
      commandes.forEach(commande => {
        //chiffre affaire
        sommechiffreAffaire+= parseFloat(commande.prixUnitaire)*parseInt(commande.quantiter);
        //calcul charge restaurant
         chargeresto+= parseFloat(commande.prixReviens)*parseInt(commande.quantiter);
         
        //affichage tableau commande
           var tr = document.createElement("tr");  
           var nom = document.createElement("td"); nom.textContent= commande.Menu;
           var prixU = document.createElement("td");prixU.textContent = commande.prixUnitaire;
           var prixI = document.createElement("td");prixI.textContent = commande.prixIntermediaire;
           var prixR = document.createElement("td"); prixR.textContent = commande.prixReviens;
           var qtt =  document.createElement("td"); qtt.textContent = commande.quantiter;
           var date = document.createElement("td"); date.textContent = commande.dateCommande;
           var client = document.createElement("td"); client.textContent = commande.idClient;
           var intermediaire = document.createElement("td");intermediaire.textContent = commande.idIntermediaire;
tr.append(nom);tr.append(prixU);tr.append(prixI);tr.append(prixR);tr.append(qtt);tr.append(date);tr.append(client);tr.append(intermediaire);
tbody.append(tr);




var valuePrixReviens = parseFloat(commande.prixReviens)*parseInt(commande.quantiter);
var chiffreToAdd = parseFloat(commande.prixUnitaire) * parseInt(commande.quantiter);
var datecom = commande.dateCommande;
updateDonnerGraph(chiffreToAdd, datecom, donnergraphChiffreA);
updateDonnerGraph(valuePrixReviens,datecom,donnergraphChiffreR);
});

        chiffreAffaire.textContent = sommechiffreAffaire+" Ar";
        chargeResto.textContent = chargeresto + " Ar"; 
        CUMULar.textContent="cummul Ca:"+ chiffreAR + "AR";
        
        totalcharge += chargeresto;

        var benefice = sommechiffreAffaire - totalcharge;
        benef.textContent = benefice+ " Ar";
            
        //met en place le lien pour allez voir les data
        localStorage.removeItem('date');
        let date = {mois: volana,year:taona};
      localStorage.setItem('date', JSON.stringify(date));
     

      

// Données du graphique
const joursSemaine = [];
const donneesGraphique1 = [];
const donneesGraphique2 = [];

for (let i = 1; i <= 30; i++) {
  let valeurPourCeJour = 0;
  let valuereviens = 0;
  for (let index = 0; index < donnergraphChiffreA.length; index++) {
    var maDate = new Date(donnergraphChiffreA[index].date);
    var jourDuMois = maDate.getDate();

    if (jourDuMois === i) {
      valeurPourCeJour += donnergraphChiffreA[index].chiffre;
       valuereviens += donnergraphChiffreR[index].chiffre;
    }
  }

  donneesGraphique1.push(valeurPourCeJour);
  donneesGraphique2.push(valuereviens);
}


for (let index = 1; index <= 30 ; index++) {
  joursSemaine.push(index);
}

// Configuration du graphique
const configGraphique = {
  type: 'bar',
  data: {
    labels: joursSemaine,
    datasets: [{
      label: 'chiffre affaire',
      data: donneesGraphique1,
      borderColor: '#007bff',
      borderWidth: 1
    }, {
      label: 'charge',
      data: donneesGraphique2,
      borderColor: '#ff6347',
      borderWidth: 1
    }]
  },
  options: {
    plugins: {
      legend: { display: false },
      tooltip: { boxPadding: 3 }
    },
    scales: {
      x: {
        title: {
          display: true,
          text: 'jour du mois'
        }
      },
      y: {
        type: 'linear', // Ou 'category' si tu as des étiquettes catégorielles
        position: 'left', // Position de l'axe y
        title: {
          display: true,
          text: 'chiffre affaire '
        }
      }
    }
  }
};

// Création du graphique
const ctx = document.getElementById('monGraphique');
if (typeof monGraphique !== 'undefined') {
  // Si le graphique existe, détruis-le
  monGraphique.destroy();
}
monGraphique = new Chart(ctx, configGraphique);


     



  }
}
}

