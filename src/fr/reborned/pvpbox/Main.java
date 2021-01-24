package fr.reborned.pvpbox;

import fr.reborned.pvpbox.commands.CommandPvpBox;
import fr.reborned.pvpbox.dbconnection.DbConnect;
import fr.reborned.pvpbox.fileconfig.Fichier;
import fr.reborned.pvpbox.pluginlistener.PluginListener;
import fr.reborned.pvpbox.sign.SignListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        System.out.println("Plugin PVPBOX UP !");
        getCommand("pvp").setExecutor(new CommandPvpBox(this));
        getCommand("pvp").setTabCompleter(new CommandPvpBox(this));
        AjoutFichier();


        //Bukkit.getPluginManager().registerEvents(new SignListener(), this);
        getServer().getPluginManager().registerEvents(new PluginListener(this), this);

    }
    public void onLoad(){
        DbConnect dbConnect = new DbConnect();
        dbConnect.getInfo(this);
    }

    public void onDisable() {
        System.out.println("Plugin PVPBOX DOWN !");
    }

    private void AjoutFichier(){
        final Fichier fichier = new Fichier(this.getDataFolder(),"/configpl.yml");
        fichier.ajoutFichierVide();
    }
}





