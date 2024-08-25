package me.forfunpenguin.miningblock.Filehandler;

import me.forfunpenguin.miningblock.Area.Pickaxe;
import me.forfunpenguin.miningblock.Memory.AreaMemory;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import me.forfunpenguin.miningblock.MiningBlock;
import me.forfunpenguin.miningblock.Utils.ItemUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AreaDataHandle {

    public static final Map<String, AreaMemory> areaMemory = new HashMap<>();

    private static List<Material> oreMaterial= Arrays.asList(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.COPPER_ORE, Material.GOLD_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.DEEPSLATE, Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_COPPER_ORE, Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.OBSIDIAN, Material.COAL_BLOCK, Material.IRON_BLOCK, Material.COPPER_BLOCK, Material.GOLD_BLOCK, Material.REDSTONE_BLOCK, Material.LAPIS_BLOCK, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK);

    public static AreaMemory getAreaMemory(String AreaDisplayName) {
        if (!areaMemory.containsKey(AreaDisplayName)) {
            AreaMemory m = new AreaMemory();
            areaMemory.put(AreaDisplayName, m);
            return m;
        }
        return areaMemory.get(AreaDisplayName);
    }

    public static void setAreaMemory(String AreaDisplayName, AreaMemory memory) {
        if (memory == null) areaMemory.remove(AreaDisplayName);
        else areaMemory.put(AreaDisplayName, memory);
    }

    public static Boolean hasAreaMemory(String AreaDisplayName) {
        return areaMemory.containsKey(AreaDisplayName);
    }

    public static void loadArea() {
        File dataFolder = new File(MiningBlock.getFolderPath() + "/Area/");
        for(File file : dataFolder.listFiles()) {
            if(file.getName().endsWith("yml")) {
                FileConfiguration areaData = YamlConfiguration.loadConfiguration(file);
                AreaMemory memory = new AreaMemory();
                memory.setPos1((areaData.getLocation("Pos1")));
                memory.setPos2(areaData.getLocation("Pos2"));
                memory.setAreaDisplayname(areaData.getString("AreaDisplayname"));
                memory.setAreaStatus("AVAILABLE");
                setAreaMemory(memory.getAreaDisplayname(), memory);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', memory.getAreaDisplayname() + "已成功載入"));
            }
        }
    }

    public static void joinArea(String AreaDisplayName, Player player) {
        AreaMemory areaMemory = getAreaMemory(ChatColor.stripColor(AreaDisplayName));
        areaMemory.setAreaStatus("UNAVAILABLE");
        setAreaMemory(AreaDisplayName, areaMemory);
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        playerMemory.setPlayerAreaLocation(AreaDisplayName);
        PlayerDataHandle.setPlayerMemory(player.getUniqueId(), playerMemory);
        loadBlock(player); //載入上次儲存的方塊位置
        getCanRegenBlock(AreaDisplayName, player); //儲存可生成方塊的位置資訊
        startTImer(player); //啟動方塊生成計時器
        player.getInventory().clear();
        Pickaxe.givePickaxe(player);
        player.getInventory().setItem(5, ItemUtils.getItem(new ItemStack(Material.CHEST), "&a礦物儲物箱"));
        player.getInventory().setItem(6, ItemUtils.getItem(new ItemStack(Material.EMERALD), "&a商店"));
        player.getInventory().setItem(7, ItemUtils.getItem(new ItemStack(Material.WRITTEN_BOOK), "&a任務"));
        player.getInventory().setItem(8, ItemUtils.getItem(new ItemStack(Material.COMPARATOR), "&a生成設定"));
        player.teleport(new Location(Bukkit.getWorld("world"), -406.5, 109, 219.5, 180, 0));
    }

    public static void resetBlock(String AreaDisplayName) {
        AreaMemory areaMemory = getAreaMemory(ChatColor.stripColor(AreaDisplayName));
        double x1, x2, y1, y2, z1, z2;
        if (areaMemory.getPos1().getX() < areaMemory.getPos2().getX()) {
            x1 = areaMemory.getPos1().getX();
            x2 = areaMemory.getPos2().getX();
        } else {
            x2 = areaMemory.getPos1().getX();
            x1 = areaMemory.getPos2().getX();
        }
        if (areaMemory.getPos1().getY() < areaMemory.getPos2().getY()) {
            y1 = areaMemory.getPos1().getY();
            y2 = areaMemory.getPos2().getY();
        } else {
            y2 = areaMemory.getPos1().getY();
            y1 = areaMemory.getPos2().getY();
        }
        if (areaMemory.getPos1().getZ() < areaMemory.getPos2().getZ()) {
            z1 = areaMemory.getPos1().getZ();
            z2 = areaMemory.getPos2().getZ();
        } else {
            z2 = areaMemory.getPos1().getZ();
            z1 = areaMemory.getPos2().getZ();
        }
        Location loc = new Location(Bukkit.getWorld("world"), x1, y1 - 1, z1);
        for (double x = x1; x <= x2; x++) {
            loc.setX(x);
            for (double z = z1; z <= z2; z++) {
                loc.setZ(z);
                loc.getBlock().setType(Material.BARRIER);
            }
        }
    }

    public static void loadBlock(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        if (playerMemory.getAreaBlockList() != null) {
            List<Material> blockList = StringListToMaterial(playerMemory.getAreaBlockList());
            AreaMemory areaMemory = getAreaMemory(playerMemory.getPlayerAreaLocation());
            double x1, x2, y1, y2, z1, z2;
            if (areaMemory.getPos1().getX() < areaMemory.getPos2().getX()) {
                x1 = areaMemory.getPos1().getX();
                x2 = areaMemory.getPos2().getX();
            } else {
                x2 = areaMemory.getPos1().getX();
                x1 = areaMemory.getPos2().getX();
            }
            if (areaMemory.getPos1().getY() < areaMemory.getPos2().getY()) {
                y1 = areaMemory.getPos1().getY();
                y2 = areaMemory.getPos2().getY();
            } else {
                y2 = areaMemory.getPos1().getY();
                y1 = areaMemory.getPos2().getY();
            }
            if (areaMemory.getPos1().getZ() < areaMemory.getPos2().getZ()) {
                z1 = areaMemory.getPos1().getZ();
                z2 = areaMemory.getPos2().getZ();
            } else {
                z2 = areaMemory.getPos1().getZ();
                z1 = areaMemory.getPos2().getZ();
            }
            Location loc = new Location(Bukkit.getWorld("world"), x1, y1 - 1, z1);
            int count = 0;
            for (double x = x1; x <= x2; x++) {
                loc.setX(x);
                for (double z = z1; z <= z2; z++) {
                    loc.setZ(z);
                    loc.getBlock().setType(blockList.get(count));
                    count++;
                }
            }
        }
    }

    public static void saveBlock(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        if (!playerMemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY")) {
            AreaMemory areaMemory = getAreaMemory(playerMemory.getPlayerAreaLocation());
            double x1, x2, y1, y2, z1, z2;
            if (areaMemory.getPos1().getX() < areaMemory.getPos2().getX()) {
                x1 = areaMemory.getPos1().getX();
                x2 = areaMemory.getPos2().getX();
            } else {
                x2 = areaMemory.getPos1().getX();
                x1 = areaMemory.getPos2().getX();
            }
            if (areaMemory.getPos1().getY() < areaMemory.getPos2().getY()) {
                y1 = areaMemory.getPos1().getY();
                y2 = areaMemory.getPos2().getY();
            } else {
                y2 = areaMemory.getPos1().getY();
                y1 = areaMemory.getPos2().getY();
            }
            if (areaMemory.getPos1().getZ() < areaMemory.getPos2().getZ()) {
                z1 = areaMemory.getPos1().getZ();
                z2 = areaMemory.getPos2().getZ();
            } else {
                z2 = areaMemory.getPos1().getZ();
                z1 = areaMemory.getPos2().getZ();
            }
            Location loc = new Location(Bukkit.getWorld("world"), x1, y1 - 1, z1);
            List<String> blockList = new ArrayList<>();
            for (double x = x1; x <= x2; x++) {
                loc.setX(x);
                for (double z = z1; z <= z2; z++) {
                    loc.setZ(z);
                    blockList.add(loc.getBlock().getType().toString());
                }
            }
            playerMemory.setAreaBlockList(blockList);
            PlayerDataHandle.setPlayerMemory(player.getUniqueId(), playerMemory);
        }
    }

    private static List<Material> StringListToMaterial(List<String> stringList) {
        List<Material> materialList = new ArrayList<>();
        for (String material : stringList) {
            materialList.add(Material.getMaterial(material));
        }
        return materialList;
    }

    public static void startTImer(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        int taskID;
        taskID = MiningBlock.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(MiningBlock.getPlugin(), () -> {
            if (!playerMemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY")) {
                double regenTimer = playerMemory.getRegenTimer();
                if (regenTimer >= playerMemory.getRegenTime()) {
                    regenBlock(player);
                    regenTimer = regenTimer - playerMemory.getRegenTime();
                }
                regenTimer = regenTimer + 0.1;
                playerMemory.setRegenTimer(regenTimer);
                PlayerDataHandle.setPlayerMemory(player.getUniqueId(), playerMemory);
            }
        }, 0L, 2L); //2L = 0.1s
        playerMemory.setTimerTask(taskID);
        PlayerDataHandle.setPlayerMemory(player.getUniqueId(), playerMemory);
    }

    public static void regenBlock(Player player) {
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        if (playerMemory.getAreaCanRegenBlockList().size() > 0) {
            Random r = new Random();
            int randomBlock = r.nextInt(playerMemory.getAreaCanRegenBlockList().size());
            //playerMemory.getAreaCanRegenBlockList().get(randomBlock).setType(Material.STONE);
            Random random = new Random();
            //playerMemory.setRandomNumber(random.nextInt(100) + 1);
            int randomNumber = random.nextInt(100) + 1;
            player.sendMessage("隨機機率: " + randomNumber);
            for (int x = 0; x < playerMemory.getPlayerSpawnChanceList().size(); x++) {
                if (randomNumber <= playerMemory.getPlayerSpawnChanceList().get(x)) { //表示隨機選種該種方塊
                    playerMemory.getAreaCanRegenBlockList().get(randomBlock).setType(oreMaterial.get(x));
                    playerMemory.getAreaCanRegenBlockList().remove(randomBlock);
                    break;
                } else {
                    randomNumber = randomNumber - playerMemory.getPlayerSpawnChanceList().get(x);
                }
            }
        }
    }

    public static void getCanRegenBlock(String AreaDisplayName, Player player) {
        AreaMemory areaMemory = getAreaMemory(ChatColor.stripColor(AreaDisplayName));
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        double x1, x2, y1, y2, z1, z2;
        if (areaMemory.getPos1().getX() < areaMemory.getPos2().getX()) {
            x1 = areaMemory.getPos1().getX();
            x2 = areaMemory.getPos2().getX();
        } else {
            x2 = areaMemory.getPos1().getX();
            x1 = areaMemory.getPos2().getX();
        }
        if (areaMemory.getPos1().getY() < areaMemory.getPos2().getY()) {
            y1 = areaMemory.getPos1().getY();
            y2 = areaMemory.getPos2().getY();
        } else {
            y2 = areaMemory.getPos1().getY();
            y1 = areaMemory.getPos2().getY();
        }
        if (areaMemory.getPos1().getZ() < areaMemory.getPos2().getZ()) {
            z1 = areaMemory.getPos1().getZ();
            z2 = areaMemory.getPos2().getZ();
        } else {
            z2 = areaMemory.getPos1().getZ();
            z1 = areaMemory.getPos2().getZ();
        }
        Location loc = new Location(Bukkit.getWorld("world"), x1, y1 - 1, z1);
        List<Block> canRegenBlockList = new ArrayList<>();
        for (double x = x1; x <= x2; x++) {
            loc.setX(x);
            for (double z = z1; z <= z2; z++) {
                loc.setZ(z);
                if (loc.getBlock().getType().equals(Material.BARRIER)) {
                    canRegenBlockList.add(loc.getBlock());
                }
            }
        }
        playerMemory.setAreaCanRegenBlockList(canRegenBlockList);
    }
}
