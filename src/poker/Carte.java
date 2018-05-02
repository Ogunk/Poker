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
    private String nom;
    private int numero;

    private static int i;
    private static int duo;
    private static int triple;
    private static ArrayList<Carte> main;
    private static ArrayList<Carte> jeu;

    //Constructeur
    public Carte(String couleur, int num, String leNom) {
        this.laCouleur = couleur;
        this.numero = num;
        this.nom = leNom;
    }

    public Carte() {
    }

    //Création des cartes
    public void creationDesCartes(Table uneTable) {
        String[] lesCouleurs = {"Trèfles", "Coeurs", "Carreaux", "Piques"};

        Carte uneCarte;

        for (String lesCouleur : lesCouleurs) {
            for (int j = 1; j <= 13; j++) {
                switch (j) {
                    case 10:
                        uneCarte = new Carte(lesCouleur, j, "Valet");
                        break;
                    case 11:
                        uneCarte = new Carte(lesCouleur, j, "Dame");
                        break;
                    case 12:
                        uneCarte = new Carte(lesCouleur, j, "Roi");
                        break;
                    case 13:
                        uneCarte = new Carte(lesCouleur, j, "As");
                        break;
                    default:
                        uneCarte = new Carte(lesCouleur, j, String.valueOf(j + 1));
                        break;
                }
                uneTable.ajouterCarte(uneCarte);
            }
        }

        System.out.println("Le jeu des cartes est complet!");
    }

    //Retourne la carte
    @Override
    public String toString() {
        String res = "";
        res = this.getNom() + " " + this.getCouleur();

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

    //Rtourne le nom de la carte
    public String getNom() {
        return this.nom;
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
                unJoueur.setScore(Carte.main.get(0).getNumero());
                unJoueur.setCombinaison("Couleur");
                unJoueur.setMainGagnante(Carte.main);
                return true;
            } else if (Carte.main.size() == 5) {
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
    public static boolean mainFlushRoyal(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();

        if (mainFlush(cartesTable, unJoueur)) {
            Carte.main.addAll(unJoueur.getMainGagnante());

            Collections.sort(Carte.main);

            if (Carte.main.get(0).getNumero() == 13) {
                System.out.println("///////////////////////////////////QUINTE FLUSH ROYAL");
                System.out.println(Carte.main);

                unJoueur.setCombinaison("FlushRoyal");
                return true;
            }
        }

        return false;
    }

    //Test main quinte flush
    public static boolean mainFlush(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();

        if (mainCouleur(cartesTable, unJoueur)) {
            Carte.main.addAll(unJoueur.getMainGagnante());
            Collections.sort(Carte.main);
            unJoueur.clearScore();
            unJoueur.clearCombinaison();
            unJoueur.clearMainGagnante();

            if (mainSuite(Carte.main, unJoueur)) {
                System.out.println("mainFlush QUINTE FLUSH");
                unJoueur.setCombinaison("Flush");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Test main suite
    public static boolean mainSuite(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        Carte.jeu = new ArrayList<>();

        Carte.jeu.addAll(unJoueur.getLaMain());
        Carte.jeu.addAll(cartesTable);
        Collections.sort(Carte.jeu);

        triple = 0;
        duo = 0;

        if (Carte.jeu.size() > 5) {
            //Si il y a un carré dans les 7 cartes, suite pas possible
            for (i = 0; i < 4; i++) {
                if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 3).getNumero()) {
                    return false;
                }
            }

            //Détection des triples et doubles dans la liste de
            for (i = 0; i < 6; i++) {
                if (i < 5) {
                    if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 2).getNumero()) {
                        triple++;
                        i = i + 2;
                    }
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
        i = 0;
        while (i < Carte.jeu.size() - 1) {
            if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 1).getNumero()) {
                Carte.jeu.remove(i);
            } else {
                i++;
            }
        }

        Collections.sort(Carte.jeu);

        if (Carte.jeu.size() > 5) {
            i = 0;
            while (i < Carte.jeu.size() - 1) {
                Carte.main.add(Carte.jeu.get(i));
                if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 1).getNumero() + 1) {
                } else {
                    if (Carte.main.size() == 5) {
                        unJoueur.setScore(Carte.main.get(0).getNumero());
                        unJoueur.setCombinaison("Suite");
                        unJoueur.setMainGagnante(Carte.main);
                        return true;
                    } else if (Carte.main.size() > 3) {
                        return false;
                    } else {
                        Carte.main.clear();
                    }
                }
                i++;
            }
            if (!Carte.main.isEmpty()) {
                if (Carte.main.get(Carte.main.size() - 1).getNumero() == (Carte.jeu.get(i).getNumero() + 1)) {
                    Carte.main.add(Carte.jeu.get(i));
                }
            }

            if (Carte.main.size() < 5) {
                return false;
            } else if (Carte.main.size() == 5) {
                unJoueur.setScore(Carte.main.get(0).getNumero());
                unJoueur.setCombinaison("Suite");
                unJoueur.setMainGagnante(Carte.main);
                //System.out.println("SUITE 4.1");
                return true;
            } else if (Carte.main.size() == 7) {
                Carte.main.remove(6);
                Carte.main.remove(5);

                unJoueur.setScore(Carte.main.get(0).getNumero());
                unJoueur.setCombinaison("Suite");
                unJoueur.setMainGagnante(Carte.main);
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

    //Test d'une main avec un carré
    public static boolean mainCarre(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        Carte.jeu = new ArrayList<>();

        Carte.jeu.addAll(unJoueur.getLaMain());
        Carte.jeu.addAll(cartesTable);
        Collections.sort(Carte.jeu);

        for (i = 0; i < 4; i++) {
            if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 3).getNumero()) {
                Carte.main.add(Carte.jeu.get(i));
                Carte.main.add(Carte.jeu.get(i + 1));
                Carte.main.add(Carte.jeu.get(i + 2));
                Carte.main.add(Carte.jeu.get(i + 3));

                Carte.jeu.remove(i);
                Carte.jeu.remove(i);
                Carte.jeu.remove(i);
                Carte.jeu.remove(i);

                Collections.sort(Carte.jeu);

                Carte.main.add(Carte.jeu.get(0));
                unJoueur.setCombinaison("Carre");
                unJoueur.setMainGagnante(Carte.main);
                unJoueur.setScore(Carte.main.get(0).getNumero());
                return true;
            }
        }

        return false;
    }

    //Test d'une main avec un carré
    public static boolean mainFull(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        Carte.jeu = new ArrayList<>();

        duo = 0;
        triple = 0;

        Carte.jeu.addAll(unJoueur.getLaMain());
        Carte.jeu.addAll(cartesTable);
        Collections.sort(Carte.jeu);

        //Détection des triples et doubles dans la liste de
        for (i = 0; i < 6; i++) {
            if (i < 5) {
                if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 2).getNumero()) {
                    Carte.main.add(Carte.jeu.get(i));
                    Carte.main.add(Carte.jeu.get(i + 1));
                    Carte.main.add(Carte.jeu.get(i + 2));
                    unJoueur.setScore(Carte.main.get(0).getNumero());
                    triple++;
                    i = i + 2;
                    if (duo == 1) {

                    }
                }
            } else if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 1).getNumero()) {
                Carte.main.add(Carte.jeu.get(i));
                Carte.main.add(Carte.jeu.get(i + 1));
                duo++;
                if (triple == 1) {
                    break;
                }
            }
        }

        if (duo == 1 && triple == 1) {
            Collections.sort(Carte.main);
            unJoueur.setCombinaison("Full");
            unJoueur.setMainGagnante(Carte.main);
            unJoueur.setScore(Carte.main.get(0).getNumero());
            return true;
        }
        unJoueur.clearScore();
        return false;
    }

    //Test main brelan
    public static boolean mainBrelan(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        Carte.jeu = new ArrayList<>();

        Carte.jeu.addAll(cartesTable);
        Carte.jeu.addAll(unJoueur.getLaMain());

        Collections.sort(Carte.jeu);

        //Détection des triples et doubles dans la liste de
        for (i = 0; i < 5; i++) {
            if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 2).getNumero()) {
                Carte.main.add(Carte.jeu.get(i));
                Carte.main.add(Carte.jeu.get(i + 1));
                Carte.main.add(Carte.jeu.get(i + 2));

                Carte.jeu.remove(i);
                Carte.jeu.remove(i);
                Carte.jeu.remove(i);

                Collections.sort(Carte.jeu);

                Carte.main.add(Carte.jeu.get(0));
                Carte.main.add(Carte.jeu.get(1));

                unJoueur.setCombinaison("Brelan");
                unJoueur.setMainGagnante(Carte.main);
                unJoueur.setScore(Carte.main.get(0).getNumero());
                return true;
            }
        }

        return false;
    }

    //Test main paire, double paires
    public static boolean mainPaires(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        Carte.jeu = new ArrayList<>();

        duo = 0;

        Carte.jeu.addAll(cartesTable);
        Carte.jeu.addAll(unJoueur.getLaMain());

        Collections.sort(Carte.jeu);

        //Détection des triples et doubles dans la liste de
        for (i = 0; i < 6; i++) {
            if (Carte.jeu.get(i).getNumero() == Carte.jeu.get(i + 1).getNumero()) {
                duo++;
                if (Carte.main.size() == 4) {
                    break;
                }
                Carte.main.add(Carte.jeu.get(i));
                Carte.main.add(Carte.jeu.get(i + 1));
                i++;
            }
        }

        if (Carte.main.size() != 0) {
            Collections.sort(Carte.main);
            unJoueur.setScore(Carte.main.get(0).getNumero());
            for (Carte uneCarte : Carte.jeu) {
                if (duo == 2) {
                    if (Carte.main.get(0).getNumero() != uneCarte.getNumero()
                            && Carte.main.get(2).getNumero() != uneCarte.getNumero()) {
                        Carte.main.add(uneCarte);
                        unJoueur.setCombinaison("Double");
                        break;
                    }
                } else if (duo == 1) {
                    if (Carte.main.get(0).getNumero() != uneCarte.getNumero()) {
                        Carte.main.add(uneCarte);
                        if (Carte.main.size() == 5) {
                            unJoueur.setCombinaison("Paire");
                            break;
                        }
                    }
                }
            }
            unJoueur.setMainGagnante(Carte.main);
            return true;
        }
        unJoueur.clearScore();
        return false;
    }

    //Test hauteur
    public static boolean mainHauteur(ArrayList<Carte> cartesTable, Joueur unJoueur) {
        Carte.main = new ArrayList<>();
        Carte.main.addAll(cartesTable);
        Carte.main.addAll(unJoueur.getLaMain());

        Collections.sort(Carte.main);

        unJoueur.setMainGagnante(Carte.main);
        unJoueur.setCombinaison("Hauteur");
        unJoueur.setScore(Carte.main.get(0).getNumero());

        return true;
    }

    //Fonction pour trier les cartes, de la plus forte 13 à la plus faible 1
    @Override
    public int compareTo(Carte carte) {

        int compareNumero = ((Carte) carte).getNumero();

        //ascending order
        //return this.numero - compareNumero;
        //descending order
        return compareNumero - this.numero;
    }
}
