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
        this.tourDeMiseBlind();

        //Affichage du flop
        //this.afficherFlop();
        System.out.println("## Mise flop");
        //Nouveau tour de mise avant l'affichage du turn
        this.tourDeMise();

        //Affichage du turn
        this.afficherTurn();
        System.out.println("## Mise turn");
        //Nouveau tour de mise avant l'affichage du turn
        this.tourDeMise();

        //Affichage de la River
        this.afficherRiver();
        System.out.println("## Mise River");
        //Dernier tour de mise
        this.tourDeMise();

        //TEST - A supprimer
        System.out.println("Voici toutes les cartes sur la table: ");
        for (Carte uneCarte : this.cartesTable) {
            System.out.println(uneCarte.toString());
        }

        //Election main Gagnante et affichage main gagnante
        this.afficherMainGagnante(this.mainGagnante());

        //Gagnant récupère la banque
        //this.joueurGagneBanque();
        //
        //Récupère les cartes
        this.recupCartes();

        //Passe les joueurs sans argents en spectateur
        this.spectateur();

        //Rotation du bouton/ PB / GB
        this.rotationRole();

        //Augmentation des blinds
        this.augmentationBlind();
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

    //Parti pari entre chaque tour
    public void tourDeMise() {
        this.joueursActifs.addAll(this.lesJoueurs);

        this.miseHaute = 0;

        for (Joueur unJoueur : this.joueursActifs) {
            System.out.println(unJoueur.getNom() + " Combien misez-vous ? \n");
            Scanner sc = new Scanner(System.in);
            int mise = sc.nextInt();
            if (mise == 0) {
                unJoueur.seCoucher();
                this.joueursActifs.remove(unJoueur);
            } else {
                while (unJoueur.getArgent() < mise) {
                    System.out.println("Impossible de miser une somme supérieur à votre solde,"
                            + " veuillez miser quelque chose égal ou inférieur à : " + unJoueur.getArgent());
                    mise = sc.nextInt();
                }
                while (mise < this.miseHaute) {
                    System.out.println("La mise minimum de ce tour est de : " + this.miseHaute);
                    mise = sc.nextInt();
                }
                this.addBanque(unJoueur.miserArgent(mise));
                System.out.println(unJoueur.getNom() + " mise : " + mise);
                System.out.println("Banque : " + this.banque);
            }
        }
        this.joueursActifs.clear();
    }

    //Ajouter l'argent a la banque
    public void addBanque(int argent) {
        this.banque += argent;
        System.out.println("La banque augmente : " + this.banque + " (+" + argent + ")");
    }

    //Parti pari entre chaque tour
    public void tourDeMiseBlind() {
        this.joueursActifs.addAll(this.lesJoueurs);

        //Mise obligatoire PB GB
        this.addBanque(this.joueursActifs.get(0).miserArgent(this.petiteBlind));
        this.addBanque(this.joueursActifs.get(1).miserArgent(this.grosseBlind));

        Collections.rotate(this.joueursActifs, -2);

        this.miseHaute = this.grosseBlind;

        for (Joueur unJoueur : this.joueursActifs) {
            System.out.println(unJoueur.getNom() + " Combien misez-vous ? \n");
            Scanner sc = new Scanner(System.in);
            int mise = sc.nextInt();
            if (mise == 0) {
                unJoueur.seCoucher();
                this.joueursActifs.remove(unJoueur);
            } else {
                while (unJoueur.getArgent() < mise) {
                    System.out.println("Impossible de miser une somme supérieur à votre solde,"
                            + " veuillez miser quelque chose égal ou inférieur à : " + unJoueur.getArgent());
                    mise = sc.nextInt();
                }
                while (mise < this.miseHaute) {
                    System.out.println("La mise minimum de ce tour est de : " + this.miseHaute);
                    mise = sc.nextInt();
                }
                this.addBanque(unJoueur.miserArgent(mise));
                System.out.println(unJoueur.getNom() + " mise : " + mise);
                System.out.println("Banque : " + this.banque);
            }
        }
        this.joueursActifs.clear();
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
            for (Joueur unJoueur : listeJoueurs) {
                System.out.println(unJoueur.mainGagnanteToString());
            }
        }
    }

}
