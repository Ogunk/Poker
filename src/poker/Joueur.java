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
    private String combinaison;
    private ArrayList<Carte> laMain;
    private ArrayList<Carte> mainGagnante;

    private int argent;
    private int miseEnCours;
    private int score;

    private boolean bouton;
    private boolean petiteBlind;
    private boolean grosseBlind;
    private boolean fold;

    public static final int MAIN = 2;

    //Constructeur
    public Joueur(String n) {
        this.laMain = new ArrayList<>();
        this.nom = n;
        this.argent = 0;
        this.miseEnCours = 0;
        this.score = 0;
        this.combinaison = null;

        this.bouton = false;
        this.petiteBlind = false;
        this.grosseBlind = false;
        this.fold = false;
    }

    //Le joueur reçoit ses deux cartes
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

    //Clear la main du joueur
    public void clearMain() {
        this.laMain.clear();
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
        this.score = score;
    }

    //Retourne le score du joueur
    public int getScore() {
        return this.score;
    }

    //Reset le score
    public void clearScore() {
        this.score = 0;
    }

    //Retourne le nom du joueur
    public String getNom() {
        return this.nom;
    }

    //Retourne le détail du joueur
    public String joueurToString() {
        String res = "";
        res = res + "Nom : " + this.nom + " | Solde : " + this.argent;

        return res;
    }

    //Miser son argent
    public void miserArgent(int mise) {

        if (mise > 0) {
            this.argent = this.argent - mise;
            this.miseEnCours = this.miseEnCours + mise;
        } else if (mise == 0) {
            seCoucher();
        }

    }

    //Affecte la combinaison de carte du joueur
    public void setCombinaison(String combi) {
        this.combinaison = combi;
    }

    //Retourne la combinaison de carte du joueur
    public String getCombinaison() {
        return this.combinaison;
    }

    //Reset la combinaison de carte du joueur
    public void clearCombinaison() {
        this.combinaison = null;
    }

    //Affecte la main forte du joueur
    public void setMainGagnante(ArrayList<Carte> main) {
        this.mainGagnante = main;
    }

    //Affecte la main forte du joueur
    public ArrayList<Carte> getMainGagnante() {
        return this.mainGagnante;
    }

    //Clear la main du joueur
    public void clearMainGagnante() {
        this.mainGagnante.clear();
    }

    //Se coucher
    public void seCoucher() {
        this.fold = true;
    }

    //Retourne la variable fold
    public boolean getCoucher() {
        System.out.println(getNom() + " dors comme un dèp");
        return this.fold;
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
