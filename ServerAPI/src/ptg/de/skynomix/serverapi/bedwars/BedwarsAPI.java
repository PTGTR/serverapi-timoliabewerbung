package ptg.de.skynomix.serverapi.bedwars;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import ptg.de.skynomix.serverapi.mysql.MySQL;

public class BedwarsAPI {
	public static boolean bedwarsgame = false;
	public static boolean playerExists(UUID uuid) {
		  try {
		        final PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM BEDWARS WHERE UUID = ?");
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
	            final PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO BEDWARS(UUID, KILLS, DEAHTS, WINS, GAMES_PLAYED, BED) VALUES (?,?,?,?,?,?)");
	           
	            ps.setString(1, uuid.toString());
	            ps.setInt(2, 0);
	            ps.setInt(3, 0);
	            ps.setInt(4, 0);
	            ps.setInt(5, 0);
	            ps.setInt(6, 0);
	            ps.executeUpdate();
	        	}
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	  public static void selectBedwars() {
		  bedwarsgame = true;
	  }
}
