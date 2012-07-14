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

		for(Entity entity : chunkEntities) {

			if(entity instanceof Animals) {
				animals.add((Animals)entity);
			} else {
				if(entity instanceof Monster) {
					Monster monster = (Monster)entity;
					monster.remove();
				}
				if(entity instanceof Slime) {
					Slime slime = (Slime)entity;
					slime.remove();
				}
				if(entity instanceof Ghast) {
					Ghast ghast = (Ghast)entity;
					ghast.remove();
				}
			}
		}

		if(animals.size() > animalsPerChunk) {
			int max = animals.size()-animalsPerChunk;
			for(int i = 0; i < max; i++) {
				Animals a = animals.get(i);
				a.remove();
			}
		}

	}

}
