package fr.reborned.pvpbox.Grade;

import fr.reborned.pvpbox.dbconnection.Connected;

import java.util.ArrayList;

public class Grades {
    private ArrayList<Grades> grades;
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Grades(String Name){
        this.Name=Name;
        this.grades = new ArrayList<>();
        remplissageArray();
    }

    private void remplissageArray(){
        Connected connected = new Connected();
        ArrayList<Grades> grades1 = connected.listGrade();
        if (!grades1.isEmpty()){
            this.grades.addAll(grades1);
        }
        for (Grades grade : grades) {
            System.out.println(grade.getName());
        }
    }

}
