package ru.demora.demoravillager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.demora.demoravillager.DemoraVillager;
import ru.demora.demoravillager.utils.ItemUtils;

public class DemoraVillagerCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("demoravillager.use")) {
			sender.sendMessage("§cУ вас нет прав для использования этой команды!");
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage("§cИспользование: /demoravillager <give|reload> [игрок]");
			return true;
		}
		String subCommand = args[0].toLowerCase();
		if (subCommand.equals("reload")) {
			return handleReload(sender);
		}
		if (subCommand.equals("give")) {
			return handleGive(sender, args);
		}
		sender.sendMessage("§cНеизвестная подкоманда! Используйте: give, reload");
		return true;
	}

	private boolean handleReload(CommandSender sender) {
		if (!sender.hasPermission("demoravillager.reload")) {
			sender.sendMessage("§cУ вас нет прав для перезагрузки плагина!");
			return true;
		}
		DemoraVillager plugin = DemoraVillager.getPlugin(DemoraVillager.class);
		try {
			plugin.reloadConfig();
			sender.sendMessage("§aКонфигурация плагина DemoraVillager перезагружена!");
		} catch (Exception e) {
			sender.sendMessage("§cОшибка при перезагрузке конфигурации!");
			plugin.getLogger().severe("Ошибка при перезагрузке конфигурации: " + e.getMessage());
		}
		return true;
	}

	private boolean handleGive(CommandSender sender, String[] args) {
		if (!sender.hasPermission("demoravillager.give")) {
			sender.sendMessage("§cУ вас нет прав для выдачи посоха!");
			return true;
		}
		if (args.length < 2) {
			sender.sendMessage("§cИспользование: /demoravillager give <игрок>");
			return true;
		}
		String targetPlayerName = args[1];
		Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
		if (targetPlayer == null) {
			sender.sendMessage("§cИгрок §e" + targetPlayerName + " §cне найден или не в сети!");
			return true;
		}
		DemoraVillager plugin = DemoraVillager.getPlugin(DemoraVillager.class);
		ItemStack staff = ItemUtils.createVillagerStaff(plugin);
		if (targetPlayer.getInventory().firstEmpty() == -1) {
			sender.sendMessage("§cУ игрока §e" + targetPlayer.getName() + " §cнет места в инвентаре!");
			return true;
		}
		targetPlayer.getInventory().addItem(staff);
		sender.sendMessage("§aВы выдали посох жителя игроку §e" + targetPlayer.getName() + "§a!");
		return true;
	}
}
