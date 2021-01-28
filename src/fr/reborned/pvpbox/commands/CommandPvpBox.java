package fr.reborned.pvpbox.commands;

import fr.reborned.pvpbox.Main;
import fr.reborned.pvpbox.joueur.Joueur;
import fr.reborned.pvpbox.pluginlistener.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CommandPvpBox extends Joueur implements TabExecutor {
    private Main main;

    public CommandPvpBox(Main main) {
        super(main);
        this.main =main;
    }

    private File fileConfig() {
        return new File(this.main.getDataFolder(), "configpl.yml");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player joueur = (Player) commandSender;
            if (args.length == 0) {
                joueur.sendMessage("Commande invalide /pvp");
            } else {
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(fileConfig());
                String key = "";
                if(args[0].equalsIgnoreCase("set")){
                    if (!args[1].isEmpty()) {
                        switch (args[1].toLowerCase()) {
                            case "respawnpoint":
                                key = "respawn.";
                                Location location = joueur.getLocation();



                                yamlConfiguration.set(key + "world", location.getWorld().getName());
                                yamlConfiguration.set(key + "x", location.getX());
                                yamlConfiguration.set(key + "y", location.getY());
                                yamlConfiguration.set(key + "z", location.getZ());
                                yamlConfiguration.set(key + "Yaw", location.getYaw());
                                yamlConfiguration.set(key + "Pitch", location.getPitch());

                                try {
                                    yamlConfiguration.save(fileConfig());
                                    joueur.sendMessage("Le respawn a bien été enregistré");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "region":
                                key="Locations";
                                final ConfigurationSection confiSection = yamlConfiguration.getConfigurationSection(key);
                                ArrayList<Location> Locations = new ArrayList<Location>();
                                    for (String locaString : confiSection.getKeys(false)){

                                        ConfigurationSection locationfromConf = yamlConfiguration.getConfigurationSection(key+"."+locaString);
                                        int x = locationfromConf.getInt(".x");
                                        int y = locationfromConf.getInt(".y");
                                        int z = locationfromConf.getInt(".z");
                                        String world = locationfromConf.getString(".world");
                                        Location l = new Location(Bukkit.getWorld(world),x,y,z);
                                        Locations.add(l);
                                    }
                                    Cuboid cuboid = new Cuboid(Locations.get(0),Locations.get(1));
                                    joueur.sendMessage("Cuboid créé !");
                                break;
                            default:
                                joueur.sendMessage("Commande incomplète");
                                break;
                        }
                    }else
                    {
                        joueur.sendMessage(ChatColor.RED+"NullPointer");
                    }

                }
                else if (args[0].equalsIgnoreCase("swordset")) {
                        swordsetter(joueur);
                    }
                }

            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length ==1){
            List<String> argsStringList1 = new ArrayList<>();
            argsStringList1.add("swordset");
            argsStringList1.add("set");
            argsStringList1.sort(Comparator.naturalOrder());
            return argsStringList1;
        }else if (args.length == 2){
            List<String> argsStringList2 = new ArrayList<>();
            argsStringList2.add("respawnpoint");
            argsStringList2.add("region");
            argsStringList2.sort(Comparator.naturalOrder());
            return  argsStringList2;
        }
        return null;
    }
}
