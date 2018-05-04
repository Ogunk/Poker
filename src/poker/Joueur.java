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
public class Joueur implements Comparable<Joueur> {

    private String nom;
    private String combinaison;
    private ArrayList<Carte> laMain;
    private ArrayList<Carte> mainGagnante;

    private int argent;
    private int miseEnCours;
    private int score;

    private String role;
    private boolean check;

    public static final int MAIN = 2;

    //Constructeur
    public Joueur(String n) {
        this.laMain = new ArrayList<>();
        this.mainGagnante = new ArrayList<>();
        this.nom = n;
        this.argent = 0;
        this.miseEnCours = 0;
        this.score = 0;
        this.combinaison = null;

        this.role = "Joueur";
        this.check = false;
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
    public void bouton() {
        this.role = "Bouton";
    }

    //Affecte la petite blinde à un joueur
    public void petiteBlind() {
        this.role = "Petite blind";
    }

    //Affecte la grosse blinde à un joueur
    public void grosseBlind() {
        this.role = "Grosse blind";
    }

    //Reset les roles a false de tout les joueurs
    public void resetRole() {
        this.role = "Joueur";
    }

    //Retourne les cartes en main
    public String mainToString() {
        String res = "";

        for (int i = 0; i < this.laMain.size(); i++) {
            res = res + "Carte numéro " + (i + 1) + " : " + this.laMain.get(i).toString() + "\n";
        }

        return res;
    }

    //Retourne la main gagnante du joueur
    public String mainGagnanteToString() {
        String res = "";
        System.out.println(this.getNom() + " - main " + this.combinaison + " taille main : " + this.mainGagnante.size() + " - score : " + this.getScore());
        for (int i = 0; i < this.mainGagnante.size(); i++) {
            res = res + "Carte numéro " + (i + 1) + " : " + this.mainGagnante.get(i).toString() + "\n";
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

    public void gagneArgent(int banque) {
        this.argent += banque;
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
        res = res + "Nom : " + this.getNom() + " | Solde : " + this.getArgent() + " | Role : " + this.getRole();

        return res;
    }

    //Miser son argent
    public int miserArgent(int mise) {
        this.argent = this.argent - mise;
        this.miseEnCours = this.miseEnCours + mise;
        return mise;
    }

    //Retourne la mise en cours du joueur
    public int getMiseEnCours() {
        return this.miseEnCours;
    }

    //Clear la variable miseEnCours
    public void clearMiseEnCours() {
        this.miseEnCours = 0;
    }

    //Mise autant que la mise la plus haute
    public void suivreMise() {

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
        if (!this.mainGagnante.isEmpty()) {
            this.mainGagnante.clear();
        }
    }

    //Checker
    public void check() {
        this.check = true;
    }

    //Clear check
    public void clearCheck() {
        this.check = false;
    }

    //Retourne la variable check
    public boolean getCheck() {
        return this.check;
    }

    //Retourne la variable fold
    public boolean getCoucher() {
        return this.check;
    }

    //Suivre
    public void suivre() {

    }

    //Retourne un string du role du joueur
    public String getRole() {
        return this.role;
    }

    //Fonction pour trier les cartes, utilisé par mainSuite
    public int compareTo(Joueur joueur) {

        int compareNumero = ((Joueur) joueur).getScore();

        //ascending order
        //return this.numero - compareNumero;
        //descending order
        return compareNumero - this.score;
    }
}
