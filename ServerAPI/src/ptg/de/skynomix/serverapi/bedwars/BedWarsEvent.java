package ptg.de.skynomix.serverapi.bedwars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ptg.de.skynomix.serverapi.ServerAPI;
import ptg.de.skynomix.serverapi.coins.CoinsAPI;

public class BedWarsEvent implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		BedwarsAPI.addPlayerToDB(e.getPlayer().getUniqueId());
		KillsAPI.kills.put(e.getPlayer(), 0);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		KillsAPI.addKills(e.getPlayer(), KillsAPI.kills.get(e.getPlayer()));
		KillsAPI.kills.remove(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player t = p.getKiller();
		if(t != null) {
			KillsAPI.addKillshashmap(t, 1);
		}
	}
}
