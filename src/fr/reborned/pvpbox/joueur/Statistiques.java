package fr.reborned.pvpbox.joueur;

import java.util.ArrayList;

public class Statistiques {
    private int tue;
    private int mort;

    private ArrayList<Statistiques> stats;

    public Statistiques(int tue, int mort){
        this.tue = tue;
        this.mort =mort;
        stats = new ArrayList<>();
    }
    public float getRatio(){
        return (float) this.tue/this.mort;
    }

    public int getTue() {
        return tue;
    }

    public void setTue(int tue) {
        this.tue = tue;
    }

    public int getMort() {
        return mort;
    }

    public void setMort(int mort) {
        this.mort = mort;
    }
}
