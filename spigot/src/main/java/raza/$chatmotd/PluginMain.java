package raza.$chatmotd;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class PluginMain extends JavaPlugin implements Listener {

	private static PluginMain instance;

	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		createResourceFile("chatmotd.txt");
		new Metrics(PluginMain.getInstance(), ((int) (14685d)));
		try {
			logMotdMessages();
			getLogger().info("Plugin has enabled.");
			if (hasSpigotUpdate("100844")) {
				getLogger().info("There is a new update available on spigot! https://www.spigotmc.org/resources/chatmotd.100844/.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (new File(getInstance().getDataFolder(), "chatmotd.json").exists()) {
			getLogger().info("JSON mode is enabled, you cannot edit the motd with the setchatmotd command.");
		}
		}

	@Override
	public void onDisable() {
		getLogger().info("Plugin has been disabled.");
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
		if (command.getName().equalsIgnoreCase("motd")) {
			if (new File(getInstance().getDataFolder(), "chatmotd.json").exists()) {
				String jsonMessage = null;
				try {
					jsonMessage = String.join("", Files.readAllLines(new File(getInstance().getDataFolder(), "chatmotd.json").toPath(), StandardCharsets.UTF_8));
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + commandSender.getName() + " " + jsonMessage);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return true;
			}
				try {
				sendMotdMessages(commandSender);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

		if (command.getName().equalsIgnoreCase("setchatmotd")) {
			if (new File(getInstance().getDataFolder(), "chatmotd.json").exists()) {
				commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cJSON mode is enabled, you cannot edit the motd with the setchatmotd command.\n" +
						"Please edit the chatmotd.json file in the plugin folder instead."));
				return true;
			}
				try {
				setChatMotd(commandArgs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

		return true;
	}


	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		try {
			sendMotdToPlayer(event.getPlayer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void logMotdMessages() throws IOException {
		for (String motdMessage : Files.readAllLines(getMotdFilePath(), StandardCharsets.UTF_8)) {
			getLogger().info("The motd is set to: " + ChatColor.translateAlternateColorCodes('&', motdMessage));
		}
	}
	private void sendMotdMessages(CommandSender commandSender) throws IOException {
		for (String motdMessage : Files.readAllLines(getMotdFilePath(), StandardCharsets.UTF_8)) {
			commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', motdMessage));
		}
	}
	private void sendMotdToPlayer(Player player) throws IOException {
		if (new File(getInstance().getDataFolder(), "chatmotd.json").exists()) {
			// just get the raw json as a string
			String jsonMessage = String.join("", Files.readAllLines(new File(getInstance().getDataFolder(), "chatmotd.json").toPath(), StandardCharsets.UTF_8));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + jsonMessage);
			return;
		}
		for (String motdMessage : Files.readAllLines(getMotdFilePath(), StandardCharsets.UTF_8)) {
			getLogger().info("this is the motd message: " + motdMessage);
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', motdMessage));
		}
	}

	private void setChatMotd(String[] commandArgs) throws IOException {
		String messageToSet = String.join(" ", createList(commandArgs));
		messageToSet = messageToSet.replace("\\n", "\n");

		// Delete and recreate the file
		File motdFile = new File(getInstance().getDataFolder(), "chatmotd.txt");
		motdFile.delete();
		motdFile.createNewFile();

		// Write the new message to the file
		Files.write(motdFile.toPath(), Collections.singleton(ChatColor.translateAlternateColorCodes('&', messageToSet)),
				StandardCharsets.UTF_8, StandardOpenOption.WRITE);

		// Inform the sender and broadcast the message
		CommandSender commandSender = Bukkit.getConsoleSender();
		commandSender.sendMessage("The ChatMOTD has been set to: " +
				ChatColor.translateAlternateColorCodes('&', messageToSet));
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', messageToSet));
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

	private Path getMotdFilePath() {
		return new File(getInstance().getDataFolder(), "chatmotd.txt").toPath();
	}

	public static PluginMain getInstance() {
		return instance;
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
}
