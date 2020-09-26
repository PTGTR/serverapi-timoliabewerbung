package ptg.de.skynomix.serverapi.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import ptg.de.skynomix.serverapi.ServerAPI;



public class MySQL {
	
    private static String HOST;
    private static String DATABASE;
    private static String USER;
    private static String PASSWORD;
    
    private static Connection con;
	public static int MySQLSchedulerID;
    
    public static void connect() {
        if (!isConnected()) {
            try {
                MySQL.con = DriverManager.getConnection("jdbc:mysql://" + MySQL.HOST + ":3306/" + MySQL.DATABASE + "?autoReconnect=true", MySQL.USER, MySQL.PASSWORD);
                Bukkit.getConsoleSender().sendMessage("§aDie Verbindung zur MySQL ist erfolgreich.");
    			onReconnectScheduler();
    		} catch (SQLException e) {
    			Bukkit.getConsoleSender().sendMessage("§cDie Verbindung zur MySQL ist fehlgeschlagen! Fehler: §7" + e.getMessage());
    		}
        }
    }
    
    public static void disconnect() {
		try {
			if(con != null) {
				con.close();
				Bukkit.getConsoleSender().sendMessage("§aDie Verbindung zur MySQl wurde erfolgreich beendet.");
	            if (Bukkit.getScheduler().isCurrentlyRunning(MySQLSchedulerID)) {
	            	Bukkit.getScheduler().cancelTask(MySQLSchedulerID);
	            }
			}
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("§cDie Verbindung zur MySQL wurde abgebrochen! Fehler: §7" + e.getMessage());
			}
        }
    
    private static void onReconnectScheduler() {
        MySQLSchedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ServerAPI.o, new Runnable() {
            public void run() {
                onReconnect();
            }
        }, 20 * 60 * 60 *6, 20 * 60 * 60 *6);
    }
 
    private static void onReconnect() {
        if(con != null) {
            try {
            	con.close();
                System.out.println("MySQL-Verbindung beendet!");
                }
            catch (SQLException e) {
                System.err.println("MySQL-Verbindung konnte nicht getrennt werden!");
                e.printStackTrace();
            }
        }
   
        Bukkit.getScheduler().scheduleSyncDelayedTask(ServerAPI.o, new Runnable() {
            public void run() {
                try {
                	MySQL.con = DriverManager.getConnection("jdbc:mysql://" + MySQL.HOST + ":3306/" + MySQL.DATABASE + "?autoReconnect=true", MySQL.USER, MySQL.PASSWORD);
                    System.out.println("MySQL-Verbindung hergestellt!");
                }
                catch (SQLException e) {
                    System.err.println("MySQL-Verbindung konnte nicht hergestellt werden!");
                    e.printStackTrace();
                }
            }
        }, 1L);
    }
    
    public static boolean isConnected() {
        return MySQL.con != null;
    }
    
    public static Connection getConnection() {
        return MySQL.con;
    }
    
    public static void readMySQL() {
        final FileConfiguration cfg = FileManager.getConfiguration();
        MySQL.HOST = cfg.getString("mysql.host");
        MySQL.USER = cfg.getString("mysql.user");
        MySQL.DATABASE = cfg.getString("mysql.datenbase");
        MySQL.PASSWORD = cfg.getString("mysql.passwort");
    
    }
}
