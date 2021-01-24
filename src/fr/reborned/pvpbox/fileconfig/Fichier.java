package fr.reborned.pvpbox.fileconfig;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fichier extends File {

    public Fichier(File file, String chemin) {
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

        try {
            configuration.save(super.getAbsoluteFile());
        } catch (IOException e){
            e.printStackTrace();
        }

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

        configuration.set(o.getWorld()+"."+ key  +"x", o.getBlockX());
        configuration.set(o.getWorld()+"."+ key  +"y", o.getBlockY());
        configuration.set(o.getWorld()+"."+ key  +"z", o.getBlockZ());

        try {
            configuration.save(super.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
