package me.forfunpenguin.miningblock.Listener;

import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
        PlayerDataHandle.savePlayerData(event.getPlayer().getUniqueId());
    }
}
