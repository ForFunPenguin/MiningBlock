package me.forfunpenguin.miningblock.Memory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

public class PlayerMemory {
    @Getter
    @Setter
    private int areaLevel; //區域等級

    @Getter
    @Setter
    private double regenTime; //方塊重生時間

    @Getter
    @Setter
    private int playTime; //遊玩時間

    @Getter
    @Setter
    private String playerAreaLocation; //玩家位置(哪個區域or伺服器)

    @Getter
    @Setter
    private List<String> areaBlockList; //紀錄區域內的所有方塊 玩家離線前會儲存 下次登入時會載入

    @Getter
    @Setter
    private List<Block> areaCanRegenBlockList;

    @Getter
    @Setter
    private double regenTimer;

    @Getter
    @Setter
    private int timerTask;

    @Getter
    @Setter
    private List<Integer> cobblestoneAmountList;

    @Getter
    @Setter
    private List<Integer> coalAmountList;

    @Getter
    @Setter
    private List<Integer> ironAmountList;

    @Getter
    @Setter
    private List<Integer> copperAmountList;

    @Getter
    @Setter
    private List<Integer> goldAmountList;

    @Getter
    @Setter
    private List<Integer> redstoneAmountList;

    @Getter
    @Setter
    private List<Integer> lapisAmountList;

    @Getter
    @Setter
    private List<Integer> diamondAmountList;

    @Getter
    @Setter
    private List<Integer> emeraldAmountList;

    @Getter
    @Setter
    private List<Integer> obsidianAmountList;

    @Getter
    @Setter
    private int coin;

    @Getter
    @Setter
    private int miningSpeed;

    @Getter
    @Setter
    private int breakPower;

    @Getter
    @Setter
    private String toolMaterial;

    @Getter
    @Setter
    private List<Integer> playerSpawnChanceList;

    @Getter
    @Setter
    private List<Integer> maxSpawnChanceList;

    @Getter
    @Setter
    private List<Integer> playerOreFoundList;

    @Getter
    @Setter
    private List<Integer> playerSpawnSettingChanceList;
}
