package me.forfunpenguin.miningblock.Memory;

import lombok.Getter;
import lombok.Setter;

public class BlockMemory {

    @Getter
    @Setter
    private String itemType;

    @Getter
    @Setter
    private double hardness;

    @Getter
    @Setter
    private String dropItem;

    @Getter
    @Setter
    private int dropMinAmount;

    @Getter
    @Setter
    private int dropMaxAmount;

    @Getter
    @Setter
    private int breakPower;

    @Getter
    @Setter
    private String isCustomDrop;

    @Getter
    @Setter
    private String headID;

    @Getter
    @Setter
    private String headValue;
}
