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
    private static ArrayList<Carte> jeu;

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
    public static boolean mainCouleur(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        Carte.jeu = new ArrayList<>();

        Carte.jeu.addAll(unJoueur.getLaMain());
        Carte.jeu.addAll(cartesTable);

        int trefle = 0;
        int coeur = 0;
        int carreau = 0;
        int pique = 0;

        String couleur = null;

        for (Carte carte : Carte.jeu) {
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
            for (Carte carte : Carte.jeu) {
                if (carte.getCouleur().equals(couleur)) {

                    Carte.main.add(carte);
                    Collections.sort(Carte.main);
                }
            }
            if (Carte.main.size() > 5) {
                if (Carte.main.size() > 6) {
                    Carte.main.remove(6);
                    Carte.main.remove(5);
                } else {
                    Carte.main.remove(5);
                }
            }
            if (Carte.main.size() == 5) {
                unJoueur.setScore(Carte.main.get(0).getNumero());
                unJoueur.setCombinaison("Couleur");
                unJoueur.setMainGagnante(Carte.main);
                return true;
            }

            return false;
        } else {
            return false;
        }
    }

    //Test main quinte flush royale
    public boolean mainFlushRoyal(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();

        cartesTable.addAll(unJoueur.getLaMain());

        return false;
    }

    //Test main quinte flush
    public boolean mainFlush(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();

        cartesTable.addAll(unJoueur.getLaMain());

        return false;
    }

    //Test main suite
    public static boolean mainSuite(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        Carte.jeu = new ArrayList<>();

        Carte.jeu.addAll(unJoueur.getLaMain());
        Carte.jeu.addAll(cartesTable);
        Collections.sort(Carte.jeu);

        int x = 0;
        int i = 0;
        int triple = 0;
        int duo = 0;

        if (Carte.jeu.size() > 5) {
            //Si il y a un carré dans les 7 cartes, suite pas possible
            for (i = 0; i < 4; i++) {
                if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 3).getNumero()) {
                    return false;
                }
            }

            //Détection des triples et doubles dans la liste de
            for (i = 0; i < 5; i++) {
                if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 2).getNumero()) {
                    triple++;
                    i = i + 2;

                } else if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 1).getNumero()) {
                    duo++;
                }
            }

            if (duo == 3 || triple == 2) {
                return false;
            } else if (duo == 1 && triple == 1) {
                return false;
            }
        }

        //Retirer les doubles ou triples
        while (x < Carte.jeu.size() - 1) {
            if (Carte.jeu.get(x).getNumero() == Carte.jeu.get(x + 1).getNumero()) {
                Carte.jeu.remove(x);
            } else {
                x++;
            }
        }

        Collections.sort(Carte.jeu);

        if (Carte.jeu.size() > 5) {
            i = 0;
            while (i < Carte.jeu.size() - 1) {
                Carte.main.add(Carte.jeu.get(i));
                //System.out.println("Ajouter carte main " + Carte.jeu.get(i));
                if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 1).getNumero() + 1) {
                } else {
                    if (Carte.main.size() == 5) {
                        unJoueur.setScore(Carte.main.get(0).getNumero());
                        unJoueur.setCombinaison("Suite");
                        unJoueur.setMainGagnante(Carte.main);
                        //System.out.println("SUITE 3.1");
                        return true;
                    } else if (Carte.main.size() > 3) {
                        //System.out.println("False main petite 3");
                        return false;
                    } else {
                        //System.out.println("Clear main");
                        Carte.main.clear();
                        //System.out.print(Carte.main);
                    }
                }
                i++;
            }
            //System.out.println("229 - " + Carte.main);
            //System.out.println("229 - " + Carte.jeu);
            if (!Carte.main.isEmpty()) {
                //System.out.println("Empty");
                if (Carte.main.get(Carte.main.size() - 1).getNumero() == (Carte.jeu.get(i).getNumero() + 1)) {
                    Carte.main.add(Carte.jeu.get(i));
                }
            }

            //System.out.println("239 - " + Carte.main);
            if (Carte.main.size() < 5) {
                //System.out.println("False main petite");
                return false;
            } else if (Carte.main.size() == 5) {
                unJoueur.setScore(Carte.main.get(0).getNumero());
                unJoueur.setCombinaison("Suite");
                unJoueur.setMainGagnante(Carte.main);
                //System.out.println("SUITE 4.1");
                return true;
            }

            System.out.println("252 - PAS FINI CODER");

        } else if (Carte.jeu.size() == 5) {
            for (i = 0; i < 4; i++) {
                if (Carte.jeu.get(i).getNumero() != Carte.jeu.get(i + 1).getNumero() + 1) {
                    //System.out.println("Return 5");
                    return false;
                }
            }
            unJoueur.setScore(Carte.jeu.get(0).getNumero());
            unJoueur.setCombinaison("Suite");
            unJoueur.setMainGagnante(Carte.jeu);
            //System.out.println("\n*** SUITE 1 : " + Carte.jeu);
            return true;

        } else {
            //System.out.println("Return 6");
            return false;
        }
        return false;
    }

    //Test main paire, double paires, brelan, full et carré
    public static boolean mainPaires(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();

        int i;
        int triple = 0;
        int duo = 0;

        cartesTable.addAll(unJoueur.getLaMain());

        for (i = 0; i < 4; i++) {
            if (cartesTable.get(i).getNumero() == cartesTable.get(i + 3).getNumero()) {
                System.out.println("Return Carré");
                Carte.main.add(cartesTable.get(i));
                Carte.main.add(cartesTable.get(i + 1));
                Carte.main.add(cartesTable.get(i + 2));
                Carte.main.add(cartesTable.get(i + 3));

                unJoueur.setMainGagnante(Carte.main);
                return false;
            }
        }

        for (i = 0; i < 5; i++) {
            if (cartesTable.get(i).getNumero() == cartesTable.get(i + 2).getNumero()) {
                triple++;
            } else if (cartesTable.get(i).getNumero() == cartesTable.get(i + 1).getNumero()) {
                duo++;
            }
        }

        if ((duo == 3 || triple == 2) || (duo == 2 && triple == 1) || (duo == 1 && triple == 1)) {
            System.out.println("Return 2");
            return false;
        }

        return false;
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
