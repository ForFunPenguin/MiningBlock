package me.forfunpenguin.miningblock.Area;

import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BlockHandle {
    private static List<Material> oreMaterial= Arrays.asList(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.COPPER_ORE, Material.GOLD_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.DEEPSLATE, Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_COPPER_ORE, Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.OBSIDIAN, Material.COAL_BLOCK, Material.IRON_BLOCK, Material.COPPER_BLOCK, Material.GOLD_BLOCK, Material.REDSTONE_BLOCK, Material.LAPIS_BLOCK, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK);

    public static void breakBlock(Player player, Block block) {
        if (oreMaterial.contains(block.getType())) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getAreaCanRegenBlockList().add(block);
            giveItem(player, block.getType());
            block.setType(Material.BARRIER);
        }
    }

    private static void giveItem(Player player, Material ore) {
        if (ore.equals(Material.STONE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getCobblestoneAmountList().set(0, playerMemory.getCobblestoneAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(0) == 0) {
                playerMemory.getPlayerOreFoundList().set(0, 1);
            }
        } else if (ore.equals(Material.COAL_ORE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getCoalAmountList().set(0, playerMemory.getCoalAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(1) == 0) {
                playerMemory.getPlayerOreFoundList().set(1, 1);
            }
        } else if (ore.equals(Material.IRON_ORE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getIronAmountList().set(0, playerMemory.getIronAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(2) == 0) {
                playerMemory.getPlayerOreFoundList().set(2, 1);
            }
        } else if (ore.equals(Material.COPPER_ORE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getCopperAmountList().set(0, playerMemory.getCopperAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(3) == 0) {
                playerMemory.getPlayerOreFoundList().set(3, 1);
            }
        } else if (ore.equals(Material.GOLD_ORE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getGoldAmountList().set(0, playerMemory.getGoldAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(4) == 0) {
                playerMemory.getPlayerOreFoundList().set(4, 1);
            }
        } else if (ore.equals(Material.REDSTONE_ORE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getRedstoneAmountList().set(0, playerMemory.getRedstoneAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(5) == 0) {
                playerMemory.getPlayerOreFoundList().set(5, 1);
            }
        } else if (ore.equals(Material.LAPIS_ORE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getLapisAmountList().set(0, playerMemory.getLapisAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(6) == 0) {
                playerMemory.getPlayerOreFoundList().set(6, 1);
            }
        } else if (ore.equals(Material.DIAMOND_ORE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getDiamondAmountList().set(0, playerMemory.getDiamondAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(7) == 0) {
                playerMemory.getPlayerOreFoundList().set(7, 1);
            }
        } else if (ore.equals(Material.EMERALD_ORE)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getEmeraldAmountList().set(0, playerMemory.getEmeraldAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(8) == 0) {
                playerMemory.getPlayerOreFoundList().set(8, 1);
            }
        } else if (ore.equals(Material.OBSIDIAN)) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            playerMemory.getObsidianAmountList().set(0, playerMemory.getObsidianAmountList().get(0) + 1);
            if (playerMemory.getPlayerOreFoundList().get(9) == 0) {
                playerMemory.getPlayerOreFoundList().set(9, 1);
            }
        }
    }
}
