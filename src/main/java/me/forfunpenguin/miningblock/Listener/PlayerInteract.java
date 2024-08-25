package me.forfunpenguin.miningblock.Listener;

import me.forfunpenguin.miningblock.Area.OreInventory;
import me.forfunpenguin.miningblock.Area.Quest;
import me.forfunpenguin.miningblock.Area.Setting;
import me.forfunpenguin.miningblock.Area.Shop;
import me.forfunpenguin.miningblock.Commands.MiningBlock;
import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(event.getPlayer().getUniqueId());
            if (playerMemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY") & event.getPlayer().getItemInHand().getType().equals(Material.BOOK)) {
                event.setCancelled(true);
                event.getPlayer().performCommand("miningblock join");
            }
            if (!playerMemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY") & event.getPlayer().getItemInHand().getType().equals(Material.CHEST)) {
                event.setCancelled(true);
                OreInventory.openOreInventory(event.getPlayer());
            }
            if (!playerMemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY") & event.getPlayer().getItemInHand().getType().equals(Material.EMERALD)) {
                event.setCancelled(true);
                Shop.openShop(event.getPlayer());
            }
            if (!playerMemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY") & event.getPlayer().getItemInHand().getType().equals(Material.WRITTEN_BOOK)) {
                event.setCancelled(true);
                Quest.openQuest(event.getPlayer());
            }
            if (!playerMemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY") & event.getPlayer().getItemInHand().getType().equals(Material.COMPARATOR)) {
                event.setCancelled(true);
                Setting.openSettingInfo(event.getPlayer());
            }
        }
    }
}
