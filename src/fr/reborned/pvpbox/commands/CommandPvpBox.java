package fr.reborned.pvpbox.commands;

import fr.reborned.pvpbox.Main;
import fr.reborned.pvpbox.joueur.Joueur;
import fr.reborned.pvpbox.pluginlistener.Cuboid;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
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
                                yamlConfiguration.set(key + "X", location.getX());
                                yamlConfiguration.set(key + "Y", location.getY());
                                yamlConfiguration.set(key + "Z", location.getZ());
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

                                key = "region.";
                                System.out.println("Loc 1 : "+ getL1() + "loc 2 :"+ this.getL2());
                                Cuboid cuboid = new Cuboid(this.getL1(),this.getL2());


                                yamlConfiguration.set(key + "cuboidL1", getL1());
                                yamlConfiguration.set(key + "cuboidL2", getL2());

                                try {
                                    yamlConfiguration.save(fileConfig());
                                    joueur.sendMessage("La region a bien été enregistré");
                                } catch (IOException e) {
                                    joueur.sendMessage("Region bug");
                                    e.printStackTrace();
                                }
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
