package ru.demora.demoravillager.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import ru.demora.demoravillager.DemoraVillager;
import ru.demora.demoravillager.managers.CooldownManager;
import ru.demora.demoravillager.utils.ItemUtils;

public class PlayerInteractListener implements Listener {
	private final DemoraVillager plugin;

	public PlayerInteractListener(DemoraVillager plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (!ItemUtils.isVillagerStaff(item, plugin)) {
			return;
		}
		event.setCancelled(true);
		CooldownManager cooldownManager = plugin.getCooldownManager();
		long cooldownTime = plugin.getConfig().getLong("cooldown.time", 10) * 1000;
		if (cooldownManager.hasCooldown(player.getUniqueId(), cooldownTime)) {
			long remaining = cooldownManager.getRemainingCooldown(player.getUniqueId(), cooldownTime);
			player.sendMessage("§cПосох перезаряжается! Осталось: §e" + remaining + "§c сек.");
			return;
		}
		Location targetLocation = getTargetLocation(player);
		if (targetLocation == null) {
			return;
		}
		plugin.getExplosiveItemDisplayManager().createExplosiveItemDisplay(targetLocation);
		cooldownManager.setCooldown(player.getUniqueId());
	}

	private Location getTargetLocation(Player player) {
		double maxDistance = 50.0;
		RayTraceResult rayTrace = player.getWorld().rayTraceBlocks(
			player.getEyeLocation(),
			player.getEyeLocation().getDirection(),
			maxDistance
		);
		Location targetLocation;
		if (rayTrace != null && rayTrace.getHitBlock() != null) {
			targetLocation = rayTrace.getHitBlock().getLocation().add(0, 1, 0);
		} else {
			Vector direction = player.getEyeLocation().getDirection().normalize();
			targetLocation = player.getEyeLocation().add(direction.multiply(maxDistance));
		}
		targetLocation.setX(targetLocation.getBlockX() + 0.5);
		targetLocation.setZ(targetLocation.getBlockZ() + 0.5);
		return targetLocation;
	}
}
