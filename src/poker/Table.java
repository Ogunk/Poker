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
    private ArrayList<Joueur> joueursActifs;
    private ArrayList<Joueur> spectateurs;
    private ArrayList<Carte> lesCartes;
    private ArrayList<Carte> cartesTable;
    private ArrayList<Carte> trash;

    private int argentJoueur;
    private int banque;
    private int potTour;
    private int miseHaute;
    private int petiteBlind;
    private int grosseBlind;

    private boolean premierTour;

    private String joueurBouton;

    private static final int NOMBRE_DE_CARTES = 52;

    //Constructeur
    public Table() {
        this.lesJoueurs = new ArrayList<>();
        this.joueursActifs = new ArrayList<>();
        this.lesCartes = new ArrayList<>();
        this.cartesTable = new ArrayList<>();
        this.trash = new ArrayList<>();
        this.spectateurs = new ArrayList<>();

        this.argentJoueur = 1000;
        this.petiteBlind = 5;
        this.grosseBlind = 10;
        this.premierTour = true;
        this.miseHaute = this.grosseBlind;
        this.potTour = 0;
    }

    //Initialise le jeu
    public void initTable() {
        //Début d'une partie
        this.setArgentJoueurs();
        this.melangerLesJoueurs();
        this.joueurRole();

        System.out.println(this.lesCartes.size());

        while (this.lesJoueurs.size() > 1) {
            this.jouer();

        }
        System.out.println("### While Joueur fini ###");
    }

    //Ajouter un joueur à la table
    public void ajouterJoueur(Joueur unJoueur) {
        this.lesJoueurs.add(unJoueur);
    }

    //Donne le rôle de Bouton, Petite Blind et grosse blind
    public void joueurRole() {
        this.lesJoueurs.get(0).bouton();
        this.lesJoueurs.get(1).petiteBlind();
        this.lesJoueurs.get(2).grosseBlind();

        Collections.rotate(this.lesJoueurs, -1);
    }

    //Rotation des rôles durant à la fin de chaque tours
    public void rotationRole() {
        System.out.println("Rotation 1");
        for (Joueur j : this.lesJoueurs) {
            System.out.println(j.joueurToString());
        }
        this.lesJoueurs.get(0).bouton();
        this.lesJoueurs.get(1).petiteBlind();
        this.lesJoueurs.get(2).grosseBlind();

        for (int i = 3; i < this.lesJoueurs.size(); i++) {
            this.lesJoueurs.get(i).resetRole();
        }
        Collections.rotate(this.lesJoueurs, -1);
    }

    // Double la petite et la grosse blind
    public void doubleBlind() {
        this.petiteBlind = this.petiteBlind * 2;
        this.grosseBlind = this.grosseBlind * 2;
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

    //On vérifie si tout les joueurs ont bien misé
    public boolean joueurAMiser() {
        int nbJPret = 0;
        int check = 0;
        boolean pret = false;

        for (Joueur unJoueur : this.joueursActifs) {
            if (unJoueur.getMiseEnCours() == this.miseHaute && this.miseHaute != 0) {
                nbJPret++;
            }
            if (unJoueur.getCheck()) {
                check++;
            }
        }

        if (nbJPret == this.joueursActifs.size()) {
            pret = true;
        } else if (this.joueursActifs.size() == 1) {
            pret = true;
        } else if (check == this.joueursActifs.size()) {
            pret = true;
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

    //Pose le flop sur la table
    public void poserFlop() {
        this.trash.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);

        for (int i = 0; i < 3; i++) {
            this.cartesTable.add(this.lesCartes.get(0));
            this.lesCartes.remove(0);
        }
    }

    //Affiche le flop, les 3 première carte sur la table, brule la carte du haut avant d'afficher les suivantes
    public void afficherFlop() {
        System.out.println("Affichage du flop");
        System.out.println(this.cartesTable.get(0).toString());
        System.out.println(this.cartesTable.get(1).toString());
        System.out.println(this.cartesTable.get(2).toString());
    }

    //Affiche la 4eme carte sur la table, on brule la 1ere carte
    public void poserTurn() {
        this.trash.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);

        this.cartesTable.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);
    }

    //Affiche la river
    public void afficherTurn() {
        System.out.println("Affichage du turn");
        System.out.println(this.cartesTable.get(3).toString());
    }

    //Pose de la derniere carte, on brule la 1ere carte
    public void poserRiver() {
        this.trash.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);

        this.cartesTable.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);
    }

    //Affiche la river
    public void afficherRiver() {
        System.out.println("Affichage de la river");
        System.out.println(this.cartesTable.get(4).toString());
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
        //Mélange des cartes
        this.melangerLesCartes();

        //Distribution des cartes
        this.distribuerCartes();

        //Test
        for (Joueur joueur : this.lesJoueurs) {
            System.out.println(joueur.joueurToString());
            System.out.println(joueur.mainToString());
        }

        //Pose du flop sur la table, face cachée
        this.poserFlop();
        System.out.println("\n######### MISE PREMIER TOUR #########");
        //Première mise avant l'affichage du flop
        this.tourParole();
        this.addBanque();

        //Affichage du flop
        this.afficherFlop();
        System.out.println("\n######### MISE FLOP #########");
        System.out.println(this.grosseBlind);
        //Nouveau tour de parole avant l'affichage du turn
        this.tourParole();
        this.addBanque();

        //Affichage du turn
        this.poserTurn();
        this.afficherTurn();
        System.out.println("\n######### MISE TURN #########");
        //Nouveau tour de mise avant l'affichage du turn
        this.tourParole();
        this.addBanque();

        //Affichage de la River
        this.poserRiver();
        this.afficherRiver();
        System.out.println("\n######### MISE RIVER #########");
        //Dernier tour de mise
        this.tourParole();
        this.addBanque();

        //TEST - A supprimer
        System.out.println("Voici toutes les cartes sur la table: ");
        for (Carte uneCarte : this.cartesTable) {
            System.out.println(uneCarte.toString());
        }

        //Met la main la plus forte de chaque joueur
        this.joueurMain();

        //Election main Gagnante et affichage main gagnante
        if (this.joueursActifs.size() > 1) {
            this.afficherMainGagnante(this.mainGagnante());
        } else if (this.joueursActifs.size() == 1) {
            this.afficherMainGagnante(this.joueursActifs);
        }

        //Gagnant récupère la banque
        this.joueurGagneBanque(this.joueursActifs);

        //Récupère les cartes
        if (this.recupCartes()) {
            System.out.println("Les cartes ont étaient récup");
        } else {
            System.out.println("IL MANQUE DES CARTES !");
        }

        //Clear toutes les variables pour un nouveau tour
        this.clearVariable();

        System.out.println("################################## Test 3");
        //Passe les joueurs sans argents en spectateur
        this.spectateur();
        System.out.println("################################## Test 4");

        //Augmentation des blinds
        this.augmentationBlind();

        //Rotation du bouton/ PB / GB
        this.rotationRole();
        System.out.println("Après rotation role");
    }

    //Augmente les blinds lorsque le bouton a fait un tour
    public void augmentationBlind() {
        if (this.joueurBouton != null) {
            for (Joueur unJoueur : this.lesJoueurs) {
                if (unJoueur.getNom().equals(this.joueurBouton)) {
                    this.grosseBlind = this.grosseBlind * 2;
                    this.petiteBlind = this.petiteBlind * 2;
                }
            }
        } else {
            for (Joueur unJoueur : this.lesJoueurs) {
                if (unJoueur.getRole().equals("Bouton")) {
                    this.joueurBouton = unJoueur.getNom();
                    break;
                }
            }
        }
    }

    //Parole pour les joueurs entre chaque tour
    public void tourParole() {
        if (this.premierTour) {
            this.joueursActifs.addAll(this.lesJoueurs);
            //Mise obligatoire PB GB
            this.addPotTour(this.joueursActifs.get(0).miserArgent(this.petiteBlind));
            this.addPotTour(this.joueursActifs.get(1).miserArgent(this.grosseBlind));

            Collections.rotate(this.joueursActifs, -2);

            this.miseHaute = this.grosseBlind;
        } else {
            this.miseHaute = 0;
        }
        System.out.println("Nb joueursActifs " + this.joueursActifs.size());

        while (!this.joueurAMiser()) {
            System.out.println("While 364");
            for (Joueur unJ : this.joueursActifs) {
                System.out.println(unJ.getNom() + " - MiseEnCours : " + unJ.getMiseEnCours() + " - Mise Haute : " + this.miseHaute);
            }
            int i = 0;
            while (i < this.joueursActifs.size()) {
                if (this.joueursActifs.size() > 1) {
                    if (this.joueursActifs.get(i).getMiseEnCours() != this.miseHaute) {
                        System.out.println("\n" + this.joueursActifs.get(i).getNom() + " " + this.joueursActifs.get(i).getMiseEnCours() + " " + this.miseHaute);
                        System.out.println("\n" + this.joueursActifs.get(i).getNom() + " que voulez-vous faire : ");
                        if ((!this.premierTour && this.miseHaute == 0) || (this.joueursActifs.get(i).getMiseEnCours() == this.miseHaute)) {
                            System.out.println("0 - Check");
                        }
                        System.out.println("1 - Suivre");
                        if (this.potTour == 0) {
                            System.out.println("2 - Miser");
                        } else {
                            System.out.println("2 - Renchérir");
                        }
                        System.out.println("3 - Se coucher");

                        Scanner sc = new Scanner(System.in);
                        int choix = sc.nextInt();

                        switch (choix) {
                            case 2:
                                if (this.potTour == 0) {
                                    this.joueurMise(i);
                                } else {
                                    this.joueurRencherit(i);
                                }

                                i++;
                                break;
                            case 0:

                                if ((!this.premierTour && this.miseHaute == 0) || (this.joueursActifs.get(i).getMiseEnCours() == this.miseHaute)) {
                                    this.joueurCheck(i);
                                    i++;
                                } else {
                                    System.out.println("Le check n'est pas possible maintenant !");
                                }
                                break;
                            case 3:
                                this.joueurSeCouche(i);
                                break;
                            case 1:
                                this.joueurSuivre(i);
                                i++;
                                break;
                            default:
                                System.out.println("Choix non pris en charge !");
                        }
                    } else {
                        i++;
                    }
                } else {
                    System.out.println("Joueur seul");
                    i = this.joueursActifs.size() + 1;
                }
            }
        }

        //Fin de tour de parole, clear variable ect
        for (Joueur unJoueur : this.joueursActifs) {
            unJoueur.clearMiseEnCours();
        }
        if (this.premierTour) {
            this.premierTour = false;
            Collections.rotate(this.joueursActifs, 2);
        }
    }

    public void joueurMise(int i) {
        System.out.println(this.joueursActifs.get(i).getNom() + ", combien misez-vous ?");
        Scanner sc = new Scanner(System.in);
        int mise = sc.nextInt();
        if (this.joueursActifs.get(i).getMiseEnCours() + mise <= this.miseHaute) {
            while (this.joueursActifs.get(i).getArgent() < mise) {
                System.out.println("Impossible de miser une somme supérieur à votre solde,"
                        + " veuillez miser quelque chose égal ou inférieur à : " + this.joueursActifs.get(i).getArgent());
                mise = sc.nextInt();
            }
            while (mise + this.joueursActifs.get(i).getMiseEnCours() < this.miseHaute) {
                System.out.println("431 - La mise minimum de ce tour est de : " + this.miseHaute);
                mise = sc.nextInt();
                if (mise > this.miseHaute) {
                    this.miseHaute = mise;
                }
            }
            if (this.joueursActifs.get(i).getMiseEnCours() + mise > this.miseHaute) {
                this.miseHaute = mise + this.joueursActifs.get(i).getMiseEnCours();
            }
            this.addPotTour(this.joueursActifs.get(i).miserArgent(mise));
            System.out.println(this.joueursActifs.get(i).getNom() + " mise : " + mise);
            System.out.println("Banque : " + this.banque + " | Mise haute : " + this.miseHaute);
            i++;
        } else if (this.joueursActifs.get(i).getMiseEnCours() + mise > this.miseHaute) {
            while (this.joueursActifs.get(i).getArgent() < mise) {
                System.out.println("Impossible de miser une somme supérieur à votre solde,"
                        + " veuillez miser quelque chose égal ou inférieur à : " + this.joueursActifs.get(i).getArgent());
                mise = sc.nextInt();
            }
            while (mise + this.joueursActifs.get(i).getMiseEnCours() < this.miseHaute) {
                System.out.println("La mise minimum de ce tour est de : " + this.miseHaute);
                mise = sc.nextInt();
            }
            if (this.joueursActifs.get(i).getMiseEnCours() + mise > this.miseHaute) {
                System.out.println("IF 469");
                this.miseHaute = mise + this.joueursActifs.get(i).getMiseEnCours();
            }
            this.addPotTour(this.joueursActifs.get(i).miserArgent(mise));
            System.out.println(this.joueursActifs.get(i).getNom() + " relance de : " + mise + " | soit : " + this.joueursActifs.get(i).getMiseEnCours());
            System.out.println("Banque : " + this.banque + " | Mise haute : " + this.miseHaute);
            i++;
        } else if (mise == 0) {
            System.out.println("Il faut miser quelque chose !");
        }
    }

    //Ajouter l'argent au pot du tour
    public void addPotTour(int argent) {
        this.potTour += argent;
    }

    //Ajouter l'argent a la banque
    public void addBanque() {
        this.banque += this.potTour;
        this.potTour = 0;
    }

    //Recupère les cartes des mains des joueurs et de la table
    public boolean recupCartes() {
        System.out.println("Carte size");
        System.out.println(this.lesCartes.size());
        System.out.println("Trash");
        System.out.println(this.trash);
        this.lesCartes.addAll(this.trash);
        System.out.println(this.lesCartes.size());
        this.lesCartes.addAll(this.cartesTable);
        System.out.println(this.lesCartes.size());
        this.trash.clear();
        this.cartesTable.clear();
        this.joueursActifs.clear();

        for (Joueur unJoueur : this.lesJoueurs) {
            this.lesCartes.addAll(unJoueur.getLaMain());
            unJoueur.clearMain();
        }
        System.out.println(this.lesCartes.size());
        return this.lesCartes.size() == 52;
    }

    //
    public ArrayList<Joueur> mainGagnante() {
        ArrayList<String> combinaison = new ArrayList<>();
        ArrayList<Joueur> joueurMainForte = new ArrayList<>();

        int i = 1;

        combinaison.add("FlushRoyal");
        combinaison.add("Flush");
        combinaison.add("Carre");
        combinaison.add("Couleur");
        combinaison.add("Suite");
        combinaison.add("Brelan");
        combinaison.add("Double");
        combinaison.add("Paire");
        combinaison.add("Hauteur");

        for (String combi : combinaison) {
            for (Joueur unJoueur : this.joueursActifs) {
                if (unJoueur.getCombinaison() == combi) {
                    joueurMainForte.add(unJoueur);
                }
            }
            if (joueurMainForte.size() >= 1) {
                if (joueurMainForte.size() == 1) {
                    return joueurMainForte;
                } else {
                    Collections.sort(joueurMainForte);
                    if (joueurMainForte.get(0).getScore() > joueurMainForte.get(1).getScore()) {
                        while (i < joueurMainForte.size()) {
                            joueurMainForte.remove(1);
                        }
                        System.out.println("JoueurMainForte 447");
                        return joueurMainForte;
                    } else {
                        System.out.println("Table.java PAS FINI CODER");
                        return this.lesJoueurs;
                    }
                }
            }
        }
        return this.lesJoueurs;
    }

    //Met le joueur en spectateur si il n'as plus d'argent
    public void spectateur() {
        int i = 0;
        while (i < this.lesJoueurs.size()) {
            if (this.lesJoueurs.get(i).getArgent() == 0) {
                if (this.lesJoueurs.get(i).getNom().equals(this.joueurBouton)) {
                    if (i + 1 == this.lesJoueurs.size()) {
                        this.joueurBouton = this.lesJoueurs.get(0).getNom();
                    } else {
                        this.joueurBouton = this.lesJoueurs.get(i + 1).getNom();
                    }
                }
                this.spectateurs.add(this.lesJoueurs.get(i));
                this.lesJoueurs.remove(i);
            } else {
                i++;
            }
        }
    }

    //Affiche la/les mains gagnantes
    public void afficherMainGagnante(ArrayList<Joueur> listeJoueurs) {
        if (listeJoueurs.size() == 1) {
            System.out.println(listeJoueurs.get(0).mainGagnanteToString());
        } else {
            System.out.println("else afficherMainGagnante");
            listeJoueurs.forEach((unJoueur) -> {
                System.out.println(unJoueur.mainGagnanteToString());
            });
        }
    }

    public void joueurCheck(int i) {
        System.out.println(this.joueursActifs.get(i).getNom() + " check !");
        this.joueursActifs.get(i).check();
    }

    public void joueurSeCouche(int i) {
        System.out.println(this.joueursActifs.get(i).getNom() + " se couche zzzZzzZzZZZZzz");
        this.joueursActifs.remove(this.joueursActifs.get(i));
    }

    public void joueurSuivre(int i) {

        this.addPotTour(this.joueursActifs.get(i).miserArgent(this.miseHaute - this.joueursActifs.get(i).getMiseEnCours()));
        if (this.joueursActifs.get(i).getMiseEnCours() == 0) {
            System.out.println(this.joueursActifs.get(i).getNom() + " suit " + (this.miseHaute - this.joueursActifs.get(i).getMiseEnCours()));
        } else {
            System.out.println(this.joueursActifs.get(i).getNom() + " suit " + this.miseHaute);
        }

        //System.out.println("Banque : " + this.banque);
    }

    public void joueurGagneBanque(ArrayList<Joueur> listeJoueur) {
        if (listeJoueur.size() == 1) {
            listeJoueur.get(0).gagneArgent(this.banque);
            this.banque = 0;
        }
    }

    public void joueurMain() {
        for (Joueur unJoueur : this.joueursActifs) {
            if (mainFlushRoyal(this.cartesTable, unJoueur)) {
            } else if (mainFlush(this.cartesTable, unJoueur)) {
            } else if (mainCarre(this.cartesTable, unJoueur)) {
            } else if (mainFull(this.cartesTable, unJoueur)) {
            } else if (mainCouleur(this.cartesTable, unJoueur)) {
            } else if (mainSuite(this.cartesTable, unJoueur)) {
            } else if (mainBrelan(this.cartesTable, unJoueur)) {
            } else if (mainPaires(this.cartesTable, unJoueur)) {
            } else if (mainHauteur(this.cartesTable, unJoueur)) {
            }
        }
    }

    //Clear les variables pour le prochain tour
    public void clearVariable() {
        for (Joueur unJ : this.lesJoueurs) {
            unJ.clearCombinaison();
            unJ.clearMiseEnCours();
            unJ.clearMainGagnante();
            unJ.clearCheck();
            unJ.clearMain();
        }
    }

    public void initVariable() {
        this.premierTour = true;
        this.miseHaute = this.grosseBlind;
    }

    public void joueurRencherit(int i) {
        this.joueurSuivre(i);
        this.joueurMise(i);
    }
}
