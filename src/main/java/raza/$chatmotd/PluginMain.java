package raza.$chatmotd;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;

public class PluginMain extends JavaPlugin implements Listener {

	private static PluginMain instance;

	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		PluginMain.createResourceFile("chatmotd.txt");
		try {
			for (Object FINAL_loopValue1 : java.nio.file.Files.readAllLines(
					new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
					java.nio.charset.StandardCharsets.UTF_8)) {
				PluginMain.getInstance().getLogger().info(("The motd is set to:"
						+ ChatColor.translateAlternateColorCodes('&', String.valueOf(FINAL_loopValue1))));
			}
			PluginMain.getInstance().getLogger().info("Plugin has enabled.");
			new Metrics(PluginMain.getInstance(), ((int) (14685d)));
			if (PluginMain.hasSpigotUpdate("100844")) {
				PluginMain.getInstance().getLogger()
						.warning((ChatColor.translateAlternateColorCodes('&',
								"&6There is an update available on spigot. Version: ")
								+ PluginMain.getSpigotVersion("100844")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		try {
			PluginMain.getInstance().getLogger().info("Plugin has been disabled.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
		if (command.getName().equalsIgnoreCase("setchatmotd")) {
			Object $2de0fd60f64bec84460174f6876c3f09 = null;
			Object $3b8bde443fbeda39d6ad8708c40415aa = null;
			try {
				$3b8bde443fbeda39d6ad8708c40415aa = PluginMain.createList(commandArgs);
				$2de0fd60f64bec84460174f6876c3f09 = String.join(" ",
						((java.util.List) (Object) $3b8bde443fbeda39d6ad8708c40415aa));
				new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").delete();
				new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").createNewFile();
				java.nio.file.Files.write(
						new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
						Collections.singleton(ChatColor.translateAlternateColorCodes('&',
								String.valueOf($2de0fd60f64bec84460174f6876c3f09))),
						java.nio.charset.StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.WRITE);
				commandSender.sendMessage(("The ChatMOTD has been set to: " + ChatColor
						.translateAlternateColorCodes('&', String.valueOf($2de0fd60f64bec84460174f6876c3f09))));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return true;
	}

	public static void procedure(String procedure, List procedureArgs) throws Exception {
	}

	public static Object function(String function, List functionArgs) throws Exception {
		return null;
	}

	public static List createList(Object obj) {
		if (obj instanceof List) {
			return (List) obj;
		}
		List list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			int length = java.lang.reflect.Array.getLength(obj);
			for (int i = 0; i < length; i++) {
				list.add(java.lang.reflect.Array.get(obj, i));
			}
		} else if (obj instanceof Collection<?>) {
			list.addAll((Collection<?>) obj);
		} else if (obj instanceof Iterator) {
			((Iterator<?>) obj).forEachRemaining(list::add);
		} else {
			list.add(obj);
		}
		return list;
	}

	public static void createResourceFile(String path) {
		Path file = getInstance().getDataFolder().toPath().resolve(path);
		if (Files.notExists(file)) {
			try (InputStream inputStream = PluginMain.class.getResourceAsStream("/" + path)) {
				Files.createDirectories(file.getParent());
				Files.copy(inputStream, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static PluginMain getInstance() {
		return instance;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void event1(org.bukkit.event.player.PlayerJoinEvent event) throws Exception {
		for (Object FINAL_loopValue1 : java.nio.file.Files.readAllLines(
				new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
				java.nio.charset.StandardCharsets.UTF_8)) {
			((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event.getPlayer()))
					.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(FINAL_loopValue1)));
		}
	}

	public static String getSpigotVersion(String resourceId) {
		String newVersion = PluginMain.getInstance().getDescription().getVersion();
		try (java.io.InputStream inputStream = new java.net.URL(
				"https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
				java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
			if (scanner.hasNext())
				newVersion = String.valueOf(scanner.next());
		} catch (java.io.IOException ioException) {
			newVersion = "Sal4iDev#4767";
			ioException.printStackTrace();
		}
		return newVersion;
	}

	public static boolean hasSpigotUpdate(String resourceId) {
		boolean hasUpdate = false;
		try (java.io.InputStream inputStream = new java.net.URL(
				"https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
				java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
			if (scanner.hasNext())
				hasUpdate = !PluginMain.getInstance().getDescription().getVersion().equalsIgnoreCase(scanner.next());
		} catch (java.io.IOException ioException) {
			ioException.printStackTrace();
			hasUpdate = false;
		}
		return hasUpdate;
	}
}
