package fr.reborned.pvpbox.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        System.out.println(sender.toString());
        System.out.println(cmd.toString());
        System.out.println(msg);

        if (!(sender instanceof Player) || args.length == 0 || !args[0].matches("map[1-3]")) {
            sender.sendMessage("§r[§4NitroPvP§r]Erreur syntaxe: [§e/pvp§r: §9map1§r/§9map2§r/§9map3§r]");
            return false;
        }
        Player player = (Player)sender;
        int mapNumber = Integer.parseInt(args[0].replaceFirst("map", ""));
        player.sendMessage("§r[§4NitroPvP§r]Tu as été teleporté a la map ("+args[0]+")");
        player.teleport(new Location(Bukkit.getWorld("world"), (mapNumber+1)*10, 4, 827, 0, 0));
        return false;

    }

}
