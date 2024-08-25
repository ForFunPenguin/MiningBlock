package me.forfunpenguin.miningblock.Commands;

import me.forfunpenguin.miningblock.Filehandler.AreaDataHandle;
import me.forfunpenguin.miningblock.Memory.GuiMemory;
import me.forfunpenguin.miningblock.Tasks.DelayedTask;
import me.forfunpenguin.miningblock.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiningBlock implements Listener, TabExecutor {

    private static final Map<Player, GuiMemory> guimemory = new HashMap<>();
    private static String createMenu = "創建場地";
    private static String joinMenu = "選擇場地";
    private int counter;
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> temp = new ArrayList<String>(Arrays.asList(
                    "create",
                    "list",
                    "join"));
            return temp;
        }
        return null;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        switch (args[0]) {
            case "create":
                openCreateMenu(player);
                return true;
            case "join":
                openJoinMenu(player);
                return true;
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(createMenu)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getSlot() == 10) {
                GuiMemory m = guimemory.get(player);
                Location loc = player.getLocation();
                loc.setX(Math.floor(loc.getX()) + 0.5);
                loc.setZ(Math.floor(loc.getZ()) + 0.5);
                m.setPos1(loc);
                guimemory.put(player, m);
                player.getOpenInventory().setItem(10, ItemUtils.getItem(new ItemStack(Material.COMPASS), "&a第一個位置: " + getLocationString(m.getPos1()), "", "&a已設定"));
            }
            if (event.getSlot() == 11) {
                GuiMemory m = guimemory.get(player);
                Location loc = player.getLocation();
                loc.setX(Math.floor(loc.getX()) + 0.5);
                loc.setZ(Math.floor(loc.getZ()) + 0.5);
                m.setPos2(loc);
                guimemory.put(player, m);
                player.getOpenInventory().setItem(11, ItemUtils.getItem(new ItemStack(Material.COMPASS), "&a第二個位置: " + getLocationString(m.getPos2()), "", "&a已設定"));
            }
            if (event.getSlot() == 12) {
                GuiMemory memory = guimemory.get(player);
                memory.setNameTyping("1");
                guimemory.put(player, memory);
                player.closeInventory();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a輸入你想設定的場地名稱, 30秒後將自動取消輸入!"));
                new DelayedTask(() -> {
                    GuiMemory m = guimemory.get(player);
                    if (m.getNameTyping() == "1") {
                        m.setNameTyping("0");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c已取消場地名稱輸入!"));
                    }
                    guimemory.put(player, m);
                }, 20 * 30); //20 ticks * 30s
            }
            if (event.getSlot() == 13) {
                GuiMemory m = guimemory.get(player);
                if (m.getPos1() != null & m.getPos2() != null & m.getAreaDisplayname() != null) {
                    guimemory.forEach((playerName, memory) -> {
                        File file = new File(me.forfunpenguin.miningblock.MiningBlock.getFolderPath() + "/Area/" + memory.getAreaDisplayname() +  ".yml");
                        FileConfiguration areaData = YamlConfiguration.loadConfiguration(file);
                        areaData.set("Pos1", memory.getPos1());
                        areaData.set("Pos2", memory.getPos2());
                        areaData.set("AreaDisplayname", memory.getAreaDisplayname());
                        try {
                            areaData.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[MiningBLock] &a區域已儲存!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a區域已成功儲存!"));
                }
            }
            if (event.getSlot() == 14) {
                GuiMemory m = new GuiMemory();
                guimemory.put(player, m);
                player.getOpenInventory().setItem(10, ItemUtils.getItem(new ItemStack(Material.COMPASS), "&a第一個位置: &c尚未設定", "", "&e點擊後將記錄目前位置!"));
                player.getOpenInventory().setItem(11, ItemUtils.getItem(new ItemStack(Material.COMPASS), "&a第二個位置: &c尚未設定", "", "&e點擊後將記錄目前位置!"));
                player.getOpenInventory().setItem(12, ItemUtils.getItem(new ItemStack(Material.NAME_TAG), "&a場地名稱: &c尚未設定", "", "&e點擊設定名稱!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a已重置所有設定!"));
            }
        }
        if (event.getView().getTitle().equals(joinMenu)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem().getType().equals(Material.GREEN_TERRACOTTA)) {
                player.closeInventory();
                AreaDataHandle.joinArea(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()), player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7正在傳送至" + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()) + "..."));
            }
        }
    }

    public static void openCreateMenu(Player player) {
        GuiMemory m;
        if (!guimemory.containsKey(player)) {
            m = new GuiMemory();
            guimemory.put(player, m);
        } else {
            m = guimemory.get(player);
            m.setNameTyping("0");
            guimemory.put(player, m);
        }
        Inventory inv = Bukkit.createInventory(player, 9*6, createMenu);

        if (m.getPos1() == null) {
            inv.setItem(10, ItemUtils.getItem(new ItemStack(Material.COMPASS), "&a第一個位置: &c尚未設定", "", "&e點擊後將記錄目前位置!"));
        } else {
            inv.setItem(10, ItemUtils.getItem(new ItemStack(Material.COMPASS), "&a第一個位置: " + getLocationString(m.getPos1()), "", "&a已設定"));
        }
        if (m.getPos2() == null) {
            inv.setItem(11, ItemUtils.getItem(new ItemStack(Material.COMPASS), "&a第二個位置: &c尚未設定", "", "&e點擊後將記錄目前位置!"));
        } else {
            inv.setItem(11, ItemUtils.getItem(new ItemStack(Material.COMPASS), "&a第二個位置: " + getLocationString(m.getPos2()), "", "&a已設定"));
        }
        if (m.getAreaDisplayname() == null) {
            inv.setItem(12, ItemUtils.getItem(new ItemStack(Material.NAME_TAG), "&a場地名稱: &c尚未設定", "", "&e點擊設定名稱!"));
        } else {
            inv.setItem(12, ItemUtils.getItem(new ItemStack(Material.NAME_TAG), "&a場地名稱: " + m.getAreaDisplayname()));
        }

        inv.setItem(13, ItemUtils.getItem(new ItemStack(Material.LIME_WOOL), "&a創建場地"));
        inv.setItem(14, ItemUtils.getItem(new ItemStack(Material.BEDROCK), "&c重置設定"));

        player.openInventory(inv);
    }

    public void openJoinMenu(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9*6, joinMenu);
        counter = 0;
        AreaDataHandle.areaMemory.forEach((AreaDisplayName, memory) -> {
            if (memory.getAreaStatus() == "AVAILABLE") {
                inv.setItem(counter, ItemUtils.getItem(new ItemStack(Material.GREEN_TERRACOTTA), "&a" + memory.getAreaDisplayname(), "&f狀態: &a可使用", "", "&e&l點擊加入!"));
            } else {
                inv.setItem(counter, ItemUtils.getItem(new ItemStack(Material.RED_TERRACOTTA), "&c" + memory.getAreaDisplayname(), "&f狀態: &c使用中", "", "&c&l無法加入!"));
            }
            counter++;
        });

        player.openInventory(inv);
    }
    public static String getLocationString(Location loc) {
        return "X: " + loc.getX() + " Y: " + loc.getY() + " Z: " + loc.getZ();
    }

    @EventHandler
    public void onPlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        GuiMemory guiMemory = guimemory.get(player);
        if (guiMemory.getNameTyping() == "1") {
            event.setCancelled(true);
            if (checkWorldName(event.getMessage()) == true) {
                guiMemory.setNameTyping("0");
                guiMemory.setAreaDisplayname(event.getMessage());
                guimemory.put(player, guiMemory);
                openCreateMenu(player);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c名稱必須為 a-z0-9/._-"));
            }
        }
    }

    public static boolean checkWorldName(String worldName) {
        String regEx = "[a-z0-9/._-]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(worldName);
        return m.find();
    }
}
