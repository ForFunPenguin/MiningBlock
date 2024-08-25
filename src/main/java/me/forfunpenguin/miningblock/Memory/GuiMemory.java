package me.forfunpenguin.miningblock.Memory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public class GuiMemory {
    @Getter
    @Setter
    private Location pos1;

    @Getter
    @Setter
    private Location pos2;

    @Getter
    @Setter
    private String areaDisplayname;

    @Getter
    @Setter
    private String NameTyping;
}
