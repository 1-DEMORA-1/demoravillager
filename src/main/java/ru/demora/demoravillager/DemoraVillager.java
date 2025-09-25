package ru.demora.demoravillager;

import org.bukkit.plugin.java.JavaPlugin;
import ru.demora.demoravillager.commands.DemoraVillagerCommand;
import ru.demora.demoravillager.listeners.PlayerInteractListener;
import ru.demora.demoravillager.listeners.RewardListener;
import ru.demora.demoravillager.managers.CooldownManager;
import ru.demora.demoravillager.managers.ExplosiveItemDisplayManager;

public final class DemoraVillager extends JavaPlugin {
	private CooldownManager cooldownManager;
	private ExplosiveItemDisplayManager explosiveItemDisplayManager;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		cooldownManager = new CooldownManager();
		explosiveItemDisplayManager = new ExplosiveItemDisplayManager(this);
		getCommand("demoravillager").setExecutor(new DemoraVillagerCommand());
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		getServer().getPluginManager().registerEvents(new RewardListener(this), this);
	}

	@Override
	public void onDisable() {
	}

	public CooldownManager getCooldownManager() {
		return cooldownManager;
	}

	public ExplosiveItemDisplayManager getExplosiveItemDisplayManager() {
		return explosiveItemDisplayManager;
	}
}
