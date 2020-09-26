package ptg.de.skynomix.serverapi.bedwars;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import ptg.de.skynomix.serverapi.ServerAPI;
import ptg.de.skynomix.serverapi.mysql.MySQL;

public class KillsAPI {
	public static HashMap<Player, Integer> kills = new HashMap<>();
	public static Integer getKills(final UUID uuid) {
	        try {
	            final PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM BEDWARS WHERE UUID = ?");
	            ps.setString(1, uuid.toString());
	            final ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("KILLS");
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return -1;
	    }
	    public static void addKills(final org.bukkit.entity.Player p, int anzahl) {
	        try {
	            final PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE BEDWARS SET KILLS = ? WHERE UUID = ?");
	            ps.setInt(1, getKills(p.getUniqueId()) + anzahl);
	            ps.setString(2, p.getUniqueId().toString());
	            ps.executeUpdate();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void removeKills(final org.bukkit.entity.Player p, int anzahl) {
	        try {
	            final PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE BEDWARS SET KILLS = ? WHERE UUID = ?");
	            ps.setInt(1, getKills(p.getUniqueId()) - anzahl);
	            ps.setString(2, p.getUniqueId().toString());
	            ps.executeUpdate();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public static void addKillshashmap(Player p, int anzahl) {
	    	try {
	    		if(kills.containsKey(p)) {
	    			kills.put(p, kills.get(p) + anzahl);
	    		} else {
	    			p.sendMessage(ServerAPI.Prefix + "§cEin Fehler ist aufgetreten, bitte melde dich im Support!");
	    		}
	    	}catch (Exception exception) {
				// TODO: handle exception
			}
	    }
	    public static void removeKillshashmap(Player p, int anzahl) {
	    	try {
	    		if(kills.containsKey(p)) {
	    			kills.put(p, kills.get(p) - anzahl);
	    		} else {
	    			p.sendMessage(ServerAPI.Prefix + "§cEin Fehler ist aufgetreten, bitte melde dich im Support!");
	    		}
	    	}catch (Exception exception) {
				// TODO: handle exception
			}
	    }
	    public static Integer getkillhashmap(Player p) {
	    	try {
	    		if(kills.containsKey(p)) {
	    			return kills.get(p);
	    		} else {
	    			p.sendMessage(ServerAPI.Prefix + "§cEin Fehler ist aufgetreten, bitte melde dich im Support!");
	    		}
	    	}catch (Exception exception) {
				// TODO: handle exception
			}
			return null;
	    }

}
