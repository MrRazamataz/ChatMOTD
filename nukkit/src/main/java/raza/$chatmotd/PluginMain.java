package raza.$chatmotd;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import cn.nukkit.*;
import cn.nukkit.command.*;
import cn.nukkit.event.*;
import cn.nukkit.plugin.*;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.*;


public class PluginMain extends PluginBase implements Listener {

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
						+ TextFormat.colorize('&', String.valueOf(FINAL_loopValue1))));
			}
			PluginMain.getInstance().getLogger().info("Plugin has enabled.");
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
		if (command.getName().equalsIgnoreCase("motd")) {
			try {
				for (Object FINAL_loopValue1 : java.nio.file.Files.readAllLines(
						new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
						java.nio.charset.StandardCharsets.UTF_8)) {
					commandSender
							.sendMessage(TextFormat.colorize('&', String.valueOf(FINAL_loopValue1)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("setchatmotd")) {
			Object $2de0fd60f64bec84460174f6876c3f09 = null;
			try {
				$2de0fd60f64bec84460174f6876c3f09 = String.join(" ", PluginMain.createList(commandArgs));
				$2de0fd60f64bec84460174f6876c3f09 = ((java.lang.String) String
						.valueOf($2de0fd60f64bec84460174f6876c3f09)
						.replace(String.valueOf("\\n"), String.valueOf('\n')));
				new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").delete();
				new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").createNewFile();
				java.nio.file.Files.write(
						new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
						Collections.singleton(TextFormat.colorize('&',
								String.valueOf($2de0fd60f64bec84460174f6876c3f09))),
						java.nio.charset.StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.WRITE);
				commandSender.sendMessage(("The ChatMOTD has been set to: " + TextFormat
						.colorize('&', String.valueOf($2de0fd60f64bec84460174f6876c3f09))));
				Server.getInstance().broadcastMessage(
						TextFormat.colorize('&', String.valueOf($2de0fd60f64bec84460174f6876c3f09)));
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
	public void event1(cn.nukkit.event.player.PlayerJoinEvent event) throws Exception {
		for (Object FINAL_loopValue1 : java.nio.file.Files.readAllLines(
				new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
				java.nio.charset.StandardCharsets.UTF_8)) {
			((cn.nukkit.command.CommandSender) (Object) ((Player) event.getPlayer()))
					.sendMessage(TextFormat.colorize('&', String.valueOf(FINAL_loopValue1)));
		}
	}

	
}
