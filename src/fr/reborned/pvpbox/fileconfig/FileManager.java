package fr.reborned.pvpbox.fileconfig;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager extends File {

    public FileManager(File file, String chemin) {
        super(file, chemin);
    }

    public File getFile() {
        return super.getAbsoluteFile();
    }

    public void ajoutFichierVide() {
        try {
            super.getParentFile().mkdirs();
            if (super.createNewFile()) {
                System.out.println("Fichier config vient d'être créer");
                if (fichierVide(super.getAbsoluteFile())) {
                    remplissageFile(super.getAbsoluteFile());
                }
            } else {
                System.out.println("Fichier de configuration déjà existant");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void remplissageFileDb(File file){
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final String key="Connection.";
        configuration.set(key+"url", "jdbc:mysql://IPMYSQL/DataBASE");
        configuration.set(key+"user", "utilisateur DB");
        configuration.set(key+"passwd", "Password Db");


        saveConfig(configuration);
    }

    private boolean fichierVide(File file) {
        boolean ret = false;
        if (file != null) {
            if (file.length() == 0) {
                ret = true;
            }
        }

        return ret;
    }


    private void remplissageFile(File file) {
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final String key = "players.";
        final ArrayList<String> Liste = listeDeConfig();

        for (String element : Liste){
            configuration.set(key+element, " ");
        }
        for (int i=0; i< 10;i++){
            configuration.set(key+".item"+i," ");
        }

        saveConfig(configuration);

    }

    private ArrayList<String> listeDeConfig(){
        ListType[] listTypeArrayList = ListType.values();
        ListArmor[] listArmor = ListArmor.values();

        ArrayList<String> FullKey = new ArrayList<>();
        for (ListArmor armor : listArmor) {
            for (ListType listType : listTypeArrayList) {
                FullKey.add(armor.toString() + "." + listType.toString());
            }
        }

        return  FullKey;

    }


    public void saveInFile(String key, Location o){
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(getFile());

        configuration.set("Locations."+ key  +"x", o.getBlockX());
        configuration.set("Locations."+ key  +"y", o.getBlockY());
        configuration.set("Locations."+ key  +"z", o.getBlockZ());
        configuration.set("Locations."+ key  +"world",o.getWorld().getName());

        saveConfig(configuration);
    }

    public void saveConfig(YamlConfiguration yaml){
        try {
            yaml.save(super.getCanonicalFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ajoutSqlFile(){
        try {
            super.getParentFile().mkdirs();
            if (super.createNewFile()){
                System.out.println("Fichier SQL créé");
                if (fichierVide(super.getCanonicalFile())){
                    String ret ="-- phpMyAdmin SQL Dump\n" +
                            "-- version 4.9.5deb2\n" +
                            "-- https://www.phpmyadmin.net/\n" +
                            "--\n" +
                            "-- Hôte : localhost:3306\n" +
                            "-- Généré le : mer. 09 déc. 2020 à 01:34\n" +
                            "-- Version du serveur :  10.3.25-MariaDB-0ubuntu0.20.04.1\n" +
                            "-- Version de PHP : 7.4.3\n" +
                            "\n" +
                            "SET SQL_MODE = \"NO_AUTO_VALUE_ON_ZERO\";\n" +
                            "SET AUTOCOMMIT = 0;\n" +
                            "START TRANSACTION;\n" +
                            "SET time_zone = \"+00:00\";\n" +
                            "\n" +
                            "\n" +
                            "/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;\n" +
                            "/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;\n" +
                            "/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;\n" +
                            "/*!40101 SET NAMES utf8mb4 */;\n" +
                            "\n" +
                            "--\n" +
                            "-- Base de données : `PluginPvpBox`\n" +
                            "--\n" +
                            "CREATE DATABASE IF NOT EXISTS `PluginPvpBox` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;\n" +
                            "USE `PluginPvpBox`;\n" +
                            "\n" +
                            "-- --------------------------------------------------------\n" +
                            "\n" +
                            "--\n" +
                            "-- Structure de la table `Grades`\n" +
                            "--\n" +
                            "\n" +
                            "CREATE TABLE `Grades` (\n" +
                            "  `ID` int(11) NOT NULL,\n" +
                            "  `NAME` varchar(255) NOT NULL,\n" +
                            "  `RANK` int(11) NOT NULL\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                            "\n" +
                            "--\n" +
                            "-- Déchargement des données de la table `Grades`\n" +
                            "--\n" +
                            "\n" +
                            "INSERT INTO `Grades` (`ID`, `NAME`, `RANK`) VALUES\n" +
                            "(1, 'Bronze 3', 1),\n" +
                            "(2, 'Bronze 2', 10),\n" +
                            "(3, 'Bonze 1', 20),\n" +
                            "(4, 'Argent 3', 30),\n" +
                            "(5, 'Argent 2', 40),\n" +
                            "(6, 'Argent 1', 50);\n" +
                            "\n" +
                            "-- --------------------------------------------------------\n" +
                            "\n" +
                            "--\n" +
                            "-- Structure de la table `JoueurGrades`\n" +
                            "--\n" +
                            "\n" +
                            "CREATE TABLE `JoueurGrades` (\n" +
                            "  `ID` int(11) NOT NULL,\n" +
                            "  `UUID` varchar(36) NOT NULL\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                            "\n" +
                            "-- --------------------------------------------------------\n" +
                            "\n" +
                            "--\n" +
                            "-- Structure de la table `Joueurs`\n" +
                            "--\n" +
                            "\n" +
                            "CREATE TABLE `Joueurs` (\n" +
                            "  `UUID` varchar(36) NOT NULL,\n" +
                            "  `NAME` varchar(255) NOT NULL,\n" +
                            "  `RANK` int(11) DEFAULT NULL\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                            "\n" +
                            "--\n" +
                            "-- Index pour les tables déchargées\n" +
                            "--\n" +
                            "\n" +
                            "--\n" +
                            "-- Index pour la table `Grades`\n" +
                            "--\n" +
                            "ALTER TABLE `Grades`\n" +
                            "  ADD PRIMARY KEY (`ID`);\n" +
                            "\n" +
                            "--\n" +
                            "-- Index pour la table `JoueurGrades`\n" +
                            "--\n" +
                            "ALTER TABLE `JoueurGrades`\n" +
                            "  ADD PRIMARY KEY (`ID`,`UUID`),\n" +
                            "  ADD KEY `fkIdx_17` (`ID`),\n" +
                            "  ADD KEY `fkIdx_21` (`UUID`);\n" +
                            "\n" +
                            "--\n" +
                            "-- Index pour la table `Joueurs`\n" +
                            "--\n" +
                            "ALTER TABLE `Joueurs`\n" +
                            "  ADD PRIMARY KEY (`UUID`);\n" +
                            "\n" +
                            "--\n" +
                            "-- AUTO_INCREMENT pour les tables déchargées\n" +
                            "--\n" +
                            "\n" +
                            "--\n" +
                            "-- AUTO_INCREMENT pour la table `Grades`\n" +
                            "--\n" +
                            "ALTER TABLE `Grades`\n" +
                            "  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;\n" +
                            "\n" +
                            "--\n" +
                            "-- Contraintes pour les tables déchargées\n" +
                            "--\n" +
                            "\n" +
                            "--\n" +
                            "-- Contraintes pour la table `JoueurGrades`\n" +
                            "--\n" +
                            "ALTER TABLE `JoueurGrades`\n" +
                            "  ADD CONSTRAINT `FK_17` FOREIGN KEY (`ID`) REFERENCES `Grades` (`ID`),\n" +
                            "  ADD CONSTRAINT `FK_21` FOREIGN KEY (`UUID`) REFERENCES `Joueurs` (`UUID`);\n" +
                            "COMMIT;\n" +
                            "\n" +
                            "/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;\n" +
                            "/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;\n" +
                            "/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;\n";
                    FileWriter myWriter = new FileWriter(super.getAbsoluteFile());
                    try {
                        myWriter.write(ret);
                        myWriter.close();
                        System.out.println("Success");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
