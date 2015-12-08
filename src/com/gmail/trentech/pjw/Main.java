package com.gmail.trentech.pjw;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.gmail.trentech.pjw.managers.CommandManager;
import com.gmail.trentech.pjw.managers.ConfigManager;
import com.gmail.trentech.pjw.managers.EventManager;
import com.gmail.trentech.pjw.utils.Resource;
import com.gmail.trentech.pjw.utils.TimeLock;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

@Plugin(id = Resource.ID, name = Resource.NAME, version = Resource.VERSION)
public class Main {

	private static Game game;
	private static Logger log;
	private static PluginContainer plugin;

	@Listener
    public void onPreInitialization(GamePreInitializationEvent event) {
		game = event.getGame();
		plugin = event.getGame().getPluginManager().getPlugin(Resource.ID).get();
		log = event.getGame().getPluginManager().getLogger(plugin);
		
    }

    @Listener
    public void onInitialization(GameInitializationEvent event) {
    	getGame().getEventManager().registerListeners(this, new EventManager());
    	getGame().getCommandManager().register(this, new CommandManager().cmdWorld, "world", "w");
    }

    @Listener
    public void onPostInitialization(GamePostInitializationEvent event) {

    }

    @Listener
    public void onStartedServer(GameStartedServerEvent event) {
    	getLog().info("Initializing...");
    	
    	ConfigManager configManager = new ConfigManager();
    	configManager.init();
    	
    	loadWorlds();
    	
    	new TimeLock().start();
    }

    @Listener
	public void onStoppingServer(GameStoppingServerEvent event) {
    	
	}
    
    @Listener
    public void onStoppedServer(GameStoppedServerEvent event) {

    }

    public static Logger getLog() {
        return log;
    }
    
	public static Game getGame() {
		return game;
	}

	public static PluginContainer getPlugin() {
		return plugin;
	}
	
	private void loadWorlds(){
		getLog().info("Loading worlds...");
		
		Map<Object, ? extends CommentedConfigurationNode> worlds = new ConfigManager().getConfig().getNode("Worlds").getChildrenMap();
		for(Entry<Object, ? extends CommentedConfigurationNode> item : worlds.entrySet()){
			String key = item.getKey().toString();
			getGame().getServer().loadWorld(key);
		}
	}
}