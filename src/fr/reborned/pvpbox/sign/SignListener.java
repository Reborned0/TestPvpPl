package fr.reborned.pvpbox.sign;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getClickedBlock() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            BlockState blockState = event.getClickedBlock().getState();
            if(blockState instanceof Sign) {
                Sign sign = (Sign) blockState;
                if (sign.getLine(0).equals("[PvP]")) {
                    player.chat("/pvp " + sign.getLine(1));
                }
            }
        }
    }
}