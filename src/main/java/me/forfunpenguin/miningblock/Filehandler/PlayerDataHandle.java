package me.forfunpenguin.miningblock.Filehandler;

import me.forfunpenguin.miningblock.Memory.AreaMemory;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import me.forfunpenguin.miningblock.MiningBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerDataHandle {
    private static final Map<UUID, PlayerMemory> playerMemory = new HashMap<>();

    public static PlayerMemory getPlayerMemory(UUID playerUUID) {
        if (!playerMemory.containsKey(playerUUID)) {
            PlayerMemory m = new PlayerMemory();
            playerMemory.put(playerUUID, m);
            return m;
        }
        return playerMemory.get(playerUUID);
    }

    public static void setPlayerMemory(UUID playerUUID, PlayerMemory memory) {
        if (memory == null) playerMemory.remove(playerUUID);
        else playerMemory.put(playerUUID, memory);
    }

    public static Boolean hasPlayerMemory(UUID playerUUID) {
        return playerMemory.containsKey(playerUUID);
    }

    public static void loadPlayerData(UUID playerUUID) {
        File file = new File(MiningBlock.getFolderPath() + "/PlayerData/Area/" + playerUUID +  ".yml");
        if (file.exists()) {
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(file);
            PlayerMemory playermemory = new PlayerMemory();
            playermemory.setAreaLevel(playerData.getInt("AreaLevel"));
            playermemory.setRegenTime(playerData.getDouble("RegenTime"));
            playermemory.setPlayTime(playerData.getInt("PlayTime"));
            playermemory.setAreaBlockList(playerData.getStringList("BlockList"));
            playermemory.setPlayerAreaLocation("LOBBY");
            playermemory.setRegenTimer(playerData.getDouble("RegenTimer"));
            playermemory.setCobblestoneAmountList((List<Integer>) playerData.getList("CobblestoneAmount"));
            playermemory.setCoalAmountList((List<Integer>) playerData.getList("CoalAmount"));
            playermemory.setIronAmountList((List<Integer>) playerData.getList("IronAmount"));
            playermemory.setCopperAmountList((List<Integer>) playerData.getList("CopperAmount"));
            playermemory.setGoldAmountList((List<Integer>) playerData.getList("GoldAmount"));
            playermemory.setRedstoneAmountList((List<Integer>) playerData.getList("RedstoneAmount"));
            playermemory.setLapisAmountList((List<Integer>) playerData.getList("LapisAmount"));
            playermemory.setDiamondAmountList((List<Integer>) playerData.getList("DiamondAmount"));
            playermemory.setEmeraldAmountList((List<Integer>) playerData.getList("EmeraldAmount"));
            playermemory.setCoin(playerData.getInt("Coin"));
            playermemory.setMiningSpeed(playerData.getInt("MiningSpeed"));
            playermemory.setBreakPower(playerData.getInt("BreakPower"));
            playermemory.setToolMaterial(playerData.getString("ToolMaterial"));
            playermemory.setMaxSpawnChanceList((List<Integer>) playerData.getList("MaxSpawnChance"));
            playermemory.setPlayerSpawnChanceList((List<Integer>) playerData.getList("PlayerSpawnChance"));
            playermemory.setPlayerOreFoundList((List<Integer>) playerData.getList("PlayerOreFound"));
            setPlayerMemory(playerUUID, playermemory);
        } else {
            PlayerMemory playermemory = new PlayerMemory();
            playermemory.setAreaLevel(0);
            playermemory.setRegenTime(5);
            playermemory.setPlayTime(0);
            playermemory.setPlayerAreaLocation("LOBBY");
            playermemory.setRegenTimer(0);
            //playermemory.setAreaBlockList(Arrays.asList(null));
            playermemory.setCobblestoneAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setCoalAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setIronAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setCopperAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setGoldAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setRedstoneAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setLapisAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setDiamondAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setEmeraldAmountList(Arrays.asList(0, 0, 0, 0, 0, 0));
            playermemory.setCoin(0);
            playermemory.setMiningSpeed(100);
            playermemory.setBreakPower(1);
            playermemory.setToolMaterial("Wooden");
            playermemory.setPlayerSpawnChanceList(Arrays.asList(100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            playermemory.setMaxSpawnChanceList(Arrays.asList(100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            playermemory.setPlayerOreFoundList(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            setPlayerMemory(playerUUID, playermemory);
        }
    }

    public static void savePlayerData(UUID playerUUID) {
        AreaDataHandle.saveBlock(Bukkit.getPlayer(playerUUID)); //先執行 因為這邊只修改數值 需要再抓數值前執行
        PlayerMemory playermemory = getPlayerMemory(playerUUID);
        File file = new File(MiningBlock.getFolderPath() + "/PlayerData/Area/" + playerUUID +  ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(file);
        playerData.set("AreaLevel", playermemory.getAreaLevel());
        playerData.set("RegenTime", playermemory.getRegenTime());
        playerData.set("PlayTime", playermemory.getPlayTime());
        playerData.set("PlayerAreaLocation", playermemory.getPlayerAreaLocation());
        playerData.set("BlockList", playermemory.getAreaBlockList());
        playerData.set("RegenTimer", playermemory.getRegenTimer());
        playerData.set("CobblestoneAmount", playermemory.getCobblestoneAmountList());
        playerData.set("CoalAmount", playermemory.getCoalAmountList());
        playerData.set("IronAmount", playermemory.getIronAmountList());
        playerData.set("CopperAmount", playermemory.getCopperAmountList());
        playerData.set("GoldAmount", playermemory.getGoldAmountList());
        playerData.set("RedstoneAmount", playermemory.getRedstoneAmountList());
        playerData.set("LapisAmount", playermemory.getLapisAmountList());
        playerData.set("DiamondAmount", playermemory.getDiamondAmountList());
        playerData.set("EmeraldAmount", playermemory.getEmeraldAmountList());
        playerData.set("Coin", playermemory.getCoin());
        playerData.set("MiningSpeed", playermemory.getMiningSpeed());
        playerData.set("BreakPower", playermemory.getBreakPower());
        playerData.set("ToolMaterial", playermemory.getToolMaterial());
        playerData.set("MaxSpawnChance", playermemory.getMaxSpawnChanceList());
        playerData.set("PlayerSpawnChance", playermemory.getPlayerSpawnChanceList());
        playerData.set("PlayerOreFound", playermemory.getPlayerOreFoundList());
        try {
            playerData.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!playermemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY")) {
            Bukkit.getServer().getScheduler().cancelTask(playermemory.getTimerTask());  //中斷行程
            AreaMemory areaMemory = AreaDataHandle.getAreaMemory(playermemory.getPlayerAreaLocation());
            areaMemory.setAreaStatus("AVAILABLE");
            AreaDataHandle.setAreaMemory(playermemory.getPlayerAreaLocation(), areaMemory);
            AreaDataHandle.resetBlock(playermemory.getPlayerAreaLocation());
        }
        setPlayerMemory(playerUUID, null);
    }
}
