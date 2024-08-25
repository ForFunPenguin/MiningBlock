package me.forfunpenguin.miningblock.Listener;

import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Utils.ItemUtils;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, false, false), true);
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -406.5, 109, 227.5, 180, 0));
        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setItem(0, ItemUtils.getItem(new ItemStack(Material.BOOK), "&a選擇伺服器 &7(右鍵點擊)"));
        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&a輸入/miningblock join來選擇可加入的伺服器遊玩!"));
        PlayerDataHandle.loadPlayerData(event.getPlayer().getUniqueId());
    }
}
