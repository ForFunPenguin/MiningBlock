package me.forfunpenguin.miningblock;

import me.forfunpenguin.miningblock.Area.Setting;
import me.forfunpenguin.miningblock.Area.Shop;
import me.forfunpenguin.miningblock.Commands.PlayTime;
import me.forfunpenguin.miningblock.Filehandler.AreaDataHandle;
import me.forfunpenguin.miningblock.Filehandler.SpeedConfigFileHandler;
import me.forfunpenguin.miningblock.Listener.*;
import me.forfunpenguin.miningblock.Mining.PacketManager;
import me.forfunpenguin.miningblock.Tasks.DelayedTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiningBlock extends JavaPlugin {

    private static MiningBlock plugin;
    public SpeedConfigFileHandler filehandler;
    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[BreakBlock] &a插件已啟用!"));

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new Scoreboard(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new Shop(), this);
        getServer().getPluginManager().registerEvents(new Setting(), this);
        getServer().getPluginManager().registerEvents(new me.forfunpenguin.miningblock.Commands.MiningBlock(), this);

        filehandler = new SpeedConfigFileHandler(this);
        filehandler.loadBlocks();

        new PacketManager(this);
        new DelayedTask(this);

        plugin.getCommand("miningblock").setExecutor(new me.forfunpenguin.miningblock.Commands.MiningBlock());
        plugin.getCommand("miningblock").setTabCompleter(new me.forfunpenguin.miningblock.Commands.MiningBlock());
        plugin.getCommand("playtime").setExecutor(new PlayTime());
        AreaDataHandle.loadArea();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MiningBlock getPlugin() {
        return plugin;
    }

    public static String getFolderPath() {
        return getPlugin().getDataFolder().getAbsolutePath();
    }
}
