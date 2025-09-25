package ru.demora.demoravillager.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemUtils {
	public static ItemStack createVillagerStaff(JavaPlugin plugin) {
		FileConfiguration config = plugin.getConfig();
		ItemStack staff = new ItemStack(Material.IRON_AXE);
		ItemMeta meta = staff.getItemMeta();
		if (meta != null) {
			String name = config.getString("staff.name", "§6§lПосох жителя");
			meta.displayName(net.kyori.adventure.text.Component.text(name));
			int customModelData = config.getInt("staff.custom_model_data", 3);
			meta.setCustomModelData(customModelData);
			meta.setUnbreakable(true);
			staff.setItemMeta(meta);
		}
		return staff;
	}

	public static boolean isVillagerStaff(ItemStack item, JavaPlugin plugin) {
		if (item == null || item.getType() != Material.IRON_AXE) {
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		if (meta == null || !meta.hasCustomModelData()) {
			return false;
		}
		int expectedCustomModelData = plugin.getConfig().getInt("staff.custom_model_data", 3);
		return meta.getCustomModelData() == expectedCustomModelData;
	}

	public static ItemStack createSlimeBallHelmet(JavaPlugin plugin) {
		ItemStack slimeBall = new ItemStack(Material.SLIME_BALL);
		ItemMeta meta = slimeBall.getItemMeta();
		if (meta != null) {
			int customModelData = plugin.getConfig().getInt("slime_ball_cmd", 12);
			meta.setCustomModelData(customModelData);
			slimeBall.setItemMeta(meta);
		}
		return slimeBall;
	}
}
