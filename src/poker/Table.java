/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Ogun & HariiBo
 */
public class Table extends Carte {

    private ArrayList<Joueur> lesJoueurs;
    private ArrayList<Carte> lesCartes;
    private ArrayList<Carte> cartesTable;

    private int argentJoueur;
    private int petiteBlind;
    private int grosseBlind;

    private static final int NOMBRE_DE_CARTES = 52;

    //Constructeur
    public Table() {
        this.lesJoueurs = new ArrayList<>();
        this.lesCartes = new ArrayList<>();
        this.cartesTable = new ArrayList<>();

        this.argentJoueur = 1000;
        this.petiteBlind = 5;
        this.grosseBlind = 10;
    }

    //Initialise le jeu
    public void initTable() {
        this.setArgentJoueurs();
        this.melangerLesJoueurs();
        this.joueurRole();
        this.melangerLesCartes();
        this.distribuerCartes();

        for (Joueur unJoueur : this.lesJoueurs) {
            System.out.println(unJoueur.joueurToString());
            System.out.println(unJoueur.mainToString());
        }

        this.afficherFlop();
        this.afficherTurn();
        this.afficherRiver();

        System.out.println("Voici toutes les cartes sur la table: ");
        Collections.sort(this.cartesTable);
        for (Carte uneCarte : this.cartesTable) {
            System.out.println(uneCarte.toString());
        }

        System.out.println("\n");

        for (Joueur unJoueur : this.lesJoueurs) {
            System.out.println("* " + unJoueur.getNom());
            if (Carte.mainFlushRoyal(this.cartesTable, unJoueur)) {
            } else if (Carte.mainFlush(this.cartesTable, unJoueur)) {
            } else if (Carte.mainCarre(this.cartesTable, unJoueur)) {
            } else if (Carte.mainFull(this.cartesTable, unJoueur)) {
            } else if (Carte.mainCouleur(this.cartesTable, unJoueur)) {
            } else if (Carte.mainSuite(this.cartesTable, unJoueur)) {
            } else if (Carte.mainBrelan(this.cartesTable, unJoueur)) {
            } else if (Carte.mainPaires(this.cartesTable, unJoueur)) {
            } //else if (Carte.hauteur(this.carteSurTable, unJoueur)){
            //}

            if (unJoueur.getCombinaison() != null) {
                System.out.println("###### " + unJoueur.getCombinaison() + " - Score : " + unJoueur.getScore());
                System.out.println(unJoueur.getMainGagnante());
            }
        }
    }

    //Ajouter un joueur à la table
    public void ajouterJoueur(Joueur unJoueur) {
        this.lesJoueurs.add(unJoueur);
    }

    //Donne le rôle de Bouton, Petite Blind et grosse blind
    public void joueurRole() {
        this.lesJoueurs.get(0).bouton(true);
        this.lesJoueurs.get(1).petiteBlind(true);
        this.lesJoueurs.get(2).grosseBlind(true);
    }

    //Rotation des rôles durant à la fin de chaque tours
    public void joueurChangeRole() {
        Collections.rotate(this.lesJoueurs, -1);
        this.lesJoueurs.get(0).bouton(true);
        this.lesJoueurs.get(1).petiteBlind(true);
        this.lesJoueurs.get(2).grosseBlind(true);

        for (int i = 3; i < this.lesJoueurs.size(); i++) {
            this.lesJoueurs.get(i).resetRole();
        }
    }

    //Ajoute les cartes à la table
    public void ajouterCarte(Carte uneCarte) {
        if (this.lesCartes.size() < NOMBRE_DE_CARTES) {
            this.lesCartes.add(uneCarte);
        } else {
            System.out.println("Le jeu de carte est complet");
        }
    }

    //Mélanger le jeu de carte
    public void melangerLesCartes() {
        if (this.lesCartes.size() == NOMBRE_DE_CARTES) {
            Collections.shuffle(this.lesCartes);
            System.out.println("Les cartes ont était mélangés !\n");
        } else {
            System.out.println("Le jeu de carte n'est pas complet!");
        }
    }

    //Mélange les joueurs pour déterminer les positions.
    public void melangerLesJoueurs() {

        Collections.shuffle(this.lesJoueurs);
        System.out.println("Les joueurs sont prêts !");
    }

    //On vérifie si tout les joueurs ont reçu leur cartes
    public boolean joueurPret() {
        boolean pret = false;

        for (Joueur unJoueur : this.lesJoueurs) {
            pret = false;
            if (unJoueur.getLaMain().size() == 2) {
                pret = true;
            }
        }

        return pret;
    }

    //Distribue les cartes aux joueurs de la table
    public void distribuerCartes() {
        while (!this.joueurPret()) {
            for (Joueur leJoueur : this.lesJoueurs) {
                leJoueur.setLaMain(this.lesCartes.get(0));

                this.lesCartes.remove(0);
            }
        }
    }

    //Affiche le flop, les 3 première carte sur la table, brule la carte du haut avant d'afficher les suivantes
    public void afficherFlop() {
        this.lesCartes.remove(0);

        for (int i = 0; i < 3; i++) {
            this.cartesTable.add(this.lesCartes.get(0));
            this.lesCartes.remove(0);
        }
    }

    //Affiche la 4eme carte sur la table, on brule la 1ere carte
    public void afficherTurn() {
        this.lesCartes.remove(0);

        this.cartesTable.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);
    }

    //Affichage de la derniere carte, on brule la 1ere carte
    public void afficherRiver() {
        this.lesCartes.remove(0);

        this.cartesTable.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);
    }

    //Retourne l'argent que cette attribue aux joueurs
    public int getArgent() {
        return this.argentJoueur;
    }

    //Initialise l'argent des joueurs en fonction de la somme de la table
    public void setArgentJoueurs() {
        for (Joueur unJoueur : this.lesJoueurs) {
            unJoueur.setArgent(getArgent());
        }
    }

    //Déroulement d'un tour de poker
    public void jouer() {
        //Première mise avant l'affichage du flop
        miseTourTable();

        //Si des joueurs jouent le tour
    }

    //Parti pari entre chaque tour
    public void miseTourTable() {
        for (Joueur unJoueur : this.lesJoueurs) {
            if (!unJoueur.getCoucher()) {
                System.out.println(unJoueur.getNom() + "Combien misez-vous ?");
                Scanner sc = new Scanner(System.in);
                int mise = sc.nextInt();
                while (unJoueur.getArgent() < mise) {
                    System.out.println("Impossible de miser une somme supérieur à votre solde,"
                            + " veuillez miser quelque chose égal ou inférieur à : " + unJoueur.getArgent());
                    mise = sc.nextInt();
                }
                unJoueur.miserArgent(mise);
                System.out.println(unJoueur.getNom() + " mise : " + mise);
            }
        }
    }

    //Recupère les cartes des mains des joueurs et de la table
    public boolean recupCartes() {
        this.lesCartes.addAll(this.cartesTable);
        this.cartesTable.clear();

        for (Joueur unJoueur : this.lesJoueurs) {
            this.lesCartes.addAll(unJoueur.getLaMain());
            unJoueur.clearMain();
        }
        return this.cartesTable.size() == 52;
    }

}
