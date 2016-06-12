package com.gianscode.giancommands;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gianscode.giancommands.subcommands.Testing;

public class CommandManager implements CommandExecutor {

	private ArrayList<Command> cmds;

	protected CommandManager() {
		cmds = new ArrayList<Command>();

		cmds.add(new Testing());
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("example")) {
			if (args.length == 0) {
				for (Command command : cmds) {
					CommandInfo info = command.getClass().getAnnotation(CommandInfo.class);

					sender.sendMessage(ChatColor.GOLD + "/example (" + StringUtils.join(info.aliases(), ", ").trim() + ")" + info.usage() + " - " + info.description());
				}

				return true;
			}

			Command exists = null;

			for (Command command : cmds) {
				CommandInfo info = command.getClass().getAnnotation(CommandInfo.class);

				for (String aliases : info.aliases()) {
					if (aliases.equalsIgnoreCase(args[0])) {
						exists = command;
						break;
					}
				}
			}

			if (exists == null) {
				sender.sendMessage(ChatColor.RED + "Command does not exist!");
				return true;
			}

			if (exists.getClass().getAnnotation(CommandInfo.class).usePermission() && !sender.hasPermission(exists.getClass().getAnnotation(CommandInfo.class).permission())) {
				sender.sendMessage(ChatColor.RED + "You do not have permission!");
				return true;
			}

			ArrayList<String> cmdArgs = new ArrayList<String>();

			Collections.addAll(cmdArgs, args);
			cmdArgs.remove(0);

			args = cmdArgs.toArray(new String[cmdArgs.size()]);

			exists.onCommand(sender, args);
		}

		return true;
	}
}