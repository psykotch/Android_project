package fr.epita.androidproject;

public class ia {
    static String name;
    int energy;
    int life;
    int dicenumber;
    int victoryPoint;
    private int numberOfThrow = 3;
    boolean isking;
    //private boolean mini;
    //private boolean poison;
    //private boolean fumé;


    public ia() {
        this.name = "unnames";
        this.energy = 0;
        this.life = 10;
        this.dicenumber = 6;
        this.victoryPoint = 0;
        this.isking = false;
        //this.mini = false;
        //this.poison = false;
        //this.fumé = false;
    }
    public ia(String name) {
        this.name = name;
        this.energy = 0;
        this.life = 10;
        this.dicenumber = 6;
        this.victoryPoint = 0;
        this.isking =false;
        //this.mini = false;
        //this.poison = false;
        //this.fumé = false;
    }
    /*methode pour lancer les dé
        tant que lancé disponible
        verifier carte possédé
        tant que dicenumber est > 0
        creer un dé
        lancer le dé
        relancé
    */
    //methode pour lancé un dé
    //methode pour acheter des cartes
}