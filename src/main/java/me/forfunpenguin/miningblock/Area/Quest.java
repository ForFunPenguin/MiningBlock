package me.forfunpenguin.miningblock.Area;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Quest {
    private static String Quest = "任務";
    public static void openQuest(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9*6, Quest);


        player.openInventory(inv);
    }
}
