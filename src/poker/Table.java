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
    }

    //Initialise le jeu
    public void initTable() {
        //Début d'une partie
        this.setArgentJoueurs();
        this.melangerLesJoueurs();
        this.joueurRole();

        while (this.lesJoueurs.size() > 1) {
            this.jouer();

        }

        System.out.println("Voici toutes les cartes sur la table: ");

        for (Carte uneCarte : this.cartesTable) {
            System.out.println(uneCarte.toString());
        }

        System.out.println("\n");

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
        Collections.rotate(this.lesJoueurs, -1);
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
    public boolean joueurMise() {
        int nbJPret = 0;
        boolean pret = false;

        if (this.miseHaute != 0) {
            for (Joueur unJoueur : this.joueursActifs) {
                if (unJoueur.getMiseEnCours() == this.miseHaute) {
                    nbJPret++;
                }
            }
        }
        if (nbJPret == this.joueursActifs.size()) {
            pret = true;
        }
        return pret;
    }

    //On vérifie si le tour continu (+1 joueur qui joue)
    public boolean tourContinu(ArrayList<Joueur> listeJoueurs) {
        boolean tour = true;

        if (listeJoueurs.size() < 2) {
            tour = false;
        }
        return tour;
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
        for (Carte uneCarte : this.cartesTable) {
            System.out.println(uneCarte.toString());
        }

    }

    //Affiche la 4eme carte sur la table, on brule la 1ere carte
    public void afficherTurn() {
        this.trash.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);

        this.cartesTable.add(this.lesCartes.get(0));
        this.lesCartes.remove(0);
    }

    //Affichage de la derniere carte, on brule la 1ere carte
    public void afficherRiver() {
        this.trash.add(this.lesCartes.get(0));
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
        //Mélange des cartes
        this.melangerLesCartes();

        //Distribution des cartes
        this.distribuerCartes();

        //TEST - A supprimer
        for (Joueur unJoueur : this.lesJoueurs) {
            System.out.println(unJoueur.joueurToString());
            System.out.println(unJoueur.mainToString());
        }

        //Pose du flop sur la table, face cachée
        this.poserFlop();
        System.out.println("## Mise premier tour");
        //Première mise avant l'affichage du flop
        this.tourParole();

        //Affichage du flop
        //this.afficherFlop();
        System.out.println("## Mise flop");

        //Nouveau tour de parole avant l'affichage du turn
        this.tourParole();

        //Affichage du turn
        this.afficherTurn();
        System.out.println("## Mise turn");
        //Nouveau tour de mise avant l'affichage du turn
        this.tourParole();

        //Affichage de la River
        this.afficherRiver();
        System.out.println("## Mise River");
        //Dernier tour de mise
        this.tourParole();

        //TEST - A supprimer
        System.out.println("Voici toutes les cartes sur la table: ");
        for (Carte uneCarte : this.cartesTable) {
            System.out.println(uneCarte.toString());
        }
        System.out.println("Test 1");
        //Election main Gagnante et affichage main gagnante
        this.afficherMainGagnante(this.mainGagnante());
        System.out.println("Test 2");
        //Gagnant récupère la banque
        //this.joueurGagneBanque();
        //
        //Récupère les cartes
        this.recupCartes();
        System.out.println("################################## Test 3");
        //Passe les joueurs sans argents en spectateur
        this.spectateur();
        System.out.println("################################## Test 4");
        //Rotation du bouton/ PB / GB
        this.rotationRole();
        System.out.println("################################## Test 5");
        //Augmentation des blinds
        this.augmentationBlind();
        System.out.println("################################## Test 6");
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
            this.addBanque(this.joueursActifs.get(0).miserArgent(this.petiteBlind));
            this.addBanque(this.joueursActifs.get(1).miserArgent(this.grosseBlind));

            Collections.rotate(this.joueursActifs, -2);

            this.miseHaute = this.grosseBlind;
        } else {
            this.miseHaute = 0;
        }
        System.out.println("329 " + this.joueursActifs.size());
        if (this.joueursActifs.size() > 1) {
            while (!this.joueurMise()) {
                int i = 0;
                while (i < this.joueursActifs.size()) {
                    System.out.println("\n" + this.joueursActifs.get(i).getNom());
                    System.out.println("Que voulez-vous faire : ");
                    if (!this.premierTour && this.miseHaute == 0) {
                        System.out.println("0 - Check");
                    }
                    System.out.println("1 - Suivre");
                    System.out.println("2 - Renchérir");
                    System.out.println("3 - Se coucher");

                    Scanner sc = new Scanner(System.in);
                    int choix = sc.nextInt();

                    switch (choix) {
                        case 2:
                            this.tourDeMise(i);
                            i++;
                            break;
                        case 0:
                            System.out.println("Check mais pas codé");
                            if (!this.premierTour) {
                                this.check(i);
                                i++;
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

    public void tourDeMise(int i) {
        System.out.println(this.joueursActifs.get(i).getNom() + ", combien misez-vous ?");
        Scanner sc = new Scanner(System.in);
        int mise = sc.nextInt();
        if (this.joueursActifs.get(i).getMiseEnCours() + mise <= this.miseHaute && mise != -1) {
            while (this.joueursActifs.get(i).getArgent() < mise) {
                System.out.println("Impossible de miser une somme supérieur à votre solde,"
                        + " veuillez miser quelque chose égal ou inférieur à : " + this.joueursActifs.get(i).getArgent());
                mise = sc.nextInt();
            }
            while (mise + this.joueursActifs.get(i).getMiseEnCours() < this.miseHaute) {
                System.out.println("La mise minimum de ce tour est de : " + this.miseHaute);
                mise = sc.nextInt();
                if (mise > this.miseHaute) {
                    this.miseHaute = mise;
                }
            }
            if (this.joueursActifs.get(i).getMiseEnCours() + mise > this.miseHaute) {
                this.miseHaute = mise + this.joueursActifs.get(i).getMiseEnCours();
            }
            this.addBanque(this.joueursActifs.get(i).miserArgent(mise));
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
                if (mise > this.miseHaute) {
                    this.miseHaute = mise;
                }
            }
            if (this.joueursActifs.get(i).getMiseEnCours() + mise > this.miseHaute) {
                this.miseHaute = mise;
            }
            this.addBanque(this.joueursActifs.get(i).miserArgent(mise));
            System.out.println(this.joueursActifs.get(i).getNom() + " relance de : " + mise + " | soit : " + this.joueursActifs.get(i).getMiseEnCours());
            System.out.println("Banque : " + this.banque + " | Mise haute : " + this.miseHaute);
            i++;
        } else if (mise == 0) {
            System.out.println("Il faut miser quelque chose !");
        }
    }

    //Ajouter l'argent a la banque
    public void addBanque(int argent) {
        this.banque += argent;
    }

    //Recupère les cartes des mains des joueurs et de la table
    public boolean recupCartes() {
        this.lesCartes.addAll(this.trash);
        this.lesCartes.addAll(this.cartesTable);
        this.trash.clear();
        this.cartesTable.clear();

        for (Joueur unJoueur : this.lesJoueurs) {
            this.lesCartes.addAll(unJoueur.getLaMain());
            unJoueur.clearMain();
        }
        return this.cartesTable.size() == 52;
    }

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
            for (Joueur unJoueur : this.lesJoueurs) {
                if (!unJoueur.getCoucher()) {
                    if (unJoueur.getCombinaison() == combi) {
                        joueurMainForte.add(unJoueur);
                    }
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

    public void check(int i) {
        System.out.println(this.joueursActifs.get(i).getNom());
    }

    public void joueurSeCouche(int i) {
        this.joueursActifs.get(i).seCoucher();
        this.joueursActifs.remove(this.joueursActifs.get(i));
    }

    public void joueurSuivre(int i) {

        this.addBanque(this.joueursActifs.get(i).miserArgent(this.miseHaute - this.joueursActifs.get(i).getMiseEnCours()));
        System.out.println(this.joueursActifs.get(i).getNom() + " suit " + this.joueursActifs.get(i).getMiseEnCours());
        System.out.println("Banque : " + this.banque);
    }

}
