package me.misterl9ine.ultragear;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public class UltraGear extends JavaPlugin implements SlimefunAddon {

    private int researchId = 3300;
    private Category category;

    @Override
    public void onEnable() {
        // Read something from your config.yml
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }

        // Slimefun4 also already comes with a bundled version of bStats
        // You can use bStats to collect usage data about your plugin
        // More info: https://bstats.org/getting-started
        // Set bStatsId to the id of your plugin
        int bStatsId = -1;
        new Metrics(this, bStatsId);

        // Create a new Category
        category = new Category(new NamespacedKey(this, "items"), new CustomItem(Material.DIAMOND_SWORD, "&6UltraGear",), 1);
        registerSword(Material.DIAMOND_SWORD, "UltraDiamond", SlimefunItems.SYNTHETIC_DIAMOND, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 10), (new Pair<>(Enchantment.DURABILITY, 10))));
        registerArmor(ArmorSet.DIAMOND, "UltraDiamond", SlimefunItems.SYNTHETIC_DIAMOND, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 10), (new Pair<>(Enchantment.DURABILITY, 10))));
    }
    private void registerSword(Material type, String component, ItemStack item, List<Pair<Enchantment, Integer>> enchantments) {
        SlimefunItemStack is = new SlimefunItemStack(component + "_SWORD", type, "&r" + ChatUtils.humanize(component) + " Sword");

        for (Pair<Enchantment, Integer> enchantment : enchantments) {
            is.addUnsafeEnchantment(enchantment.getFirstValue(), enchantment.getSecondValue());
        }

        SlimefunItem slimefunItem = new SlimefunItem(category, is, RecipeType.MAGIC_WORKBENCH, new ItemStack[] { null, item, null, null, item, null, null, new ItemStack(Material.STICK), null });
        slimefunItem.register(this);

        researchId++;

        Research research = new Research(new NamespacedKey(this, component.toLowerCase() + "_sword"), researchId, ChatUtils.humanize(component) + " Sword", 3);
        research.addItems(slimefunItem);
        research.register();
    }
    private void registerArmor(ArmorSet armorset, String component, ItemStack item, List<Pair<Enchantment, Integer>> enchantments) {
        SlimefunItemStack[] armor = { new SlimefunItemStack(component + "_HELMET", armorset.getHelmet(), "&r" + ChatUtils.humanize(component) + " Helmet"), new SlimefunItemStack(component + "_CHESTPLATE", armorset.getChestplate(), "&r" + ChatUtils.humanize(component) + " Chestplate"), new SlimefunItemStack(component + "_LEGGINGS", armorset.getLeggings(), "&r" + ChatUtils.humanize(component) + " Leggings"), new SlimefunItemStack(component + "_BOOTS", armorset.getBoots(), "&r" + ChatUtils.humanize(component) + " Boots") };

        for (Pair<Enchantment, Integer> enchantment : enchantments) {
            for (ItemStack is : armor) {
                is.addUnsafeEnchantment(enchantment.getFirstValue(), enchantment.getSecondValue());
            }
        }
        SlimefunItem helmet = new SlimefunItem(category, armor[0], RecipeType.ARMOR_FORGE, new ItemStack[] { item, item, item, item, null, item, null, null, null });
        helmet.register(this);

        SlimefunItem chestplate = new SlimefunItem(category, armor[1], RecipeType.ARMOR_FORGE, new ItemStack[] { item, null, item, item, item, item, item, item, item });
        chestplate.register(this);

        SlimefunItem leggings = new SlimefunItem(category, armor[2], RecipeType.ARMOR_FORGE, new ItemStack[] { item, item, item, item, null, item, item, null, item });
        leggings.register(this);

        SlimefunItem boots = new SlimefunItem(category, armor[3], RecipeType.ARMOR_FORGE, new ItemStack[] { null, null, null, item, null, item, item, null, item });
        boots.register(this);

        researchId++;

        Research research = new Research(new NamespacedKey(this, component.toLowerCase() + "_armor"), researchId, ChatUtils.humanize(component) + " Armor", 30);
        research.addItems(helmet, chestplate, leggings, boots);
        research.register();
    }
    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

}
