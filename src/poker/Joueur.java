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
    private ArrayList<Carte> laMain;
    private int argent;
    private boolean bouton;
    
    public static final int MAIN = 2;
    
    //Constructeur
    public Joueur(String n, int argent)
    {
        this.laMain =  new ArrayList<>();
        this.nom = n;
        this.argent = argent;
        this.bouton = false;
    }
    
    //Le joueur reçoit ses cartes
    public void setLaMain(Carte uneCarte)
    {
        if(this.laMain.size() < MAIN)
        {
            this.laMain.add(uneCarte);
        }
        else
        {
            System.out.print("Main déjà distribué !");
        }
    }
    
    //Affecte le bouton à un joueur
    public void setLeBouton(boolean b)
    {
        this.bouton = b;
    }
    
    //Retourne la main
    public ArrayList getLaMain()
    {
        return this.laMain;
    }
    
    //Retourne les cartes en main
    public String mainToString()
    {
        String res="";
        
        for(int i=0; i<this.laMain.size(); i++)
        {
            res = res + "Carte numéro " + (i+1) + " : " + this.laMain.get(i).toString()+ "\n";
        }
        
        return res;
    }
    
    //Retourne le solde actuel du joueurs
    public int getArgent()
    {
        return this.argent;
    }
    
    //Retourne le détail du joueur
    public String joueurToString()
    {
        String res="";
        res = res + "Nom : " + this.nom + " | Solde : " + this.argent;
        
        return res;
    }
    
    //Miser son argent
    public void miserArgent()
    {
        
    }
    
    //Se coucher
    public void seCoucher()
    {
        
    }
    
    //Suivre 
    public void suivre()
    {
        
    }
    
}
