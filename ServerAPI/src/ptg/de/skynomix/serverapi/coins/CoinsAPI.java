package ptg.de.skynomix.serverapi.coins;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import ptg.de.skynomix.serverapi.ServerAPI;
import ptg.de.skynomix.serverapi.mysql.MySQL;

public class CoinsAPI {
	public static HashMap<Player, Integer> coins = new HashMap<>();
	  public static boolean playerExists(UUID uuid) {
		  try {
		        final PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM COINS WHERE UUID = ?");
		        ps.setString(1, uuid.toString());
		        final ResultSet rs = ps.executeQuery();
		        return rs.next();
		  }catch (Exception exception) {
			// TODO: handle exception
		}
		  return false;
	  }
	  public static void addPlayerToDB(final UUID uuid) {
	        try {
	        	if(playerExists(uuid)) {
	        		
	        	}else {
	            final PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO COINS(UUID, COINS) VALUES (?,?)");
	           
	            ps.setString(1, uuid.toString());
	            ps.setInt(2, 0);
	            ps.executeUpdate();
	        	}
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	  public static Integer getCoins(final UUID uuid) {
	        try {
	            final PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM COINS WHERE UUID = ?");
	            ps.setString(1, uuid.toString());
	            final ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("COINS");
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return -1;
	    }
	    public static void addCoins(final UUID uuid, int anzahl) {
	        try {
	            final PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE COINS SET COINS = ? WHERE UUID = ?");
	            ps.setInt(1,  anzahl);
	            ps.setString(2, uuid.toString());
	            ps.executeUpdate();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void removeCoins(final UUID uuid, int anzahl) {
	        try {
	            final PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE COINS SET COINS = ? WHERE UUID = ?");
	            ps.setInt(1, getCoins(uuid) - anzahl);
	            ps.setString(2, uuid.toString());
	            ps.executeUpdate();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public static void addCoinshashmap(Player p, int anzahl) {
	    	try {
	    		if(coins.containsKey(p)) {
	    			coins.put(p, coins.get(p) + anzahl);
	    		} else {
	    			p.sendMessage(ServerAPI.Prefix + "§cEin Fehler ist aufgetreten, bitte melde dich im Support!");
	    		}
	    	}catch (Exception exception) {
				// TODO: handle exception
			}
	    }
	    public static void removeCoinshashmap(Player p, int anzahl) {
	    	try {
	    		if(coins.containsKey(p)) {
	    			coins.put(p, coins.get(p) - anzahl);
	    		} else {
	    			p.sendMessage(ServerAPI.Prefix + "§cEin Fehler ist aufgetreten, bitte melde dich im Support!");
	    		}
	    	}catch (Exception exception) {
				// TODO: handle exception
			}
	    }
	    public static Integer getCoinshashmap(Player p) {
	    	try {
	    		if(coins.containsKey(p)) {
	    			return coins.get(p);
	    		} else {
	    			p.sendMessage(ServerAPI.Prefix + "§cEin Fehler ist aufgetreten, bitte melde dich im Support!");
	    		}
	    	}catch (Exception exception) {
				// TODO: handle exception
			}
			return null;
	    }
}
