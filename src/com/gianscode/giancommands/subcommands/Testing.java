package com.gianscode.giancommands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gianscode.giancommands.Command;
import com.gianscode.giancommands.CommandInfo;

@CommandInfo(description =  "Testing the command system.", usage = "<number>", aliases = { "testing", "test" }, usePermission = true, permission = "giancommands.commands.testing")
public class Testing extends Command {

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Specify a number!");
			return;
		}

		int number = 0;

		try {
			number = Integer.valueOf(args[0]);
		} catch (NumberFormatException ex) {
			sender.sendMessage(ChatColor.RED + "You did not specify a number! Getting default value of 0!");
		}

		sender.sendMessage(ChatColor.GREEN + "Your number = " + ChatColor.RED + number);
	}
}