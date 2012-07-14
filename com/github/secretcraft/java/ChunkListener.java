package com.github.secretcraft.java;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkListener implements Listener {
	
	private int animalsPerChunk;
	private List<Animals> animals = new LinkedList<Animals>();
	
	public ChunkListener(Configuration config) {
		animalsPerChunk = config.getInt("animals-per-chunk");
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		
		Entity[] chunkEntities = event.getChunk().getEntities();
		
		for(int i = 0; i < chunkEntities.length; i++) {
			
			if(chunkEntities[i] instanceof Animals) {
				animals.add((Animals)chunkEntities[i]);
			} else {
				if(chunkEntities[i] instanceof Monster) {
					Monster monster = (Monster)chunkEntities[i];
					monster.setHealth(0);
				}
				if(chunkEntities[i] instanceof Slime) {
					Slime slime = (Slime)chunkEntities[i];
					slime.setHealth(0);
				}
				if(chunkEntities[i] instanceof Ghast) {
					Ghast ghast = (Ghast)chunkEntities[i];
					ghast.setHealth(0);
				}
			}
		}
		
		if(animals.size() > animalsPerChunk) {
			int max = animals.size()-animalsPerChunk;
			for(int i = 0; i < max; i++) {
				Animals a = animals.get(i);
				a.setHealth(0);
			}
		}
		
	}
	
}
