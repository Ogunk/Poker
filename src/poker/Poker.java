/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

/**
 *
 * @author Ogun
 */
public class Poker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Création joueur
        Joueur unJoeur = new Joueur("Ogün");
        Joueur deuxJoueur = new Joueur("Ezziaro");
        
        //Création table + ajout joueur
        Table laTable = new Table();
        laTable.ajouterJoueur(unJoeur);
        laTable.ajouterJoueur(deuxJoueur);
        
        //Création des cartes
        Carte lesCartes = new Carte();
        lesCartes.creationDesCartes(laTable);
        
        //initialisation
        laTable.initTable();

    }
    
}
