package ptg.de.skynomix.serverapi;

import java.sql.PreparedStatement;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ptg.de.skynomix.commands.CommandCheatClear;
import ptg.de.skynomix.commands.CommandCoins;
import ptg.de.skynomix.commands.CommandRecources;
import ptg.de.skynomix.serverapi.bedwars.BedWarsEvent;
import ptg.de.skynomix.serverapi.bedwars.BedwarsAPI;
import ptg.de.skynomix.serverapi.coins.CoinsEvent;
import ptg.de.skynomix.serverapi.mysql.FileManager;
import ptg.de.skynomix.serverapi.mysql.MySQL;

public class ServerAPI extends JavaPlugin{
	public static String Prefix = "§7[§eSky§6Nomix§7] ";
	public static ServerAPI o;
	@Override
	public void onEnable() {
		o = this;
		FileManager.createFile();
		MySQL.readMySQL();
		loadDatenbank();
		getCommand("coins").setExecutor(new CommandCoins());
		getCommand("chatclear").setExecutor(new CommandCheatClear());
		getCommand("recources").setExecutor(new CommandRecources());
		getServer().getPluginManager().registerEvents(new CoinsEvent(), this);
		Bukkit.getConsoleSender().sendMessage(Prefix + "§eGame searched..."); 
		if(BedwarsAPI.bedwarsgame == true) {
			getServer().getPluginManager().registerEvents(new BedWarsEvent(), this);	
			Bukkit.getConsoleSender().sendMessage(Prefix + "§eBedWars loading..."); 
		} else {
			Bukkit.getConsoleSender().sendMessage(Prefix + "§4Game not found!"); 
		}
		Bukkit.getConsoleSender().sendMessage(Prefix + "§aServer API loaded!");
		Bukkit.getConsoleSender().sendMessage(Prefix + "§aPlugin made by ptg"); 
	}
//	KILLS
//	DEATHS
//	WINS
//	GAMES_PLAYED
//	BEDS

	private void loadDatenbank() {
        MySQL.connect();
        try {
        	final PreparedStatement ps1 = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS COINS (UUID VARCHAR(64), COINS int)");
        	final PreparedStatement ps2 = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS BEDWARS (UUID VARCHAR(64), KILLS int, DEAHTS int, WINS int, GAMES_PLAYED int, BED int)");
        	ps1.executeUpdate();
        	ps2.executeUpdate();
        } catch (Exception exception) {
			// TODO: handle exception
		}

    }

}
