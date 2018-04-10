/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Ogun
 */
public class Table {
    
    private ArrayList<Joueur> lesJoueurs;
    private ArrayList<Carte> lesCartes;
    private ArrayList<Carte> carteSurTable;
    
    private int argentJoueur;
    private int petiteBlinde;
    private int grosseBlinde;
    
    private static final int NOMBRE_DE_CARTES = 52;
    
    //Constructeur
    public Table()
    {
        this.lesJoueurs = new ArrayList<>();
        this.lesCartes = new ArrayList<>();
        this.carteSurTable = new ArrayList<>();
        this.argentJoueur = 1000;
    }
    
    //Initialise le jeu
    public void initTable()
    {
        this.choisirJoueurBouton();
        this.melangerLesCartes();
        this.distribuerCartes();
        
        for(Joueur unJoueur : this.lesJoueurs)
        {
            System.out.println(unJoueur.joueurToString());
            System.out.println(unJoueur.mainToString());
        }
        
        this.afficherFlop();
        this.afficherTurn();
        this.afficherRiver();
        
        System.out.println("Voici toutes les cartes sur la table: ");
        for(Carte uneCarte : this.carteSurTable)
        {          
            System.out.println(uneCarte.toString());
        }
    }
    
    //Ajouter un joueur à la table
    public void ajouterJoueur(Joueur unJoueur)
    {
        this.lesJoueurs.add(unJoueur);
    }
    
    //Ajoute les cartes à la table
    public void ajouterCarte(Carte uneCarte)
    {
        if(this.lesCartes.size() < NOMBRE_DE_CARTES)
        {
            this.lesCartes.add(uneCarte);
        }
        else
        {
            System.out.println("Le jeu de carte est complet");
        }
        
    }
    
    //Mélanger le jeu de carte
    public void melangerLesCartes()
    {
        if(this.lesCartes.size() == NOMBRE_DE_CARTES)
        {
            Collections.shuffle(this.lesCartes);
            System.out.println("Les cartes ont était mélangés ! \n");
        }
        else
        {
            System.out.println("Le jeu de carte n'est pas complet!");
        }
    }
    
    //On vérifie si tout les joueurs ont reçu leur cartes
    public boolean joueurPret()
    {
        boolean pret = false;
        
        for(Joueur unJoueur : this.lesJoueurs)
        {
            pret = false;
            if(unJoueur.getLaMain().size()==2)
            {
                pret = true;
            }
        }
        
        return pret;
    }
    
    //Choisie un personne au hasard pour qu'il ai le bouton
    public void choisirJoueurBouton()
    {
        Random rand = new Random();
        int n = rand.nextInt(this.lesJoueurs.size()) + 1 ;
        
        this.lesJoueurs.get(n).setLeBouton(true);
    }
    
    //Distribue les cartes aux joueurs de la table
    public void distribuerCartes()
    {       
        while(!this.joueurPret())
        {
            for(Joueur leJoueur : this.lesJoueurs)
            {
                leJoueur.setLaMain(this.lesCartes.get(0));
                this.lesCartes.remove(0);         
            }
        }
    } 
    
    //Affiche le flop, les 3 première carte sur la table, brule la carte du haut avant d'afficher les suivantes
    public void afficherFlop()
    {
        System.out.println("Affichage du Flop, on brule la première carte.");
        this.lesCartes.remove(0);
        System.out.println("Carte brulé.");
        System.out.println("Placement du Flop : ");
        for(int i=0; i<3;i++)
        {
            this.carteSurTable.add(this.lesCartes.get(0));
            this.lesCartes.remove(0);
        }
        
        for(Carte uneCarte : this.carteSurTable)
        {
            System.out.println(uneCarte.toString());
        }
        
    }
    
    //Affiche la 4eme carte sur la table, on brule la 1ere carte
    public void afficherTurn()
    {
        System.out.println("Affichage du Turn, on brule la première carte.");
        //System.out.println(this.lesCartes.get(0).toString());
        this.lesCartes.remove(0);
        //System.out.println(this.lesCartes.get(0).toString());
        System.out.println("Carte brulé.");
        System.out.println("Placement du Turn : ");
        this.carteSurTable.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);
        System.out.println(this.carteSurTable.get(this.carteSurTable.size()-1).toString());
    }
    
    //Affichage de la derniere carte, on brule la 1ere carte
    public void afficherRiver()
    {
        System.out.println("Affichage de la River, on brule la première carte.");
        //System.out.println(this.lesCartes.get(0).toString());
        this.lesCartes.remove(0);
        //System.out.println(this.lesCartes.get(0).toString());
        System.out.println("Carte brulé.");
        System.out.println("Placement de la River : ");
        this.carteSurTable.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);
        System.out.println(this.carteSurTable.get(this.carteSurTable.size()-1).toString());
    }
    
    //Retourne l'argent que cette attribue aux joueurs
    public int getArgent()
    {
        return this.argentJoueur;
    }
    
    //Joue un tour de poker
    public void jouer()
    {
        
    } 
          
}