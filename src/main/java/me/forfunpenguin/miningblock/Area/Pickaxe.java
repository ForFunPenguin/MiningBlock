package me.forfunpenguin.miningblock.Area;

import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import me.forfunpenguin.miningblock.Utils.ItemUtils;
import me.forfunpenguin.miningblock.Utils.NBTUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Pickaxe {
    public static void givePickaxe(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        if (playerMemory.getToolMaterial().equalsIgnoreCase("Wooden")) {
            ItemStack item = ItemUtils.getItem(new ItemStack(Material.WOODEN_PICKAXE) , "&f木鎬", "&8破壞強度 " + playerMemory.getBreakPower(), "","&7挖掘速度: &a+" + playerMemory.getMiningSpeed());
            item = NBTUtils.putIntTag(item, "BreakPower", playerMemory.getBreakPower());
            item = NBTUtils.putIntTag(item, "MiningSpeed", playerMemory.getMiningSpeed());
            player.getInventory().setItem(0, item);
        }
    }
}
