package me.forfunpenguin.miningblock.Memory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.UUID;

public class AreaMemory {
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
    private UUID playerUUID;

    @Getter
    @Setter
    private String areaStatus;
}
