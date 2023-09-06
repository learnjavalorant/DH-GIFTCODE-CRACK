/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package camchua.dhgiftcode;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    public static ItemStack create(Material mat) {
        ItemStack item = new ItemStack(mat);
        return item;
    }

    public static ItemStack create(Material mat, int amount) {
        ItemStack item = new ItemStack(mat, amount);
        return item;
    }

    public static ItemStack create(Material mat, int amount, int data) {
        ItemStack item = new ItemStack(mat, amount, (short)((byte)data));
        return item;
    }

    public static ItemStack create(Material mat, int amount, int data, String displayname) {
        ItemStack item = new ItemStack(mat, amount, (short)((byte)data));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)displayname));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material mat, int amount, int data, String displayname, List<String> lore, List<String> lorereplace) {
        ItemStack item = new ItemStack(mat, amount, (short)((byte)data));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)displayname));
        ArrayList<String> lores = new ArrayList<String>();
        for (String l : lore) {
            for (String re : lorereplace) {
                String form = re.split(":")[0];
                String to = re.split(":")[1];
                l.replace(form, to);
            }
            lores.add(ChatColor.translateAlternateColorCodes((char)'&', (String)l));
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material mat, int amount, int data, String displayname, List<String> lore) {
        ItemStack item = new ItemStack(mat, amount, (short)((byte)data));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)displayname));
        ArrayList<String> lores = new ArrayList<String>();
        for (String l : lore) {
            lores.add(ChatColor.translateAlternateColorCodes((char)'&', (String)l));
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }
}

