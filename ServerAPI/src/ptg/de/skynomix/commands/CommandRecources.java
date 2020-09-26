package ptg.de.skynomix.commands;

import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ptg.de.skynomix.serverapi.ServerAPI;


public class CommandRecources implements CommandExecutor {


    public static String formatDateDiff(final long date) {
        final Calendar c = new GregorianCalendar();
        c.setTimeInMillis(date);
        final Calendar now = new GregorianCalendar();
        return formatDateDiff(now, c);
    }

    public static String formatDateDiff(final Calendar fromDate, final Calendar toDate) {
        boolean future = false;
        if (toDate.equals(fromDate)) {
            return "now" + new Object[0];
        }
        if (toDate.after(fromDate)) {
            future = true;
        }
        final StringBuilder sb = new StringBuilder();
        final int[] types = {1, 2, 5, 11, 12, 13};
        final String[] names = {"Jahr(e)", "Jahre", "Monat(e)", "Monate", "Tag(e)", "Tage", "Stunde(n)", "Stunden", "Minute(n)", "Minuten", "Sekunde(n)", "Sekunden"};
        for (int accuracy = 0, i = 0; i < types.length && accuracy <= 2; ++i) {
            final int diff = dateDiff(types[i], fromDate, toDate, future);
            if (diff > 0) {
                ++accuracy;
                sb.append(" ").append(diff).append(" ").append(names[i * 2 + 0]);
            }
        }
        if (sb.length() == 0) {
            return "now";
        }
        return sb.toString().trim();
    }

    private static int dateDiff(final int type, final Calendar fromDate, final Calendar toDate, final boolean future) {
        int diff = 0;
        long savedDate = fromDate.getTimeInMillis();
        while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
            savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future ? 1 : -1);
            ++diff;
        }
        --diff;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
            Player p = (Player) arg0;
            if (p.hasPermission("serverapi.admin")) {
                final long ram = Runtime.getRuntime().totalMemory();
                final long ramr = Math.round((float) ram);
                final double kram = (double) (ramr / 8000000L);
                final long ver = Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory();
                final long pro = ver * 100L / Runtime.getRuntime().maxMemory();
                p.sendMessage(ServerAPI.Prefix+"§7Betriebsinformationen zum Server");
                p.sendMessage("§8» §7Maximaler Arbeitsspeicher§8: §e" + Runtime.getRuntime().maxMemory() / 1048576L + "MB");
                p.sendMessage("§8» §7Verfügbarer Arbeitsspeicher§8: §e" + Runtime.getRuntime().freeMemory() / 1048576L + "MB");
                p.sendMessage("§8» §7Benutzer Arbeitsspeicher§8: §e" + ver / 1048576L + "MB");
                p.sendMessage("§8» §7Benutzter Arbeitsspeicher in Prozent§8: §e" + pro + "%");
                p.sendMessage("§8» §7SWAP§8: §e" + kram + " §7mbit/s");
                p.sendMessage("§8» §7Uptime§8: §e" + formatDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime()));
            } else {
                p.sendMessage(ServerAPI.Prefix+"§7Dafür hast du keine Rechte.");
            }
            return false;
    }

}
