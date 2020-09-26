package ptg.de.skynomix.serverapi.coins;

import org.apache.logging.log4j.core.jmx.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.cache.AbstractCache.StatsCounter;

public class CoinsEvent implements Listener {
	@EventHandler
	public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
		CoinsAPI.addPlayerToDB(e.getPlayer().getUniqueId());
		CoinsAPI.coins.put(e.getPlayer(), CoinsAPI.getCoins(e.getPlayer().getUniqueId()));
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		CoinsAPI.addCoins(e.getPlayer().getUniqueId(), CoinsAPI.coins.get(e.getPlayer()));
		CoinsAPI.coins.remove(e.getPlayer());
	}

}
