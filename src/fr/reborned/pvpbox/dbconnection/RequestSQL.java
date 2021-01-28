package fr.reborned.pvpbox.dbconnection;

import fr.reborned.pvpbox.Grade.Grades;
import fr.reborned.pvpbox.joueur.Statistiques;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestSQL {

    public Connection connect;

    public RequestSQL() {
        connect = DbConnect.getInstance();
    }

    public void findByNameAndUUID(String name, String uuid) {

        try {
            PreparedStatement statement = connect.prepareStatement("select * from Joueurs WHERE UUID = ? AND NAME = ?");
            statement.setString(1, uuid);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.first()) {
                statement = connect.prepareStatement("INSERT INTO Joueurs(UUID, NAME) VALUES (?, ?)");
                statement.setString(1, uuid);
                statement.setString(2, name);
                statement.execute();

                statement = connect.prepareStatement("INSERT INTO JoueurGrades (ID, UUID) VALUES ('1', ?)");
                statement.setString(1, uuid);
                statement.execute();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Grades> listGrade(){
        ArrayList<Grades> grades = new ArrayList<>();
        try {
            PreparedStatement statement =connect.prepareStatement("select NAME FROM Grades");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                grades.add(new Grades(resultSet.getString("NAME")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return grades;
    }

    public void register(Player player, Statistiques statistiques, String Rang) {
        try {
            PreparedStatement statement = connect.prepareStatement("Update Joueurs SET RANK = ?, DEATH = ?, RANK= ? WHERE UUID= ? AND NAME = ?");
            statement.setString(1,String.valueOf(statistiques.getTue()));
            statement.setString(2,String.valueOf(statistiques.getMort()));
            statement.setString(3,Rang);
            statement.setString(4,player.getUniqueId().toString());
            statement.setString(5,player.getName());
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public ArrayList<Statistiques> statsByNameAndUUID(String Name, String UUID){
        ArrayList<Statistiques> stat = new ArrayList<>();

        try {
            PreparedStatement statement = connect.prepareStatement("select KILLS, DEATH from Joueurs WHERE NAME = ? AND UUID = ?");
            statement.setString(1,Name);
            statement.setString(2,UUID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()){
                Statistiques statistiques = new Statistiques(resultSet.getInt("KILL"),resultSet.getInt("DEATH"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return stat;
    }
}
