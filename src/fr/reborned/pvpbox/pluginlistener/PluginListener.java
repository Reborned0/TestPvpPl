package fr.reborned.pvpbox.pluginlistener;

import fr.reborned.pvpbox.Main;
import fr.reborned.pvpbox.dbconnection.Connected;
import fr.reborned.pvpbox.fileconfig.Fichier;
import fr.reborned.pvpbox.joueur.Joueur;
import fr.reborned.pvpbox.joueur.Teleportation;
import org.apache.logging.log4j.core.net.Priority;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.parser.Entity;

public class PluginListener extends Joueur implements Listener {

    public PluginListener(Main main) {
        super(main);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Connected connected = new Connected();
        if (e.getPlayer() != null) {
            connected.findByNameAndUUID(e.getPlayer().getDisplayName(),e.getPlayer().getUniqueId().toString());
            playerjoining(e.getPlayer());
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPick(EntityPickupItemEvent e) {
        e.setCancelled(!e.getEntity().isOp());
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        e.getDrops().clear();
        e.getEventName();
        Teleportation teleportation = new Teleportation(e.getEntity().getPlayer());
        teleportation.teleportPlayer();


    }
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        if (e.toWeatherState()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onThunderChange(ThunderChangeEvent e){
        if (e.toThunderState()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onDeconnection(PlayerQuitEvent e){
        if (e.getPlayer() != null){
            Connected connected = new Connected();
            connected.register(e.getPlayer(),getStatistiques(),getGrade());
        }
    }

//    @EventHandler
//    public void blockBreakEvent(BlockBreakEvent e){
//        e.setCancelled(!e.getPlayer().isOp());
//    }

    @EventHandler
    public void onInterract(PlayerInteractEvent e) {
        if (e.getItem() == null) {
            return;
        } else {
            if (e.getItem().getType().equals(Material.WOOD_SWORD) && ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase("PVPSWORD_SET")) {

                if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    Location location =e.getClickedBlock().getLocation();
                    e.getPlayer().sendMessage(ChatColor.GOLD+""+ ChatColor.UNDERLINE +"Block position 1 :"+ChatColor.RED+" X: "+location.getBlockX() + ChatColor.BLUE +" Y: "+location.getBlockY() + ChatColor.GREEN +" Z: "+location.getBlockZ());

                    saveInfile("location_1.",location);


                    e.setCancelled(true);
                } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Location location =e.getClickedBlock().getLocation();
                    e.getPlayer().sendMessage(ChatColor.GOLD+""+ ChatColor.UNDERLINE +"Block position 2 :"+ChatColor.RED+" X: "+location.getBlockX() + ChatColor.BLUE +" Y: "+location.getBlockY() + ChatColor.GREEN +" Z: "+location.getBlockZ());
                    saveInfile("location_2.",location);

                }
            }

        }
    }

}
