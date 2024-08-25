package me.forfunpenguin.miningblock.Commands;

import me.forfunpenguin.miningblock.Filehandler.PlayerDataHandle;
import me.forfunpenguin.miningblock.Memory.PlayerMemory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayTime implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        PlayerMemory playerMemory = PlayerDataHandle.getPlayerMemory(player.getUniqueId());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a你的總遊戲時數為: " + playerMemory.getPlayTime()));
        return true;
    }
}
