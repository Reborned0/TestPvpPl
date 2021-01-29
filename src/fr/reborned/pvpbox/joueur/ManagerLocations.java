package fr.reborned.pvpbox.joueur;

import fr.reborned.pvpbox.Main;
import fr.reborned.pvpbox.pluginlistener.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ManagerLocations {
    private Player player;
    private Main main;

    public ManagerLocations(Player player) {
        this.player = player;
        this.main = new Main();
    }

    private YamlConfiguration yamlConfig(){
        YamlConfiguration yamlConfiguration = null;
        Main main = new Main();
        try {
            yamlConfiguration = YamlConfiguration.loadConfiguration(new File(this.main.getDataFolder(),"configpl.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yamlConfiguration;
    }

    public void teleportToSpawn(Player player){
        String path = "respawn";
        Location spawn = new Location(Bukkit.getWorld(yamlConfig().getConfigurationSection(path).getString(".world")), yamlConfig().getConfigurationSection(path).getInt(".x"), yamlConfig().getConfigurationSection(path).getInt(".y"), yamlConfig().getConfigurationSection(path).getInt(".z"));
        player.teleport(spawn);
    }

    public boolean playerIsInCuboid(){
        boolean ret = false;
        Location loc = this.player.getLocation();
        Cuboid cuboid = mapPvpBox();
        if (loc.getX() >= cuboid.getLowerX() && loc.getX() <= cuboid.getUpperX() && loc.getY() >= cuboid.getLowerY() && loc.getY() <= cuboid.getUpperY() && loc.getZ() >= cuboid.getLowerZ() && loc.getZ() <= cuboid.getUpperZ()){
            this.player.sendMessage("PVP ACTIF");
            ret = true;
        }
        return ret;

    }

    private Cuboid mapPvpBox(){
        Cuboid cuboid = null;
        String path1 = "Locations.location_1";
        String path2 = "Locations.location_2";
        try {
            Location l1 = new Location(Bukkit.getWorld(yamlConfig().getConfigurationSection(path1).getString(".world")),
                    yamlConfig().getConfigurationSection(path1).getInt(".x"),
                    yamlConfig().getConfigurationSection(path1).getInt(".y"),
                    yamlConfig().getConfigurationSection(path1).getInt(".z"));

            Location l2 = new Location(Bukkit.getWorld(yamlConfig().getConfigurationSection(path2).getString(".world")),
                    yamlConfig().getConfigurationSection(path2).getInt(".x"),
                    yamlConfig().getConfigurationSection(path2).getInt(".y"),
                    yamlConfig().getConfigurationSection(path2).getInt(".z"));
            cuboid = new Cuboid(l1,l2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cuboid;
    }

}
