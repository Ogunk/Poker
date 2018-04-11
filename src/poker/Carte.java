/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;

/**
 *
 * @author Ogun
 */
public class Carte {
    
    private String laCouleur;
    private int numero;
    private ArrayList<Carte> main;
    
    
    //Constructeur
    public Carte(String couleur, int num)
    {
        this.laCouleur = couleur;
        this.numero = num;
    }
    
    public Carte(){}
    
    //Création des cartes
    public void creationDesCartes(Table uneTable)
    {
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
    public String toString()
    {
        String res = "";
        res = res + getCouleur() + " " + getNumero();
        
        return res;
    }
    
    //Rtourne la couleur de la carte
    public String getCouleur()
    {
        return this.laCouleur;
    }
    
    //Rtourne la valeur de la carte
    public int getNumero()
    {
        return this.numero;
    }
    
    //Test couleur
    public ArrayList<Carte> mainCouleur(ArrayList<Carte> cartesSurTable,Joueur unJoueur)
    {
        this.main =  new ArrayList<>();
        cartesSurTable.addAll(unJoueur.getLaMain());
        
        int score;
        int trefle = 0;
        int coeur = 0;
        int carreau = 0;
        int pique = 0;
        
        for (Carte carte : cartesSurTable)
        {
            switch (carte.getCouleur())
            {
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
        
        if ()
        {
            score = this.main.get(0).getNumero();
            for (Carte c : this.main)
            {
                if (c.getNumero() > score)
                {
                    score = c.getNumero();
                }
            }
            unJoueur.setScore(score);
            
        }
        else
        {
            return cartesSurTable;
        }
        return cartesSurTable;
    }
    
    //Test main quinte flush royale
    public void mainFlushRoyal(ArrayList cartesSurTable,ArrayList mainJoueur)
    {
        this.main =  new ArrayList<>();
        this.main.addAll(cartesSurTable);
        this.main.addAll(mainJoueur);
        
        
    }
    
    //Test main quinte flush
    public void mainFlush(ArrayList cartesSurTable,ArrayList mainJoueur)
    {
        this.main =  new ArrayList<>();
        this.main.addAll(cartesSurTable);
        this.main.addAll(mainJoueur);
    }
            
}
