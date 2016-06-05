package com.gmail.trentech.pjw.listeners;

import java.io.File;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.world.ChangeWorldWeatherEvent;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import com.gmail.trentech.pjw.Main;
import com.gmail.trentech.pjw.utils.ConfigManager;

import ninja.leaping.configurate.ConfigurationNode;

public class EventManager {

	@Listener
	public void onClientConnectionEventJoin(ClientConnectionEvent.Join event) {
	    Player player = event.getTargetEntity();

		if(player.hasPermission("pjw.options.motd")) {
			player.sendMessage(Main.getGame().getServer().getMotd());
		}

		ConfigurationNode node = new ConfigManager().getConfig().getNode("options");
		
		String defaultWorld = Main.getGame().getServer().getDefaultWorld().get().getWorldName();
		
		boolean lobbyMode = node.getNode("lobby_mode").getBoolean();
		boolean firstJoin = new File(defaultWorld + File.separator + "playerdata", player.getUniqueId().toString() + ".dat").exists();
		
		if(firstJoin && !lobbyMode) {
			return;
		}

		String worldName = node.getNode("first_join", "world").getString();
		
		Optional<World> optionalWorld = Main.getGame().getServer().getWorld(worldName);
		
		if(!optionalWorld.isPresent()) {
			return;
		}
		World world = optionalWorld.get();
		
		player.setLocationSafely(world.getSpawnLocation());
		
		if(player.hasPermission("pjw.options.gamemode")) {
			player.offer(Keys.GAME_MODE, world.getProperties().getGameMode());
		}		
		
		if(!firstJoin) {
			Text title = TextSerializers.FORMATTING_CODE.deserialize(node.getNode("first_join", "title").getString());
			Text subTitle = TextSerializers.FORMATTING_CODE.deserialize(node.getNode("first_join", "sub_title").getString());

			player.sendTitle(Title.of(title, subTitle));
		}		
	}
	
	@Listener
	public void onLoadWorldEvent(LoadWorldEvent event) {
		World world = event.getTargetWorld();

		WorldProperties properties = world.getProperties();

		if(!properties.getGameRule("spawnOnDeath").isPresent()) {
			if(properties.getGameRule("respawnWorld").isPresent()) {
				properties.setGameRule("spawnOnDeath", properties.getGameRule("respawnWorld").get());				
			}else {
				properties.setGameRule("spawnOnDeath", world.getName());
			}
		}
		
		if(!properties.getGameRule("doWeatherCycle").isPresent()) {
			properties.setGameRule("doWeatherCycle", "true");
		}
		if(!properties.getGameRule("netherWorld").isPresent()) {
			properties.setGameRule("netherWorld", "DIM-1");
		}
		if(!properties.getGameRule("endWorld").isPresent()) {
			properties.setGameRule("endWorld", "DIM1");
		}
	}

	@Listener
	public void onDisplaceEntityEvent(DisplaceEntityEvent.Teleport event) {
		Entity entity = event.getTargetEntity();
		
		if(!(entity instanceof Player)) {
			return;
		}
		Player player = (Player) entity;

		if(!player.hasPermission("pjw.options.gamemode")) {
			return;
		}
		
		World from = event.getFromTransform().getExtent();
		World to = event.getToTransform().getExtent();
		
		WorldProperties properties = to.getProperties();
		
		if(!from.equals(to)) {
			if(!properties.getGameMode().equals(player.gameMode().get())) {
				player.offer(Keys.GAME_MODE, properties.getGameMode());
			}			
		}
	}

    @Listener
    public void onDamageEntityEvent(DamageEntityEvent event, @First EntityDamageSource damageSource) {
    	if(!(event.getTargetEntity() instanceof Player)) {
    		return;
    	}
    	Player victim = (Player) event.getTargetEntity();

        Entity source = damageSource.getSource();
        if (!(source instanceof Player)) {
        	return;
        }

        World world = victim.getWorld();
		WorldProperties properties = world.getProperties();

		if(!properties.isPVPEnabled() && !victim.hasPermission("pjw.options.pvp")) {
			event.setCancelled(true);
		}
    }

	@Listener
	public void onChangeWorldWeatherEvent(ChangeWorldWeatherEvent event) {
		World world = event.getTargetWorld();
		WorldProperties properties = world.getProperties();

		if(properties.getGameRule("doWeatherCycle").get().equalsIgnoreCase("false")) {
			event.setCancelled(true);
		}
	}
	
	@Listener
	public void onRespawnPlayerEvent(RespawnPlayerEvent event) {
		if(!(event.getTargetEntity() instanceof Player)) {
			return;
		}
		
		World world = event.getFromTransform().getExtent();
		WorldProperties properties = world.getProperties();
		
		String worldName = properties.getGameRule("spawnOnDeath").get();
		
		Optional<World> optionalSpawnWorld = Main.getGame().getServer().getWorld(worldName);
		
		if(!optionalSpawnWorld.isPresent()) {
			return;
		}
		World spawnWorld = optionalSpawnWorld.get();
		
		Transform<World> transform = event.getToTransform().setLocation(spawnWorld.getSpawnLocation());
		event.setToTransform(transform);
	}
}
