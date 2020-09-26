package ptg.de.skynomix.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ptg.de.skynomix.serverapi.ServerAPI;
import ptg.de.skynomix.serverapi.coins.CoinsAPI;
import ptg.de.skynomix.serverapi.mysql.MySQL;

public class CommandCoins implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
			if(arg3.length == 0) {
				p.sendMessage(ServerAPI.Prefix + "§7Du besitzt derzeit §e" + CoinsAPI.getCoinshashmap(p) + "§7 Coins.");
			} else if(arg3.length == 1){
				if(p.hasPermission("serverapi.team")) {
					if(arg3[0].equalsIgnoreCase("help")) {
		                p.sendMessage(ServerAPI.Prefix+"§e/Coins §8[§7Name§8]");
		            	if(p.hasPermission("serverapi.admin")) {
	                    p.sendMessage(ServerAPI.Prefix+"§e/Coins add §8[§7Name§8] §8[§7Wert§8]");
	                    p.sendMessage(ServerAPI.Prefix+"§e/Coins remove §8[§7Name§8] §8[§7Wert§8]");
		            	}
						return true;
					}
					Player target = Bukkit.getPlayer(arg3[0]);
					if(target != null) {
							p.sendMessage(ServerAPI.Prefix + "§7Der Spieler §a" + target.getName() + "§7 hat derzeit §e" + CoinsAPI.getCoinshashmap(target) + "§7 Coins.");
					} else  {
					OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(arg3[0]);
					if(CoinsAPI.playerExists(targetOffline.getUniqueId())) {
						p.sendMessage(ServerAPI.Prefix + "§7Der Spieler §a" + targetOffline + "§7 hat derzeit §e" + CoinsAPI.getCoins(targetOffline.getUniqueId()) + "§7 Coins.");
					} else {
						p.sendMessage(ServerAPI.Prefix + "§cDieser Spieler ist nicht in der Datanbank aufgelistet§7.");
					}
					}
				} else {
					p.sendMessage(ServerAPI.Prefix + "§cDazu fehlt dir die benötige Permission§7.");
				}
			} else if(arg3.length == 3) {
				  if (arg3[0].equalsIgnoreCase("add")) {
				if(p.hasPermission("serverapi.admin")) {
	            	Player target = Bukkit.getPlayer(arg3[1]);
	                int value = Integer.parseInt(arg3[2]);
	            	if (target != null) {
	            		CoinsAPI.addCoinshashmap(target, value);
	            		p.sendMessage(ServerAPI.Prefix+"§7Dem Spieler §a" + target.getName() + " §7wurden §e" + value + " §7Coins §ahinzugefügt§7.");
	            	} else {
	            		OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(arg3[1]);
	            		if(CoinsAPI.playerExists(targetOffline.getUniqueId())) {
	            			CoinsAPI.addCoins(targetOffline.getUniqueId(), value);
	                        p.sendMessage(ServerAPI.Prefix+"§7Dem Spieler §a" + targetOffline.getName() + " §7wurden §e" + value + " §7Coins §ahinzugefügt§7.");
	            		} else {
							p.sendMessage(ServerAPI.Prefix + "§cDieser Spieler ist nicht in der Datanbank aufgelistet§7.");
	            		}
	            	}
				} else {
					p.sendMessage(ServerAPI.Prefix + "§cDazu fehlt dir die benötige Permission§7.");
				}
				  } else if (arg3[0].equalsIgnoreCase("remove")) {
						if(p.hasPermission("serverapi.admin")) {
			            	Player target = Bukkit.getPlayer(arg3[1]);
			                int value = Integer.parseInt(arg3[2]);
			            	if (target != null) {
			            		CoinsAPI.removeCoinshashmap(target, value);
			            		p.sendMessage(ServerAPI.Prefix+"§7Dem Spieler §a" + target.getName() + " §7wurden §e" + value + " §7Coins §centfernt§7.");
			            	} else {
			            		OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(arg3[1]);
			            		if(CoinsAPI.playerExists(targetOffline.getUniqueId())) {
			            			CoinsAPI.removeCoins(target.getUniqueId(), value);
			                        p.sendMessage(ServerAPI.Prefix+"§7Dem Spieler §a" + targetOffline.getName() + " §7wurden §e" + value + " §7Coins §centfernt§7.");
			            		} else {
									p.sendMessage(ServerAPI.Prefix + "§cDieser Spieler ist nicht in der Datanbank aufgelistet§7.");
			            		}
			            	}
						} else {
							p.sendMessage(ServerAPI.Prefix + "§cDazu fehlt dir die benötige Permission§7.");
						}
						  }
			}
		return false;
	}

}
