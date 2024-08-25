package me.forfunpenguin.miningblock.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.forfunpenguin.miningblock.MiningBlock;
import me.forfunpenguin.miningblock.Memory.ItemMemory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class ItemUtils {
    public static ItemStack getItem(ItemStack item, String name, String ... lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lores = new ArrayList<>();
        for (String s : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);

        item.setItemMeta(meta);

        return item;
    }

    //SkullOwner:{Id:[I;1257704380,829308929,-1527456141,202011262],Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjg5NjFhMjE1ZGRhZjQ2NDU2NGIwNGFkYzUyNTljYWYzOGY5YjM1NTE0OTZhZjBkNGMyYzA1OWYzZjdhZTgxOCJ9fX0="}]}} 1
    //Id I;1257704380,829308929,-1527456141,202011262
    //Value yJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjg5NjFhMjE1ZGRhZjQ2NDU2NGIwNGFkYzUyNTljYWYzOGY5YjM1NTE0OTZhZjBkNGMyYzA1OWYzZjdhZTgxOCJ9fX0=
    //Id:[],Properties:{textures:[{Value:""}]}
    public static ItemStack getHead(String headID, String headValue) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD); //Creating a new player Head
        SkullMeta skullmetaGlobe = getSkull(headValue, "Global Stats", head, headID); // Setting the custom texture from the website
        head.setItemMeta(skullmetaGlobe); // Setting the texture to the player head

        //net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(head);
        //CompoundTag compoundTag = nmsItem.getTag();
        //Bukkit.getLogger().info(compoundTag.toString());
        return head;
    }

    private static SkullMeta getSkull(String url, String name, ItemStack itemStack, String headID)
    {
        if(!url.isEmpty() && !name.isEmpty())
        {
            SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
            GameProfile profile = new GameProfile(UUID.fromString(headID), name);
            //Bukkit.getLogger().info(profile.getId().toString());
            profile.getProperties().put("textures", new Property("textures", url));

            try {
                assert headMeta != null;
                Field profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            //Bukkit.getLogger().info(headMeta.toString());
            return headMeta;
        }
        else
        {
            return null;
        }
    }
    //以下都為itemmemory
    private static final Map<String, ItemMemory> itemmemory = new HashMap<>();

    public static ItemMemory getItemMemory(String id) {
        if (!itemmemory.containsKey(id)) {
            ItemMemory m = new ItemMemory();
            itemmemory.put(id, m);
            return m;
        }
        return itemmemory.get(id);
    }

    public static void setItemMemory(String id, ItemMemory memory) {
        if (memory == null) itemmemory.remove(id);
        else itemmemory.put(id, memory);
    }

    public static Boolean hasItemMemory(String id) {
        return itemmemory.containsKey(id);
    }

    public static void itemBuilder(String id){
        try {
            File file = new File(MiningBlock.getFolderPath() + "/Items/" + id + ".yml");
            FileConfiguration itemData = YamlConfiguration.loadConfiguration(file);
            ItemMemory memory = new ItemMemory();
            memory.setId((itemData.getString("ItemID")));
            memory.setType((itemData.getString("ItemType")));
            memory.setDisplayName((itemData.getString("ItemDisplayName")));
            memory.setLore((itemData.getStringList("ItemLore")));
            ItemStack itemStack;
            if (memory.getType().equals("PLAYER_HEAD")) {
                memory.setHeadID((itemData.getString("HeadID")));
                memory.setHeadValue((itemData.getString("HeadValue")));
                itemStack = getHead(memory.getHeadID(), memory.getHeadValue());
            } else {
                itemStack = new ItemStack(Material.getMaterial(memory.getType()));
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(memory.getDisplayName());
            itemMeta.setLore(memory.getLore());
            itemStack.setItemMeta(itemMeta);
            itemStack = NBTUtils.putStringTag(itemStack,"ID", memory.getId());
            //Bukkit.getLogger().info(NBTUtils.getTag(itemStack));
            memory.setItem(itemStack);
            setItemMemory(memory.getId(), memory);
            Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&a" + memory.getId() + "已載入"));

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&c物品建構時發生錯誤!"));
        }
    }

    public static void loreBuilder(ItemStack itemStack) {

    }
}
