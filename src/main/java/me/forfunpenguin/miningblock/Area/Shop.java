package me.forfunpenguin.miningblock.Area;

import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import me.forfunpenguin.miningblock.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop implements Listener {
    private static String Shop = "商店";
    private static List<String> oreName= Arrays.asList("石頭", "煤礦", "鐵礦", "銅礦", "金礦", "紅石礦", "青金石礦", "鑽石礦", "綠寶石礦", "深板岩", "深板岩煤礦", "深板岩鐵礦", "深板岩銅礦", "深板岩金礦", "深板岩紅石礦", "深板岩青金石礦", "深板岩鑽石礦", "深板岩綠寶石礦", "黑曜石", "煤炭方塊", "鐵方塊", "銅方塊", "金方塊", "紅石方塊", "青金石方塊", "鑽石方塊", "綠寶石方塊");
    private static int maxCanUpgradeLevel = 130;
    public static void openShop(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());

        Inventory inv = Bukkit.createInventory(player, 9*6, Shop);
        int nextLvl = playerMemory.getAreaLevel() + 1;
        if (playerMemory.getAreaLevel() < maxCanUpgradeLevel) {
            if (upgradeDesc(player).size() == 1) {
                inv.setItem(22, ItemUtils.getItem(new ItemStack(Material.NETHER_STAR), "&a場地升級", "", "&f等級: &7" + playerMemory.getAreaLevel() + " ➨ &a" + nextLvl, upgradeDesc(player).get(0), "", "&f升級費用: &6" + splitCoins(upgradeCost(player))));
            } else if (upgradeDesc(player).size() == 2) {
                inv.setItem(22, ItemUtils.getItem(new ItemStack(Material.NETHER_STAR), "&a場地升級", "", "&f等級: &7" + playerMemory.getAreaLevel() + " ➨ &a" + nextLvl, upgradeDesc(player).get(0), upgradeDesc(player).get(1), "", "&f升級費用: &6" + splitCoins(upgradeCost(player))));
            }
        } else {
            inv.setItem(22, ItemUtils.getItem(new ItemStack(Material.NETHER_STAR), "&a場地升級", "", "&f等級: &7" + playerMemory.getAreaLevel(), "", "&c已達到最高等級!"));
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(Shop)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getSlot() == 22) {
                areaUpgrade(player);
            }
        }
    }

    private static double upgradeCost(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        return playerMemory.getAreaLevel() * 25 * (1 + (playerMemory.getAreaLevel() - 1) * 0.5 * (Math.floor((playerMemory.getAreaLevel() - 1) / 5) + 1));
    }

    private static String splitCoins(double coin) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(coin);
    }

    private static List<String> upgradeDesc(Player player) {
        List<String> desc = null;
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        if (playerMemory.getAreaLevel() < 40) {
            double stage = Math.ceil((double) (playerMemory.getAreaLevel() + 1) / 5);
            if ((playerMemory.getAreaLevel() + 1) % 5 == 0) {
                desc = Arrays.asList("&7 • 礦物生成時間 -&a0.25&7s", "&7 • " + oreName.get((int) stage) + "最大可生成機率提升&a20&7%");
            } else {
                desc = Arrays.asList("&7 • " + oreName.get((int) stage) + "最大可生成機率提升&a20&7%");
            }
        }
        if (playerMemory.getAreaLevel() >= 40 & playerMemory.getAreaLevel() < 130) {
            double stage = Math.ceil((double) (playerMemory.getAreaLevel() - 40 + 1) / 10);
            if ((playerMemory.getAreaLevel() + 1) % 10 == 0) {
                desc = Arrays.asList("&7 • 礦物生成時間 -&a0.1&7s", "&7 • " + oreName.get((int) stage + 8) + "最大可生成機率提升&a10&7%");
            } else {
                desc = Arrays.asList("&7 • " + oreName.get((int) stage + 8) + "最大可生成機率提升&a10&7%");
            }
        }
        return desc;
    }
    private void areaUpgrade(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        if (playerMemory.getAreaLevel() >= 0 & playerMemory.getAreaLevel() < 130) {
            player.closeInventory();
            playerMemory.setAreaLevel(playerMemory.getAreaLevel() + 1);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a場地等級已提升至等級" + playerMemory.getAreaLevel() + ", 礦物的最大生成機率已提升."));
            updataRegenTime(player);
            updataSpawnChance(player);
        }
    }

    private void updataRegenTime(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        player.sendMessage(String.valueOf(playerMemory.getAreaLevel()));
        //double stage = Math.floor(playerMemory.getAreaLevel()/ 10);
        double reduceTime = 0;
        if (playerMemory.getAreaLevel() <= 40) {
            reduceTime = Math.floor(playerMemory.getAreaLevel()/ 10) * 0.25;
        }
        if (playerMemory.getAreaLevel() > 40 & playerMemory.getAreaLevel() <= 130) {
            reduceTime = 1 + Math.floor((playerMemory.getAreaLevel() - 40) / 10) * 0.1;
        }
        playerMemory.setRegenTime(5 - reduceTime);
    }
    private void updataSpawnChance(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        playerMemory.setMaxSpawnChanceList(Arrays.asList(100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        if (playerMemory.getAreaLevel() <= 40) {
            double stage = Math.ceil((double) (playerMemory.getAreaLevel() + 1) / 5);
            for (int x = 0; x < stage + 1; x++) {
                if (stage > x) {  //表示在這之前的都是100%
                    playerMemory.getMaxSpawnChanceList().set(x, 100);
                } else {
                    playerMemory.getMaxSpawnChanceList().set(x, 20 * (playerMemory.getAreaLevel() % 5));
                }
            }
        }
        if (playerMemory.getAreaLevel() > 40 & playerMemory.getAreaLevel() <= 130) {
            double stage = Math.ceil((double) (playerMemory.getAreaLevel() - 40 + 1) / 10);
            for (int x = 0; x < 9; x++) { //先將前面的都捕到100
                playerMemory.getMaxSpawnChanceList().set(x, 100);
            }
            for (int x = 0; x < stage + 1; x++) {
                if (stage > x) {  //表示在這之前的都是100%
                    playerMemory.getMaxSpawnChanceList().set(x + 9, 100);
                } else {
                    playerMemory.getMaxSpawnChanceList().set(x + 9, 10 * (playerMemory.getAreaLevel() % 10));
                }
            }
        }
    }
}
