package me.forfunpenguin.miningblock.Area;

import me.forfunpenguin.miningblock.Filehandler.AreaDataHandle;
import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import me.forfunpenguin.miningblock.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OreInventory {

    private static String OreInventory = "礦物儲物箱";
    public static void openOreInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9*6, OreInventory);
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        //playerMemory.getCoalAmountList().stream().mapToInt(Integer::intValue).sum() 可以取加總
        if (playerMemory.getPlayerOreFoundList().get(0) == 1) {
            inv.setItem(0, ItemUtils.getItem(new ItemStack(Material.COBBLESTONE), "&f鵝卵石" + getOreAmountString(player, "cobblestone")));
        } else {
            inv.setItem(0, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c鵝卵石", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(1) == 1) {
            inv.setItem(1, ItemUtils.getItem(new ItemStack(Material.COAL), "&f煤炭" + getOreAmountString(player, "coal")));
        } else {
            inv.setItem(1, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c煤炭", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(2) == 1) {
            inv.setItem(2, ItemUtils.getItem(new ItemStack(Material.IRON_INGOT), "&f鐵錠" + getOreAmountString(player, "iron")));
        } else {
            inv.setItem(2, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c鐵錠", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(3) == 1) {
            inv.setItem(3, ItemUtils.getItem(new ItemStack(Material.COPPER_INGOT), "&f銅錠" + getOreAmountString(player, "copper")));
        } else {
            inv.setItem(3, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c銅錠", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(4) == 1) {
            inv.setItem(4, ItemUtils.getItem(new ItemStack(Material.GOLD_INGOT), "&f金錠" + getOreAmountString(player, "gold")));
        } else {
            inv.setItem(4, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c金錠", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(5) == 1) {
            inv.setItem(5, ItemUtils.getItem(new ItemStack(Material.REDSTONE), "&f紅石" + getOreAmountString(player, "redstone")));
        } else {
            inv.setItem(5, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c紅石", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(6) == 1) {
            inv.setItem(6, ItemUtils.getItem(new ItemStack(Material.LAPIS_LAZULI), "&f青金石" + getOreAmountString(player, "lapis")));
        } else {
            inv.setItem(6, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c青金石", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(7) == 1) {
            inv.setItem(7, ItemUtils.getItem(new ItemStack(Material.DIAMOND), "&f鑽石" + getOreAmountString(player, "diamond")));
        } else {
            inv.setItem(7, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c鑽石", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(8) == 1) {
            inv.setItem(8, ItemUtils.getItem(new ItemStack(Material.EMERALD), "&f綠寶石" + getOreAmountString(player, "emerald")));
        } else {
            inv.setItem(8, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c綠寶石", "", "&c尚未發現!"));
        }
        if (playerMemory.getPlayerOreFoundList().get(9) == 1) {
            inv.setItem(9, ItemUtils.getItem(new ItemStack(Material.OBSIDIAN), "&f黑曜石" + getOreAmountString(player, "obsidian")));
        } else {
            inv.setItem(9, ItemUtils.getItem(new ItemStack(Material.GRAY_DYE), "&c黑曜石", "", "&c尚未發現!"));
        }
        player.openInventory(inv);
    }

    private static String getOreAmountString(Player player, String ore) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        if (ore.equalsIgnoreCase("cobblestone")) {
            return "&f " + playerMemory.getCobblestoneAmountList().get(0) + " &7|&a " + playerMemory.getCobblestoneAmountList().get(1) + " &7|&9 " + playerMemory.getCobblestoneAmountList().get(2) + " &7|&5 " + playerMemory.getCobblestoneAmountList().get(3) + " &7|&6 " + playerMemory.getCobblestoneAmountList().get(4) + " &7|&c " + playerMemory.getCobblestoneAmountList().get(5);
        } else if (ore.equalsIgnoreCase("coal")) {
            return "&f " + playerMemory.getCoalAmountList().get(0) + " &7|&a " + playerMemory.getCoalAmountList().get(1) + " &7|&9 " + playerMemory.getCoalAmountList().get(2) + " &7|&5 " + playerMemory.getCoalAmountList().get(3) + " &7|&6 " + playerMemory.getCoalAmountList().get(4) + " &7|&c " + playerMemory.getCoalAmountList().get(5);
        } else if (ore.equalsIgnoreCase("copper")) {
            return "&f " + playerMemory.getCopperAmountList().get(0) + " &7|&a " + playerMemory.getCopperAmountList().get(1) + " &7|&9 " + playerMemory.getCopperAmountList().get(2) + " &7|&5 " + playerMemory.getCopperAmountList().get(3) + " &7|&6 " + playerMemory.getCopperAmountList().get(4) + " &7|&c " + playerMemory.getCopperAmountList().get(5);
        } else if (ore.equalsIgnoreCase("iron")) {
            return "&f " + playerMemory.getIronAmountList().get(0) + " &7|&a " + playerMemory.getIronAmountList().get(1) + " &7|&9 " + playerMemory.getIronAmountList().get(2) + " &7|&5 " + playerMemory.getIronAmountList().get(3) + " &7|&6 " + playerMemory.getIronAmountList().get(4) + " &7|&c " + playerMemory.getIronAmountList().get(5);
        } else if (ore.equalsIgnoreCase("gold")) {
            return "&f " + playerMemory.getGoldAmountList().get(0) + " &7|&a " + playerMemory.getGoldAmountList().get(1) + " &7|&9 " + playerMemory.getGoldAmountList().get(2) + " &7|&5 " + playerMemory.getGoldAmountList().get(3) + " &7|&6 " + playerMemory.getGoldAmountList().get(4) + " &7|&c " + playerMemory.getGoldAmountList().get(5);
        } else if (ore.equalsIgnoreCase("redstone")) {
            return "&f " + playerMemory.getRedstoneAmountList().get(0) + " &7|&a " + playerMemory.getRedstoneAmountList().get(1) + " &7|&9 " + playerMemory.getRedstoneAmountList().get(2) + " &7|&5 " + playerMemory.getRedstoneAmountList().get(3) + " &7|&6 " + playerMemory.getRedstoneAmountList().get(4) + " &7|&c " + playerMemory.getRedstoneAmountList().get(5);
        } else if (ore.equalsIgnoreCase("lapis")) {
            return "&f " + playerMemory.getLapisAmountList().get(0) + " &7|&a " + playerMemory.getLapisAmountList().get(1) + " &7|&9 " + playerMemory.getLapisAmountList().get(2) + " &7|&5 " + playerMemory.getLapisAmountList().get(3) + " &7|&6 " + playerMemory.getLapisAmountList().get(4) + " &7|&c " + playerMemory.getLapisAmountList().get(5);
        } else if (ore.equalsIgnoreCase("diamond")) {
            return "&f " + playerMemory.getDiamondAmountList().get(0) + " &7|&a " + playerMemory.getDiamondAmountList().get(1) + " &7|&9 " + playerMemory.getDiamondAmountList().get(2) + " &7|&5 " + playerMemory.getDiamondAmountList().get(3) + " &7|&6 " + playerMemory.getDiamondAmountList().get(4) + " &7|&c " + playerMemory.getDiamondAmountList().get(5);
        } else if (ore.equalsIgnoreCase("emerald")) {
            return "&f " + playerMemory.getEmeraldAmountList().get(0) + " &7|&a " + playerMemory.getEmeraldAmountList().get(1) + " &7|&9 " + playerMemory.getEmeraldAmountList().get(2) + " &7|&5 " + playerMemory.getEmeraldAmountList().get(3) + " &7|&6 " + playerMemory.getEmeraldAmountList().get(4) + " &7|&c " + playerMemory.getEmeraldAmountList().get(5);
        } else if (ore.equalsIgnoreCase("obsidian")) {
            return "&f " + playerMemory.getObsidianAmountList().get(0) + " &7|&a " + playerMemory.getObsidianAmountList().get(1) + " &7|&9 " + playerMemory.getObsidianAmountList().get(2) + " &7|&5 " + playerMemory.getObsidianAmountList().get(3) + " &7|&6 " + playerMemory.getObsidianAmountList().get(4) + " &7|&c " + playerMemory.getObsidianAmountList().get(5);
        } else {
            return null;
        }
    }
}
