package fr.reborned.pvpbox.pluginlistener;

import fr.reborned.pvpbox.Main;
import fr.reborned.pvpbox.dbconnection.RequestSQL;
import fr.reborned.pvpbox.joueur.Joueur;
import fr.reborned.pvpbox.joueur.ManagerLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class PluginListener extends Joueur implements Listener {

    public PluginListener(Main main) {
        super(main);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        RequestSQL requestSQL = new RequestSQL();
        if (e.getPlayer() != null) {
            requestSQL.findByNameAndUUID(e.getPlayer().getName(),e.getPlayer().getUniqueId().toString());
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
        ManagerLocations managerLocations = new ManagerLocations(e.getEntity().getPlayer());
        managerLocations.teleportToSpawn(e.getEntity().getPlayer());
        //Todo faire le +1 kill -0.5 exp mort et -1 mort


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
            RequestSQL requestSQL = new RequestSQL();
            requestSQL.register(e.getPlayer(),getStatistiques(),getGrade());
        }
    }


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
