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
public class Joueur {

    private String nom;
    private ArrayList<Carte> laMain;
    private int argent;
    private int miseEnCours;
    private int score;

    private boolean bouton;
    private boolean petiteBlind;
    private boolean grosseBlind;

    public static final int MAIN = 2;

    //Constructeur
    public Joueur(String n) {
        this.laMain = new ArrayList<>();
        this.nom = n;
        this.argent = 0;
        this.miseEnCours = 0;
        this.score = 0;

        this.bouton = false;
        this.petiteBlind = false;
        this.grosseBlind = false;
    }

    //Le joueur reçoit ses cartes
    public void setLaMain(Carte uneCarte) {
        if (this.laMain.size() < MAIN) {
            this.laMain.add(uneCarte);
        } else {
            System.out.print("Main déjà distribué !");
        }
    }

    //Retourne la main d'un joueur
    public ArrayList getLaMain() {
        return this.laMain;
    }

    //Affecte le bouton à un joueur
    public void bouton(boolean b) {
        this.bouton = b;
        this.petiteBlind = false;
        this.grosseBlind = false;
    }

    //Affecte la petite blinde à un joueur
    public void petiteBlind(boolean b) {
        this.petiteBlind = b;
        this.bouton = false;
        this.grosseBlind = false;
    }

    //Affecte la grosse blinde à un joueur
    public void grosseBlind(boolean b) {
        this.grosseBlind = b;
        this.bouton = false;
        this.petiteBlind = false;
    }

    //Reset les roles a false de tout les joueurs
    public void resetRole() {
        this.bouton = false;
        this.petiteBlind = false;
        this.grosseBlind = false;
    }

    //Retourne les cartes en main
    public String mainToString() {
        String res = "";

        for (int i = 0; i < this.laMain.size(); i++) {
            res = res + "Carte numéro " + (i + 1) + " : " + this.laMain.get(i).toString() + "\n";
        }

        return res;
    }

    //Retourne le solde actuel du joueurs
    public int getArgent() {
        return this.argent;
    }

    //Modifie le solde actuel du joueur
    public void setArgent(int argent) {
        this.argent = argent;
    }

    //Modifie le score du joueur
    public void setScore(int score) {
        this.argent = score;
    }

    //Retourne le détail du joueur
    public String joueurToString() {
        String res = "";
        res = res + "Nom : " + this.nom + " | Solde : " + this.argent;

        return res;
    }

    //Miser son argent
    public void miserArgent(int sommeAMiser) {
        while (this.argent < sommeAMiser) {
            System.out.println("Impossible de miser une somme supérieur à votre solde, veuillez miser quelque chose égal ou inférieur à : " + this.argent);
        }

        this.argent = this.argent - sommeAMiser;
        this.miseEnCours = this.miseEnCours + sommeAMiser;

    }

    //Se coucher
    public void seCoucher() {

    }

    //Suivre
    public void suivre() {

    }

    //Retourne un string du role du joueur
    public String getRole() {
        if (this.bouton) {
            return "Bouton";
        } else if (this.grosseBlind) {
            return "Grosse Blind";
        } else if (this.petiteBlind) {
            return "Petite Blind";
        } else {
            return "Joueur";
        }
    }

}
