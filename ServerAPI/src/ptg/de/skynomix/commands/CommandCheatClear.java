package ptg.de.skynomix.commands;

import org.apache.logging.log4j.core.jmx.Server;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ptg.de.skynomix.serverapi.ServerAPI;


public class CommandCheatClear implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		  Player p = (org.bukkit.entity.Player)arg0;
		    	if (!p.hasPermission("serverapi.team")) {
		    		p.sendMessage(ServerAPI.Prefix+"§cDafür hast du keine Rechte.");
		    	} else {
		    		for (int i = 0; i < 300; ++i) {
		    			for (Player all : Bukkit.getOnlinePlayers()) {
		    				all.sendMessage(" ");
		    			   }
	                	}
		    		p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 500.0F, 500.0F);
		    		Bukkit.broadcastMessage(ServerAPI.Prefix+"§7Der §eChat §7wurde von §a"+p.getName()+" §7gelöscht.");
		    	}
		    return false;
		  }
	

}
