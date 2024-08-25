package me.forfunpenguin.miningblock.Mining;

import java.util.HashMap;

import me.forfunpenguin.miningblock.Area.BlockHandle;
import me.forfunpenguin.miningblock.Memory.BlockMemory;
import me.forfunpenguin.miningblock.Filehandler.SpeedConfigFileHandler;
import me.forfunpenguin.miningblock.MiningBlock;
import me.forfunpenguin.miningblock.Utils.NBTUtils;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

public class BlockDamage {
    private MiningBlock plugin;

    private static HashMap<String, ScheduleTask> scheduleId = new HashMap<String, ScheduleTask>();

    private static ProtocolManager manager;

    public BlockDamage(MiningBlock plugin) {
        this.plugin = plugin;
        manager = ProtocolLibrary.getProtocolManager();
    }


    protected void configureBreakingPacket(double hardness, Player player, Block block) {
        player.sendMessage("修改封包");
        PacketContainer breakingAnimation = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

        // this enusres that the player wont conflict with another player's breaking animation
        int entityId = player.getEntityId() + 1;
        entityId = entityId * 1000;


        breakingAnimation.getIntegers().write(0, entityId);
        breakingAnimation.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));

        breakingTimeCheck(hardness, player, block, breakingAnimation);

    }

    private void breakingTimeCheck(double hardness, Player player, Block block, PacketContainer breakingAnimation) {
        player.sendMessage("判定破壞時間");
        double breakingTimeTicks = getBreakingTime(hardness, player, block);

        // check if the breakingTime is instant
        if (breakingTimeTicks == 0) {
            playerBreakBlock(player, block);
            player.sendMessage(block.getType().toString());
            if (block.getType().toString().equals("COBBLESTONE")) {
                BlockMemory blockMemory = SpeedConfigFileHandler.getBlockMemory(block.getType().toString());
                configureBreakingPacket(blockMemory.getHardness(), player, block);
            }
            return;
        }
        if (breakingTimeTicks <= 10000) {
            player.sendMessage("breakingTimeTicks: " + breakingTimeTicks);
            startBreaking(player, breakingAnimation, breakingTimeTicks, block);
        }
    }

    private void startBreaking(Player player, PacketContainer breakingAnimation, double breakingTimeTicks, Block originalBlock) {
        player.sendMessage("開始挖掘");
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            double currentTicks = 0d;

            @Override
            public void run() {

                // stops breaking if player isn't actively breaking the block
                if (!(PacketManager.armSwinging.containsKey(player.getName()))) {
                    player.sendMessage("目標方塊變更 取消挖掘");
                    Bukkit.getScheduler().cancelTask(scheduleId.get(player.getName()).taskId);
                    scheduleId.remove(player.getName());

                    // returns the breaking animation back to none
                    breakingAnimation.getIntegers().write(1, -1);
                    try {
                        manager.sendServerPacket(player, breakingAnimation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }

                Block currentTarget = player.getTargetBlockExact(5);

                // removes any progress if mining from block onto air and cancels this task
                if (currentTarget == null) {
                    player.sendMessage("目標缺失 取消挖掘");
                    Bukkit.getScheduler().cancelTask(scheduleId.get(player.getName()).taskId);
                    scheduleId.remove(player.getName());

                    // returns the breaking animation back to none
                    breakingAnimation.getIntegers().write(1, -1);
                    try {
                        manager.sendServerPacket(player, breakingAnimation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }

                // breaks the block if it has been mined for a succificnet amount of time
                if(currentTicks >= breakingTimeTicks) {
                    player.sendMessage("挖掘時間超過");
                    // sets the final breaking animation
                    breakingAnimation.getIntegers().write(1, -1);
                    try {
                        manager.sendServerPacket(player, breakingAnimation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    playerBreakBlock(player, originalBlock);
                    if (originalBlock.getType().toString().equals("COBBLESTONE")) {
                        BlockMemory blockMemory = SpeedConfigFileHandler.getBlockMemory("COBBLESTONE");
                        //originalBlock.setType(Material.COBBLESTONE);
                        currentTicks = currentTicks - getBreakingTime(blockMemory.getHardness(), player, originalBlock);
                    } else {
                        Bukkit.getScheduler().cancelTask(scheduleId.get(player.getName()).taskId);
                        scheduleId.remove(player.getName());
                    }
                    return;
                } else {
                    player.sendMessage("正在挖掘");
                    double multiplier = 0.1;
                    for (int x=0; x <= 9; x++) {
                        player.sendMessage(String.valueOf("挖掘時間:" + currentTicks));
                        player.sendMessage(String.valueOf("需要挖掘時間:" + breakingTimeTicks));
                        if (currentTicks <= (breakingTimeTicks * multiplier)) {
                            breakingAnimation.getIntegers().write(1, x-1);
                            try {
                                manager.sendServerPacket(player, breakingAnimation);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        multiplier += 0.1;
                    }
                }

                currentTicks = currentTicks + 1;
            }
        },0L, 1L);

        scheduleId.put(player.getName(), new ScheduleTask(taskId, originalBlock));
    }

    public static void cancelTaskWithBlockReset(Player player) {
        if (scheduleId.containsKey(player.getName())) {
            Block block = scheduleId.get(player.getName()).block;

            Bukkit.getScheduler().cancelTask(scheduleId.get(player.getName()).taskId);
            scheduleId.remove(player.getName());


            // this enusres that the player wont conflict with another player's breaking animation
            int entityId = player.getEntityId() + 1;
            entityId = entityId * 1000;
            PacketContainer breakingAnimation = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

            breakingAnimation.getIntegers().write(0, entityId);
            breakingAnimation.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));

            // returns the breaking animation back to none
            breakingAnimation.getIntegers().write(1, -1);
            try {
                manager.sendServerPacket(player, breakingAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }

    @SuppressWarnings("deprecation")
    private double getBreakingTime(double hardness, Player player, Block block) {
        double miningSpeed = 0;
        int BreakPower = 0;

        ItemStack item = player.getEquipment().getItemInMainHand();

        if (item.getType().equals(Material.AIR)) {
            miningSpeed = 0;
            BreakPower = 0;
        }

        else if (item.getType().equals(Material.WOODEN_PICKAXE)) {
            miningSpeed = NBTUtils.getTagIntValue(item, "MiningSpeed");
            BreakPower = NBTUtils.getTagIntValue(item, "BreakPower");
        }

        else if (item.getType().equals(Material.STONE_PICKAXE)) {
            miningSpeed = 110;
            BreakPower = 2;
        }

        else if (item.getType().equals(Material.IRON_PICKAXE)) {
            miningSpeed = 160;
            BreakPower = 3;
        }

        else if (item.getType().equals(Material.DIAMOND_PICKAXE)) {
            miningSpeed = 230;
            BreakPower = 4;
        }

        else if (item.getType().equals(Material.NETHERITE_PICKAXE)) {
            miningSpeed = 300;
            BreakPower = 5;
        }

        else if (item.getType().equals(Material.GOLDEN_PICKAXE)) {
            miningSpeed = 250;
            BreakPower = 1;
        }

        double damage;

        player.sendMessage("挖掘方塊目標: " + block.getType().toString());
        // checks for a custom hardness
        if (SpeedConfigFileHandler.hasBlockMemory(block.getType().toString()) == Boolean.TRUE) {
            player.sendMessage(block.getType().toString());
            BlockMemory blockMemory = SpeedConfigFileHandler.getBlockMemory(block.getType().toString());
            player.sendMessage("你的工具破壞強度: " + BreakPower);
            player.sendMessage("你的工具挖掘速度: " + miningSpeed);
            player.sendMessage("方塊破壞強度: " + blockMemory.getBreakPower());
            if (BreakPower >= blockMemory.getBreakPower()) {
                damage = miningSpeed / hardness; // 1 / 5000
            } else {
                damage = 0;
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c你需要更好的工具來破壞此方塊!"));
            }
        } else {
            damage = miningSpeed / block.getType().getHardness();
        }

        damage /= 30; //damage = damage / 30

        // Instant breaking
        if (damage > 1) {
            return 0d;
        }

        return  Math.round(1 / damage);
    }
    final BlockFace[] faces = new BlockFace[] { BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
    private void playerBreakBlock(Player player, Block block) {
        player.sendMessage("破壞方塊");
        String blockType = block.getType().toString();
        Sound sound = block.getBlockData().getSoundGroup().getBreakSound();
        BlockHandle.breakBlock(player, block);
        player.sendMessage("破壞的方塊名稱: " + blockType);
        block.getWorld().playSound(block.getLocation(), sound, 1.0f, 1.0f);
    }
}
