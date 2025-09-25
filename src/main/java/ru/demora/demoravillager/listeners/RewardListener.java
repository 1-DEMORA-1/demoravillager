package ru.demora.demoravillager.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.demora.demoravillager.DemoraVillager;

public class RewardListener implements Listener {
	private final DemoraVillager plugin;

	public RewardListener(DemoraVillager plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity().getLastDamageCause() == null) return;
		org.bukkit.event.entity.EntityDamageEvent.DamageCause cause = event.getEntity().getLastDamageCause().getCause();
		if (cause != org.bukkit.event.entity.EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && cause != org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) return;
		java.util.Map<java.util.UUID, Long> tags = plugin.getExplosiveItemDisplayManager().getTaggedForReward();
		Long ts = tags.get(event.getEntity().getUniqueId());
		if (ts == null) return;
		if (System.currentTimeMillis() - ts > 3000) return;
		event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new org.bukkit.inventory.ItemStack(Material.EMERALD_BLOCK, 1));
		tags.remove(event.getEntity().getUniqueId());
	}
}
