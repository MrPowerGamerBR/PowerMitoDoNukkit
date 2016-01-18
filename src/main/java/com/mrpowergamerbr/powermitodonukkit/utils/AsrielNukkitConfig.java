package com.mrpowergamerbr.powermitodonukkit.utils;

import java.util.HashMap;

import com.mrpowergamerbr.powermitodonukkit.PowerMitoDoNukkit;

public class AsrielNukkitConfig {
	/*
	 * Asriel Nukkit "Dreemurr" Config - A way "like a dream" to load values from config without setting values!
	 * 
	 * Created by MrPowerGamerBR 2016
	 */
	PowerMitoDoNukkit m;
	
	public AsrielNukkitConfig(PowerMitoDoNukkit m) {
		this.m = m;
	}
	
	HashMap<String, Object> cache = new HashMap<String, Object>();
	
	public Object get(String value) {
		if (cache.containsKey(value)) {
			return cache.get(value);
		} else {
			cache.put(value, m.getConfig().getNested(value));
			return cache.get(value);
		}
	}
	
	public void resetToReload() {
		m.reloadConfig();
		
		cache.clear();
	}
}
