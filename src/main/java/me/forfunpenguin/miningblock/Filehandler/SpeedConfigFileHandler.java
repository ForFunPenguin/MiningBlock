package me.forfunpenguin.miningblock.Filehandler;

import me.forfunpenguin.miningblock.Memory.BlockMemory;
import me.forfunpenguin.miningblock.MiningBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class SpeedConfigFileHandler {
    private File blockSaveFile;
    private YamlConfiguration config = new YamlConfiguration();
    private MiningBlock plugin;

    private static final Map<String, BlockMemory> blockmemory = new HashMap<>();

    public static BlockMemory getBlockMemory(String blockType) {
        if (!blockmemory.containsKey(blockType)) {
            BlockMemory m = new BlockMemory();
            blockmemory.put(blockType, m);
            return m;
        }
        return blockmemory.get(blockType);
    }

    public static void setBlockMemory(String blockType, BlockMemory memory) {
        if (memory == null) blockmemory.remove(blockType);
        else blockmemory.put(blockType, memory);
    }

    public static Boolean hasBlockMemory(String blockType) {
        return blockmemory.containsKey(blockType);
    }

    public void loadBlocks() {
        File dataFolder = new File(MiningBlock.getFolderPath() + "/Blocks/");
        //plugin.getLogger().info("資料夾位置: " + dataFolder);
        File[] files = dataFolder.listFiles((pathname) -> pathname.getName().endsWith(".yaml"));
        //plugin.getLogger().info("Blocks數量: " + files.length);
        for(File file : dataFolder.listFiles()) {
            if(file.getName().endsWith("yml")) {
                FileConfiguration blockData = YamlConfiguration.loadConfiguration(file);
                BlockMemory memory = new BlockMemory();
                memory.setItemType((blockData.getString("ItemType")));
                memory.setHardness(blockData.getDouble("Hardness"));
                memory.setBreakPower(blockData.getInt("BreakPower"));
                SpeedConfigFileHandler.setBlockMemory(blockData.getString("ItemType"), memory);
                plugin.getLogger().info(blockData.getString("ItemType") + "已成功載入");


                BlockMemory test = SpeedConfigFileHandler.getBlockMemory(blockData.getString("ItemType"));
                //plugin.getLogger().info("ItemType:" + test.getItemType());
                //plugin.getLogger().info("Hardness:" + test.getHardness());
                //plugin.getLogger().info("DropItem:" + test.getDropItem());
                //plugin.getLogger().info("DropAmount:" + test.getDropAmount());
                //plugin.getLogger().info("BreakPower:" + test.getBreakPower());
            }
        }
    }

    public static void saveMemory() {
        blockmemory.forEach((blockName, memory) -> {
            File file = new File(MiningBlock.getFolderPath() + "/Blocks/" + blockName +  ".yml");
            FileConfiguration blockData = YamlConfiguration.loadConfiguration(file);
            blockData.set("ItemType", memory.getItemType());
            blockData.set("Hardness", memory.getHardness());
            blockData.set("BreakPower", memory.getBreakPower());
            try {
                blockData.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[BreakBlock] &aBlocks已儲存!"));
    }

    public SpeedConfigFileHandler(MiningBlock plugin) {
        this.plugin = plugin;
        initialiseConfig();
    }

    private void initialiseConfig() {
        blockSaveFile = new File(plugin.getDataFolder().toString(), "SpeedConfig.yml");

        if (!blockSaveFile.exists()) {
            blockSaveFile.getParentFile().mkdirs();
            try {
                blockSaveFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "[CriticalMiningTech] An error occured while creating SpeedConfig.yml!");
                return;
            }
        }

        try {
            config.load(blockSaveFile);
        } catch (IOException | InvalidConfigurationException e1) {
            Bukkit.getLogger().log(Level.SEVERE, "[CriticalMiningTech] An error occured while loading SpeedConfig.yml!");
            return;
        }

        System.out.println("[CriticalMiningTech] SpeedConfig.yml loaded successfully");
    }


    public YamlConfiguration getBlockSaveFile() {
        return this.config;
    }

    public boolean addBlockToConfig(Block block, double amount) {
        if (config.getConfigurationSection("Speeds") == null) {
            config.createSection("Speeds");
        }


        config.set("Speeds." + block.getType().toString(), amount);

        try {
            config.save(blockSaveFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[CriticalMiningTech] An error occured while saving SpeedConfig.yml!");
            return false;
        }

        System.out.println("[CriticalMiningTech] SpeedConfig.yml saved successfully");
        return true;
    }

    public boolean removeBlockFromConfig(Block block) {
        if (config.getConfigurationSection("Speeds") == null) {
            return true;
        }

        config.set("Speeds." + block.getType().toString(), null);

        try {
            config.save(blockSaveFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[CriticalMiningTech] An error occured while saving SpeedConfig.yml!");
            return false;
        }

        System.out.println("[CriticalMiningTech] SpeedConfig.yml saved successfully");
        return true;

    }

    public YamlConfiguration getConfig() {
        return this.config;
    }
}
