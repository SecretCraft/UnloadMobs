package com.github.secretcraft.java;

import java.util.logging.Logger;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class UnloadMobs extends JavaPlugin {
	
	private ChunkListener listener;
	private Configuration config;
	Logger log;
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		log = getLogger();
		
		config = this.getConfig();
		
		if(config.get("enable") == null) {
			initializeConfig();
		}
		
		this.reloadConfig();
		
		listener = new ChunkListener(config);
		
		if(config.getBoolean("enable") == true) {
			this.getServer().getPluginManager().registerEvents(listener, this);
			log.info("UnloadMobs enabled!");
		} else {
			log.info("UnloadMobs disabled!");
			this.getPluginLoader().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		this.listener = null;
		log.info("UnloadMobs disabled!");
	}
	
	private void initializeConfig() {
		
		config.addDefault("enable", false);
		config.addDefault("animals-per-chunk", 20);
		config.options().copyDefaults(true);
		this.saveConfig();
		
	}
	
}
