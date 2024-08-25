package me.forfunpenguin.miningblock.Utils;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockUtils {
    final static BlockFace[] faces = new BlockFace[] { BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
    final static BlockFace[] connectedFaces = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    public static BlockFace getClosestFace(float direction) {

        direction = direction % 360;

        if (direction < 0)
            direction += 360;

        direction = Math.round(direction / 45);

        switch ((int) direction) {
            case 0:
                return BlockFace.WEST;
            case 1:
                return BlockFace.NORTH_WEST;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.NORTH_EAST;
            case 4:
                return BlockFace.EAST;
            case 5:
                return BlockFace.SOUTH_EAST;
            case 6:
                return BlockFace.SOUTH;
            case 7:
                return BlockFace.SOUTH_WEST;
            default:
                return BlockFace.WEST;
        }
    }

    public static BlockFace[] getFacing(String strBlockData, Player player) {
        String strBlockFaces = strBlockData.substring(strBlockData.indexOf("[")+1, strBlockData.indexOf("]")).toUpperCase();
        //player.sendMessage(strBlockFaces);
        String[] blockFaces = strBlockFaces.split(",");
        List<String> listBlockFaces = new ArrayList<String>();
        for (String str : blockFaces) {
            String[] blockFacesValue = str.split("=");
            if (!blockFacesValue[0].equals("WATERLOGGED")) {
                if (blockFacesValue[1].equals("TRUE")) {
                    listBlockFaces.add(blockFacesValue[0]);
                }
            }
        }
        //player.sendMessage(listBlockFaces.toString());
        String strDirection = listBlockFaces.toString().substring(listBlockFaces.toString().indexOf("[")+1, listBlockFaces.toString().indexOf("]"));
        BlockFace[] face = Arrays.stream(strDirection.toString().split(", ")).map(BlockFace::valueOf).toArray(BlockFace[]::new);
        return face;
    }

    public static void repairConnectedBlock(Block block) {
        List<String> listBlockFaces = new ArrayList<String>();
        BlockState state = block.getState();
        for (BlockFace face : connectedFaces) {
            Block relative = block.getRelative(face);
            if (relative.getType().toString().contains("GLASS") || relative.getType().toString().equals("STONE") || relative.getType().toString().equals("COBBLESTONE")) {
                listBlockFaces.add(face.toString());
            }
        }
        String strDirection = listBlockFaces.toString().substring(listBlockFaces.toString().indexOf("[")+1, listBlockFaces.toString().indexOf("]"));
        BlockFace[] blockFaces = Arrays.stream(strDirection.toString().split(", ")).map(BlockFace::valueOf).toArray(BlockFace[]::new);
        if (block.getBlockData() instanceof GlassPane) {
            GlassPane glassPane = (GlassPane) block.getBlockData();
            if (blockFaces.length > 0) {
                for (int x=0; x<blockFaces.length; x++) {
                    glassPane.setFace(blockFaces[x], true);
                }
                state.setBlockData(glassPane);
                state.update();
            }
        }
    }
}
