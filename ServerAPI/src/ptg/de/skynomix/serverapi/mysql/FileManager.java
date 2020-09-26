package ptg.de.skynomix.serverapi.mysql;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ptg.de.skynomix.serverapi.ServerAPI;

import java.io.File;

public class FileManager {
	
    private static File getFile() {
        return new File("plugins/ServerAPI", "config.yml");
    }
    
    public static FileConfiguration getConfiguration() {
        return (FileConfiguration)YamlConfiguration.loadConfiguration(getFile());
    }
    public static void createFile() {
        if (!ServerAPI.o.getDataFolder().exists()) {
        	ServerAPI.o.getDataFolder().mkdir();
        }
        final File file = getFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
                final FileConfiguration cfg = getConfiguration();
                cfg.set("mysql.host", (Object)"localhost");
                cfg.set("mysql.user", (Object)"username");
                cfg.set("mysql.datenbase", (Object)"username");
                cfg.set("mysql.passwort", (Object)"passwort");
                cfg.save(getFile());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}