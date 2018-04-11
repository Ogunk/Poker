/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

/**
 *
 * @author Ogun
 */
public class Carte {
    
    private String laCouleur;
    private int numéro;
    
    //Constructeur
    public Carte(String couleur, int num)
    {
        this.laCouleur = couleur;
        this.numéro = num;
    }
    
    public Carte(){}
    
    //Création des cartes
    public void creationDesCartes(Table uneTable)
    {
        String[] lesCouleurs = new String[4];
        lesCouleurs[0] = "Trèfles";
        lesCouleurs[1] = "Coeurs";
        lesCouleurs[2] = "Carreaux";
        lesCouleurs[3] = "Piques";
        
        Carte uneCarte;
        
        for(int i=0; i<lesCouleurs.length; i++)
        {
            for(int j=1; j<=13;j++)
            {
                uneCarte = new Carte(lesCouleurs[i], j);
                uneTable.ajouterCarte(uneCarte);
            }
        }
        
        System.out.println("Le jeu des cartes est complet!");
       
    }
    
    //Retourne la carte
    @Override
    public String toString()
    {
        String res = "";
        res = res + this.laCouleur + " " + this.numéro;
        
        return res;
    }

}
