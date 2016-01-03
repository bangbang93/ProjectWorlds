package com.gmail.trentech.pjw.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pjw.Main;

public class CMDWorld implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		
		pages.title(Text.builder().color(TextColors.DARK_GREEN).append(Text.of(TextColors.AQUA, "Command List")).build());
		
		List<Text> list = new ArrayList<>();
		
		if(src.hasPermission("pjw.cmd.world.create")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Create")).append(Text.of(" /world create")).build());
		}
		if(src.hasPermission("pjw.cmd.world.delete")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Delete")).append(Text.of(" /world delete")).build());
		}
		if(src.hasPermission("pjw.cmd.world.rename")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Rename")).append(Text.of(" /world rename")).build());
		}
		if(src.hasPermission("pjw.cmd.world.copy")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Copy")).append(Text.of(" /world copy")).build());
		}
		if(src.hasPermission("pjw.cmd.world.load")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Load")).append(Text.of(" /world load")).build());
		}
		if(src.hasPermission("pjw.cmd.world.unload")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Unload")).append(Text.of(" /world unload")).build());
		}
		if(src.hasPermission("pjw.cmd.world.enable")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Enable")).append(Text.of(" /world enable")).build());
		}
		if(src.hasPermission("pjw.cmd.world.regen")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Regen")).append(Text.of(" /world regen")).build());
		}
		if(src.hasPermission("pjw.cmd.world.gamemode")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Gamemode")).append(Text.of(" /world gamemode")).build());
		}
		if(src.hasPermission("pjw.cmd.world.difficulty")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Difficulty")).append(Text.of(" /world difficulty")).build());
		}
		if(src.hasPermission("pjw.cmd.world.setspawn")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Setspawn")).append(Text.of(" /world setspawn")).build());
		}
//		if(src.hasPermission("pjw.cmd.world.pvp")) {
//			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
//					.onClick(TextActions.runCommand("/world help Pvp")).append(Text.of(" /world pvp")).build());
//		}
		if(src.hasPermission("pjw.cmd.world.keepspawnloaded")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Keepspawnloaded")).append(Text.of(" /world keepspawnloaded")).build());
		}
		if(src.hasPermission("pjw.cmd.world.hardcore")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help Hardcore")).append(Text.of(" /world hardcore")).build());
		}
		if(src.hasPermission("pjw.cmd.world.list")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help List")).append(Text.of(" /world list")).build());
		}
		if(src.hasPermission("pjw.cmd.world.teleport")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/world help teleport")).append(Text.of(" /world teleport")).build());
		}
		if(src.hasPermission("pjw.cmd.gamerule")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information ")))
					.onClick(TextActions.runCommand(" /gamerule")).append(Text.of(" /gamerule")).build());
		}
		pages.contents(list);
		
		pages.sendTo(src);

		return CommandResult.success();
	}

}
