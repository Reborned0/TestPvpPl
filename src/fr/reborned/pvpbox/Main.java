package fr.reborned.pvpbox;

import fr.reborned.pvpbox.commands.CommandPvpBox;
import fr.reborned.pvpbox.dbconnection.DbConnect;
import fr.reborned.pvpbox.fileconfig.FileManager;
import fr.reborned.pvpbox.pluginlistener.PluginListener;
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
        dbConnect.setInfo(this);
        dbConnect.loadConfigDB(this);
        dbConnect.creationDB(this);
    }

    public void onDisable() {
        System.out.println("Plugin PVPBOX DOWN !");
    }

    private void AjoutFichier(){
        final FileManager fileManager = new FileManager(this.getDataFolder(),"/configpl.yml");
        fileManager.ajoutFichierVide();
        final FileManager fileManager1 = new FileManager(this.getDataFolder(),"/PluginPvpBox.sql");
        fileManager1.ajoutSqlFile();
    }
}





