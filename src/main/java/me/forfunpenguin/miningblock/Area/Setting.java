package me.forfunpenguin.miningblock.Area;

import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import me.forfunpenguin.miningblock.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Setting implements Listener {
    //設定場地生成機率選單 guimemory要新增用來記錄設定的數值 儲存時更新過去player 使用List儲存 儲存時使用總和查看是否為100
    private static String settingInfo = "生成機率";
    private static String settingMenu = "設定生成機率";
    private static List<String> oreName= Arrays.asList("石頭", "煤礦", "鐵礦", "銅礦", "金礦", "紅石礦", "青金石礦", "鑽石礦", "綠寶石礦", "深板岩", "深板岩煤礦", "深板岩鐵礦", "深板岩銅礦", "深板岩金礦", "深板岩紅石礦", "深板岩青金石礦", "深板岩鑽石礦", "深板岩綠寶石礦", "黑曜石", "煤炭方塊", "鐵方塊", "銅方塊", "金方塊", "紅石方塊", "青金石方塊", "鑽石方塊", "綠寶石方塊");
    private static List<Material> oreMaterial= Arrays.asList(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.COPPER_ORE, Material.GOLD_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.DEEPSLATE, Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_COPPER_ORE, Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.OBSIDIAN, Material.COAL_BLOCK, Material.IRON_BLOCK, Material.COPPER_BLOCK, Material.GOLD_BLOCK, Material.REDSTONE_BLOCK, Material.LAPIS_BLOCK, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK);

    public static void openSettingInfo(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        Inventory inv = Bukkit.createInventory(player, 9*6, settingInfo);

        inv.setItem(10, ItemUtils.getItem(new ItemStack(Material.STONE), "&f石頭", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(0) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(0) + "%"));
        inv.setItem(11, ItemUtils.getItem(new ItemStack(Material.COAL_ORE), "&f煤礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(1) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(1) + "%"));
        inv.setItem(12, ItemUtils.getItem(new ItemStack(Material.IRON_ORE), "&f鐵礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(2) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(2) + "%"));
        inv.setItem(13, ItemUtils.getItem(new ItemStack(Material.COPPER_ORE), "&f銅礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(3) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(3) + "%"));
        inv.setItem(14, ItemUtils.getItem(new ItemStack(Material.GOLD_ORE), "&f金礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(4) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(4) + "%"));
        inv.setItem(15, ItemUtils.getItem(new ItemStack(Material.REDSTONE_ORE), "&f紅石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(5) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(5) + "%"));
        inv.setItem(16, ItemUtils.getItem(new ItemStack(Material.LAPIS_ORE), "&f青金石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(6) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(6) + "%"));

        inv.setItem(19, ItemUtils.getItem(new ItemStack(Material.DIAMOND_ORE), "&f鑽石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(7) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(7) + "%"));
        inv.setItem(20, ItemUtils.getItem(new ItemStack(Material.EMERALD_ORE), "&f綠寶石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(8) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(8) + "%"));
        inv.setItem(21, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE), "&f深板岩", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(9) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(9) + "%"));
        inv.setItem(22, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_COAL_ORE), "&f深板岩煤礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(10) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(10) + "%"));
        inv.setItem(23, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_IRON_ORE), "&f深板岩鐵礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(11) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(11) + "%"));
        inv.setItem(24, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_COPPER_ORE), "&f深板岩銅礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(12) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(12) + "%"));
        inv.setItem(25, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_GOLD_ORE), "&f深板岩金礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(13) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(13) + "%"));

        inv.setItem(28, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_REDSTONE_ORE), "&f深板岩紅石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(14) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(14) + "%"));
        inv.setItem(29, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_REDSTONE_ORE), "&f深板岩紅石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(15) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(15) + "%"));
        inv.setItem(30, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_LAPIS_ORE), "&f深板岩青金石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(16) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(16) + "%"));
        inv.setItem(31, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_DIAMOND_ORE), "&f深板岩鑽石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(17) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(17) + "%"));
        inv.setItem(32, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_EMERALD_ORE), "&f深板岩綠寶石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(18) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(18) + "%"));
        inv.setItem(33, ItemUtils.getItem(new ItemStack(Material.OBSIDIAN), "&f黑曜石", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(19) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(19) + "%"));
        inv.setItem(34, ItemUtils.getItem(new ItemStack(Material.COAL_BLOCK), "&f煤炭方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(20) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(20) + "%"));

        inv.setItem(37, ItemUtils.getItem(new ItemStack(Material.IRON_BLOCK), "&f鐵方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(21) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(21) + "%"));
        inv.setItem(38, ItemUtils.getItem(new ItemStack(Material.COPPER_BLOCK), "&f銅方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(22) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(22) + "%"));
        inv.setItem(39, ItemUtils.getItem(new ItemStack(Material.GOLD_BLOCK), "&f金方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(23) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(23) + "%"));
        inv.setItem(40, ItemUtils.getItem(new ItemStack(Material.REDSTONE_BLOCK), "&f紅石方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(24) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(24) + "%"));
        inv.setItem(41, ItemUtils.getItem(new ItemStack(Material.LAPIS_BLOCK), "&f青金石方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(25) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(25) + "%"));
        inv.setItem(42, ItemUtils.getItem(new ItemStack(Material.DIAMOND_BLOCK), "&f鑽石方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(26) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(26) + "%"));
        inv.setItem(43, ItemUtils.getItem(new ItemStack(Material.EMERALD_BLOCK), "&f綠寶石方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnChanceList().get(27) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(27) + "%"));

        inv.setItem(49, ItemUtils.getItem(new ItemStack(Material.BARRIER), "&c關閉"));
        inv.setItem(53, ItemUtils.getItem(new ItemStack(Material.COMPARATOR), "&a設定場地生成機率"));
        player.openInventory(inv);
        updateSpawnDetail(player, "settingInfo");
    }

    public void openSettingMenu(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        List<Integer> list = playerMemory.getPlayerSpawnChanceList();
        playerMemory.setPlayerSpawnSettingChanceList(new ArrayList<Integer>(playerMemory.getPlayerSpawnChanceList())); //如果不使用new 會導致這兩個變為同一個 在梗改時會同時修改兩邊
        Inventory inv = Bukkit.createInventory(player, 9 * 6, settingMenu);

        inv.setItem(10, ItemUtils.getItem(new ItemStack(Material.STONE), "&f石頭", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(0) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(0) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(11, ItemUtils.getItem(new ItemStack(Material.COAL_ORE), "&f煤礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(1) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(1) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(12, ItemUtils.getItem(new ItemStack(Material.IRON_ORE), "&f鐵礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(2) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(2) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(13, ItemUtils.getItem(new ItemStack(Material.COPPER_ORE), "&f銅礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(3) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(3) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(14, ItemUtils.getItem(new ItemStack(Material.GOLD_ORE), "&f金礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(4) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(4) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(15, ItemUtils.getItem(new ItemStack(Material.REDSTONE_ORE), "&f紅石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(5) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(5) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(16, ItemUtils.getItem(new ItemStack(Material.LAPIS_ORE), "&f青金石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(6) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(6) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));

        inv.setItem(19, ItemUtils.getItem(new ItemStack(Material.DIAMOND_ORE), "&f鑽石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(7) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(7) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(20, ItemUtils.getItem(new ItemStack(Material.EMERALD_ORE), "&f綠寶石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(8) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(8) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(21, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE), "&f深板岩", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(9) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(9) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(22, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_COAL_ORE), "&f深板岩煤礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(10) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(10) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(23, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_IRON_ORE), "&f深板岩鐵礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(11) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(11) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(24, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_COPPER_ORE), "&f深板岩銅礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(12) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(12) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(25, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_GOLD_ORE), "&f深板岩金礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(13) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(13) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));

        inv.setItem(28, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_REDSTONE_ORE), "&f深板岩紅石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(14) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(14) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(29, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_REDSTONE_ORE), "&f深板岩紅石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(15) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(15) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(30, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_LAPIS_ORE), "&f深板岩青金石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(16) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(16) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(31, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_DIAMOND_ORE), "&f深板岩鑽石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(17) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(17) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(32, ItemUtils.getItem(new ItemStack(Material.DEEPSLATE_EMERALD_ORE), "&f深板岩綠寶石礦", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(18) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(18) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(33, ItemUtils.getItem(new ItemStack(Material.OBSIDIAN), "&f黑曜石", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(19) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(19) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(34, ItemUtils.getItem(new ItemStack(Material.COAL_BLOCK), "&f煤炭方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(20) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(20) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));

        inv.setItem(37, ItemUtils.getItem(new ItemStack(Material.IRON_BLOCK), "&f鐵方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(21) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(21) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(38, ItemUtils.getItem(new ItemStack(Material.COPPER_BLOCK), "&f銅方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(22) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(22) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(39, ItemUtils.getItem(new ItemStack(Material.GOLD_BLOCK), "&f金方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(23) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(23) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(40, ItemUtils.getItem(new ItemStack(Material.REDSTONE_BLOCK), "&f紅石方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(24) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(24) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(41, ItemUtils.getItem(new ItemStack(Material.LAPIS_BLOCK), "&f青金石方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(25) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(25) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(42, ItemUtils.getItem(new ItemStack(Material.DIAMOND_BLOCK), "&f鑽石方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(26) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(26) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));
        inv.setItem(43, ItemUtils.getItem(new ItemStack(Material.EMERALD_BLOCK), "&f綠寶石方塊", "", "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(27) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(27) + "%", "", "&e點擊增加/減少1%", "&eShift-點擊增加/減少10%"));

        inv.setItem(45, ItemUtils.getItem(new ItemStack(Material.ARROW), "&a返回", "&7返回至" + settingInfo));
        inv.setItem(53, ItemUtils.getItem(new ItemStack(Material.COMPARATOR), "&a儲存設定"));
        player.openInventory(inv);
        updateSpawnDetail(player, "settingMenu");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(settingInfo)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getSlot() == 49) {
                player.closeInventory();
            }
            if (event.getSlot() == 53) {
                openSettingMenu(player);
            }
        }
        if (event.getView().getTitle().equals(settingMenu)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getSlot() >= 10 & event.getSlot() <= 43) {
                if (event.getCurrentItem() != null) {
                    if (event.getClick() == ClickType.RIGHT) {
                        addSpawnChance(player, event.getCurrentItem(), -1, event.getSlot());
                    } else if (event.getClick() == ClickType.SHIFT_RIGHT) {
                        addSpawnChance(player, event.getCurrentItem(), -10, event.getSlot());
                    } else if (event.getClick() == ClickType.LEFT) {
                        addSpawnChance(player, event.getCurrentItem(), 1, event.getSlot());
                    } else if (event.getClick() == ClickType.SHIFT_LEFT) {
                        addSpawnChance(player, event.getCurrentItem(), 10, event.getSlot());
                    }
                }
            }
            if (event.getSlot() == 45) {
                openSettingInfo(player);
            }
            if (event.getSlot() == 53) {
                PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
                if (playerMemory.getPlayerSpawnSettingChanceList().stream().mapToInt(Integer::intValue).sum() == 100) {
                    playerMemory.setPlayerSpawnChanceList(new ArrayList<>(playerMemory.getPlayerSpawnSettingChanceList()));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a已成功修改礦物生成機率!"));
                    player.playSound(player, Sound.BLOCK_ANVIL_USE, 1, 1);
                    player.closeInventory();
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c總機率一定要為100%!"));
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
                }
            }
        }
    }

    private void addSpawnChance(Player player, ItemStack item, int chance, int slot) {
        Material ore = item.getType();
        ItemMeta meta = item.getItemMeta();
        List<String> lores = meta.getLore();
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        if (playerMemory.getPlayerSpawnSettingChanceList().get(oreMaterial.indexOf(ore)) + chance <= playerMemory.getMaxSpawnChanceList().get(oreMaterial.indexOf(ore)) & playerMemory.getMaxSpawnChanceList().get(oreMaterial.indexOf(ore)) != 0) {
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            if (chance > 0) {
                if (playerMemory.getPlayerSpawnSettingChanceList().get(oreMaterial.indexOf(ore)) + chance < playerMemory.getMaxSpawnChanceList().get(oreMaterial.indexOf(ore))) {
                    playerMemory.getPlayerSpawnSettingChanceList().set(oreMaterial.indexOf(ore), playerMemory.getPlayerSpawnSettingChanceList().get(oreMaterial.indexOf(ore)) + chance);
                } else {
                    playerMemory.getPlayerSpawnSettingChanceList().set(oreMaterial.indexOf(ore), playerMemory.getMaxSpawnChanceList().get(oreMaterial.indexOf(ore)));
                }
            } else { //chance < 0
                if (playerMemory.getPlayerSpawnSettingChanceList().get(oreMaterial.indexOf(ore)) + chance > 0) {
                    playerMemory.getPlayerSpawnSettingChanceList().set(oreMaterial.indexOf(ore), playerMemory.getPlayerSpawnSettingChanceList().get(oreMaterial.indexOf(ore)) + chance);
                } else {
                    playerMemory.getPlayerSpawnSettingChanceList().set(oreMaterial.indexOf(ore), 0);
                }
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c此機率無法超過最大可生成機率!"));
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
        }

        lores.set(1, ChatColor.translateAlternateColorCodes('&', "&f生成機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().get(oreMaterial.indexOf(ore)) + "% &8/ " + playerMemory.getMaxSpawnChanceList().get(oreMaterial.indexOf(ore)) + "%"));
        meta.setLore(lores);
        item.setItemMeta(meta);
        player.getOpenInventory().setItem(slot, item);
        updateSpawnDetail(player, "settingMenu");
    }
    private static void updateSpawnDetail(Player player, String invName) {
        if (invName.equalsIgnoreCase("settingInfo")) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            ItemStack info = ItemUtils.getItem(new ItemStack(Material.LEGACY_BOOK_AND_QUILL), "&a場地生成資訊", "", "&7目前礦物生成機率:");
            ItemMeta meta = info.getItemMeta();
            List<String> lores = meta.getLore();
            for (int x=0; x<playerMemory.getPlayerSpawnChanceList().size(); x++) {
                if (playerMemory.getPlayerSpawnChanceList().get(x) > 0) {
                    lores.add(ChatColor.translateAlternateColorCodes('&', "  &f" + oreName.get(x) + ": &a" + playerMemory.getPlayerSpawnChanceList().get(x) + "%"));
                }
            }
            meta.setLore(lores);
            info.setItemMeta(meta);
            player.getOpenInventory().setItem(4, info);
        }
        if (invName.equalsIgnoreCase("settingMenu")) {
            PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
            ItemStack info = ItemUtils.getItem(new ItemStack(Material.LEGACY_BOOK_AND_QUILL), "&a場地生成資訊", "", "&f總機率: &a" + playerMemory.getPlayerSpawnSettingChanceList().stream().mapToInt(Integer::intValue).sum() + "%", "", "&7目前礦物生成機率:");
            ItemMeta meta = info.getItemMeta();
            List<String> lores = meta.getLore();
            for (int x=0; x<playerMemory.getPlayerSpawnSettingChanceList().size(); x++) {
                if (playerMemory.getPlayerSpawnSettingChanceList().get(x) > 0) {
                    lores.add(ChatColor.translateAlternateColorCodes('&', "  &f" + oreName.get(x) + ": &a" + playerMemory.getPlayerSpawnSettingChanceList().get(x) + "%"));
                }
            }
            meta.setLore(lores);
            info.setItemMeta(meta);
            player.getOpenInventory().setItem(4, info);
        }
    }
}
