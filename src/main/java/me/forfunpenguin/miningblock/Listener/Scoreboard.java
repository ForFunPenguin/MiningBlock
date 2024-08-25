package me.forfunpenguin.miningblock.Listener;

import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import me.forfunpenguin.miningblock.MiningBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Scoreboard implements Listener{
    public Scoreboard(MiningBlock plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd aHH:mm");
                PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
                if (playerMemory.getPlayerAreaLocation().equalsIgnoreCase("LOBBY")) {
                    player.setScoreboard(scoreboard(player,"&e&lMining Block", "&7" + format.format(now), "", "&f目前位置: &a大廳", "&f線上人數: &a" + Bukkit.getOnlinePlayers().size(), "", "&eForFunPenguin.ddns.net"));
                } else {
                    player.setScoreboard(scoreboard(player,"&e&lMining Block", "&7" + format.format(now), "", "&f硬幣: &6" + playerMemory.getCoin(), "", "&f場地等級: &a" + playerMemory.getAreaLevel(), "&f生成時間: &a" + playerMemory.getRegenTime() + "秒/塊","", "&eForFunPenguin.ddns.net"));
                }
            }
        }, 0L, 20L);

    }

    private org.bukkit.scoreboard.Scoreboard scoreboard(Player p, String title, String ... line) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("test", "dummy", ChatColor.translateAlternateColorCodes('&', title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = new ArrayList<>();
        int counter = 0;
        for (String s : line) {
            if (s == "") {
                String text = "&c";
                for (int space = 0; space < counter + 1; ++space) {
                    text = text + " ";
                }
                lines.add(ChatColor.translateAlternateColorCodes('&', text));
                ++counter;
            } else {
                lines.add(ChatColor.translateAlternateColorCodes('&', s));
            }
        }
        for (int a = 0; a < lines.size(); ++a) {
            Score score = objective.getScore(lines.get(a));
            score.setScore(lines.size() - a);
        }
        p.setScoreboard(scoreboard);
        return scoreboard;
    }
}
