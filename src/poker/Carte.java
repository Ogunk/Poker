/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Ogun & HariiBo
 */
public class Carte implements Comparable<Carte> {

    private String laCouleur;
    private int numero;
    private static ArrayList<Carte> main;

    //Constructeur
    public Carte(String couleur, int num) {
        this.laCouleur = couleur;
        this.numero = num;
    }

    public Carte() {
    }

    //Création des cartes
    public void creationDesCartes(Table uneTable) {
        String[] lesCouleurs = new String[4];
        lesCouleurs[0] = "Trèfles";
        lesCouleurs[1] = "Coeurs";
        lesCouleurs[2] = "Carreaux";
        lesCouleurs[3] = "Piques";

        Carte uneCarte;

        for (int i = 0; i < lesCouleurs.length; i++) {
            for (int j = 1; j <= 13; j++) {
                uneCarte = new Carte(lesCouleurs[i], j);
                uneTable.ajouterCarte(uneCarte);
            }
        }

        System.out.println("Le jeu des cartes est complet!");
    }

    //Retourne la carte
    @Override
    public String toString() {
        String res = "";
        res = res + getCouleur() + " " + getNumero();

        return res;
    }

    //Rtourne la couleur de la carte
    public String getCouleur() {
        return this.laCouleur;
    }

    //Rtourne la valeur de la carte
    public int getNumero() {
        return this.numero;
    }

    //Test main couleur
    public static ArrayList<Carte> mainCouleur(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        cartesTable.addAll(unJoueur.getLaMain());

        int trefle = 0;
        int coeur = 0;
        int carreau = 0;
        int pique = 0;
        String couleur = null;

        for (Carte carte : cartesTable) {
            if (carte.getCouleur().equals("Trèfles")) {
                trefle++;
            } else if (carte.getCouleur().equals("Coeurs")) {
                coeur++;
            } else if (carte.getCouleur().equals("Carreaux")) {
                carreau++;
            } else if (carte.getCouleur().equals("Piques")) {
                pique++;
            }
        }

        if (trefle >= 5) {
            couleur = "Trèfles";
        } else if (coeur >= 5) {
            couleur = "Coeurs";
        } else if (carreau >= 5) {
            couleur = "Carreaux";
        } else if (pique >= 5) {
            couleur = "Piques";
        }

        if (couleur != null) {
            for (Carte carte : cartesTable) {
                if (carte.getCouleur().equals(couleur)) {
                    Carte.main.add(carte);
                    Collections.sort(Carte.main);
                    if (Carte.main.size() > 5) {
                        if (Carte.main.size() > 6) {
                            Carte.main.remove(6);
                            Carte.main.remove(5);
                        } else {
                            Carte.main.remove(5);
                        }
                    }
                }
            }
            unJoueur.setCombinaison("Couleur");
            return Carte.main;
        } else {
            return cartesTable;
        }
    }

    //Test main quinte flush royale
    public ArrayList<Carte> mainFlushRoyal(ArrayList cartesTable, Joueur unJoueur) {
        this.main = new ArrayList<>();
        cartesTable.addAll(unJoueur.getLaMain());

        return cartesTable;
    }

    //Test main quinte flush
    public ArrayList<Carte> mainFlush(ArrayList cartesSurTable, Joueur unJoueur) {
        this.main = new ArrayList<>();
        cartesSurTable.addAll(unJoueur.getLaMain());

        return cartesSurTable;
    }

    //Test main suite
    public static ArrayList<Carte> mainSuite(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<Carte>();
        cartesTable.addAll(unJoueur.getLaMain());

        int x = 0;
        int i = 0;
        int triple = 0;
        int duo = 0;

        if (cartesTable.size() > 5) {
            //Si il y a un carré dans les 7 cartes, suite pas possible
            for (i = 0; i < 4; i++) {
                if (cartesTable.get(i).getNumero() == cartesTable.get(i + 3).getNumero()) {
                    return cartesTable;
                }
            }
            //Détection des triples et doubles dans la liste de
            for (i = 0; i < 6; i++) {
                if (cartesTable.get(i).getNumero() == cartesTable.get(i + 2).getNumero()) {
                    triple++;
                } else if (cartesTable.get(i).getNumero() == cartesTable.get(i + 1).getNumero()) {
                    duo++;
                }
            }
            if ((duo == 3 || triple == 2) || (duo == 2 && triple == 1) || (duo == 1 && triple == 1)) {
                return cartesTable;
            }

            //Retirer les doubles ou triples
            while (x < 5) {
                if (cartesTable.get(x).getNumero() == cartesTable.get(x + 1).getNumero()) {
                    cartesTable.remove(x);
                } else {
                    Carte.main.add(cartesTable.get(x));
                    x++;
                }
            }
            Collections.sort(cartesTable);

            if (cartesTable.size() >= 5) {
                Carte.main.addAll(cartesTable);
                i = 0;
                while (i < Carte.main.size() - 1) {
                    if (Carte.main.get(i).getNumero() == Carte.main.get(i + 1).getNumero() + 1) {
                        if (Carte.main.size() < 5) {
                            return cartesTable;
                        }
                    } else {
                        Carte.main.remove(i);
                        i++;
                    }
                }
                if (Carte.main.size() == 5) {
                    return Carte.main;
                } else if (Carte.main.size() > 5) {

                }

            }

        } else if (cartesTable.size() == 5) {
            for (i = 0; i < 4; i++) {
                if (cartesTable.get(i).getNumero() != cartesTable.get(i + 1).getNumero() + 1) {
                    return cartesTable;
                }
            }
            return Carte.main;
        }
        return cartesTable;
    }

//Fonction pour trier les cartes, utilisé par mainSuite
    @Override
    public int compareTo(Carte carte) {

        int compareNumero = ((Carte) carte).getNumero();

        //ascending order
        //return this.numero - compareNumero;
        //descending order
        return compareNumero - this.numero;
    }
}
