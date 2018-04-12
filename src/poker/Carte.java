/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Ogun
 */
public class Carte implements Comparable<Carte>{
    
    private String laCouleur;
    private int numero;
    private ArrayList<Carte> main;
    
    
    //Constructeur
    public Carte(String couleur, int num){
        this.laCouleur = couleur;
        this.numero = num;
    }
    
    public Carte(){}
    
    //Création des cartes
    public void creationDesCartes(Table uneTable){
        String[] lesCouleurs = new String[4];
        lesCouleurs[0] = "Trèfles";
        lesCouleurs[1] = "Coeurs";
        lesCouleurs[2] = "Carreaux";
        lesCouleurs[3] = "Piques";
        
        Carte uneCarte;
        
        for(int i=0; i<lesCouleurs.length; i++)
        {
            for(int j=1; j<=13;j++)
            {
                uneCarte = new Carte(lesCouleurs[i], j);
                uneTable.ajouterCarte(uneCarte);
            }
        }
        
        System.out.println("Le jeu des cartes est complet!");
    }
    
    //Retourne la carte
    @Override
    public String toString(){
        String res = "";
        res = res + getCouleur() + " " + getNumero();
        
        return res;
    }
    
    //Rtourne la couleur de la carte
    public String getCouleur(){
        return this.laCouleur;
    }
    
    //Rtourne la valeur de la carte
    public int getNumero(){
        return this.numero;
    }
    
    //Test main couleur
    public ArrayList<Carte> mainCouleur(ArrayList<Carte> cartesTable,Joueur unJoueur){
        this.main =  new ArrayList<>();
        cartesTable.addAll(unJoueur.getLaMain());
        
        int trefle = 0;
        int coeur = 0;
        int carreau = 0;
        int pique = 0;
        String couleur= null;
        
        for (Carte carte : cartesTable){
            switch (carte.getCouleur()){
                case "Trèfles":
                    trefle = trefle++;
                    break;
                case "Coeurs":
                    coeur = coeur++;
                    break;
                case "Carreux":
                    carreau = carreau++;
                    break;
                case "Piques":
                    pique = pique++;
                    break;
            }
        }
        
        if (trefle >= 5){
            couleur = "Trèfles";
        }
        else if (coeur >= 5){
            couleur = "Coeur";
        }
        else if (carreau >=5){
            couleur = "Carreaux";
        }
        else if (pique >= 5){
            couleur = "Piques";
        }
                      
        if (couleur != null)
        {
            for (Carte carte : cartesTable){
                if (carte.getCouleur().equals(couleur)){
                    this.main.add(carte);
                }
            }
            
            Collections.sort(cartesTable);

            unJoueur.setScore(this.main.get(0).getNumero());
            return this.main;
        }
        else
        {
            return cartesTable;
        }
    }
    
    //Test main quinte flush royale
    public ArrayList<Carte> mainFlushRoyal(ArrayList cartesTable,Joueur unJoueur){
        this.main =  new ArrayList<>();
        cartesTable.addAll(unJoueur.getLaMain());
        
        
        return cartesTable;
    }
    
    //Test main quinte flush
    public ArrayList<Carte> mainFlush(ArrayList cartesSurTable,Joueur unJoueur){
        this.main =  new ArrayList<>();
        cartesSurTable.addAll(unJoueur.getLaMain());
        
        
        
        return cartesSurTable;
    }
    
    //Test main suite
    public ArrayList<Carte> mainSuite(ArrayList<Carte> cartesTable,Joueur unJoueur){
        this.main =  new ArrayList<Carte>();
        int x =0;
        int i= 0;
        cartesTable.addAll(unJoueur.getLaMain());
        
        Collections.sort(cartesTable);
        
        if (cartesTable.size() > 5){
            while (x < 4){
                if (cartesTable.get(x).getNumero() == cartesTable.get(x+3).getNumero()){
                    return cartesTable;
                }
                else if (cartesTable.get(x).getNumero() == cartesTable.get(x+2).getNumero()){
                    this.main.add((Carte) cartesTable.get(x));
                    if (cartesTable.get(x+2).getNumero() == cartesTable.get(x+3).getNumero()){
                        return cartesTable;
                    }
                }
                else if (cartesTable.get(x).equals(cartesTable.get(x+1))){
                    
                }
            }
            
            while (i <=7){
                cartesTable.get(i).getNumero();
            }
            
        }
        else if (cartesTable.size() == 5){
            
        }
            return cartesTable;
    }
        
        
        
    
    //Fonction pour trier les cartes, utilisé par mainSuite
    @Override
    public int compareTo(Carte carte) {
	
	int compareNumero = ((Carte) carte).getNumero(); 
		
	//ascending order
	//return this.numero - compareNumero;
		
	//descending order
	return compareNumero - this.numero;	
    }
}
