package me.forfunpenguin.miningblock.Memory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemMemory {

    @Getter
    @Setter
    private ItemStack item;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String displayName;

    @Getter
    @Setter
    private List<String> lore;


    @Getter
    @Setter
    private String headID;

    @Getter
    @Setter
    private String headValue;

    @Getter
    @Setter
    private String color;
}
