package com.mrpowergamerbr.powermitodonukkit.utils;

import java.net.URL;
import java.util.Map.Entry;
import java.util.Scanner;

import com.mrpowergamerbr.powermitodonukkit.PowerMitoDoNukkit;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.LogLevel;

public class TemmieNukkitUpdater implements Listener {
	/*
	 * TemmieUpdater - hOI! Im TEMMIE!!!
	 * 
	 * Created by MrPowerGamerBR 2016
	 */
	PowerMitoDoNukkit m;
	String newVersion = "";

	public TemmieNukkitUpdater(PowerMitoDoNukkit m) {
		this.m = m;
		m.getServer().getPluginManager().registerEvents(this, m);
		startUpdater();
	}

	public void startUpdater() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Scanner updater;
					if ((boolean) m.asriel.get("TemmieUpdater.TemmieMetrics")) {
						updater = new Scanner((new URL("http://plugins.mrpowergamerbr.com/updater?plugin=" + PowerMitoDoNukkit.pluginName + "&port=" + m.getServer().getPort() + "&ver=" + getVersion() + "&plugins=" + getPlugins())).openStream());
					} else {
						updater = new Scanner((new URL("http://plugins.mrpowergamerbr.com/updater?plugin=" + PowerMitoDoNukkit.pluginName + "&port=" + m.getServer().getPort())).openStream());
					}
					m.getServer().getLogger().log(LogLevel.INFO, "[" + PowerMitoDoNukkit.pluginName + "] Verificando atualizações...");

					boolean update = false;
					while (updater.hasNextLine()) {
						String line = updater.nextLine();
						if (line.equals(PowerMitoDoNukkit.version)) {
							break;
						} else {
							update = true;
							newVersion = line;
						}
					}
					updater.close();

					if (update) {
						m.getServer().getLogger().log(LogLevel.INFO, "[" + PowerMitoDoNukkit.pluginName + "] Uma nova atualização para o " + PowerMitoDoNukkit.pluginName + " está disponível! (" + newVersion + ")");
					} else {
						m.getServer().getLogger().log(LogLevel.INFO, "[" + PowerMitoDoNukkit.pluginName + "] Você está na última versão do " + PowerMitoDoNukkit.pluginName);
					}
				} catch (Exception e) {
					m.getServer().getLogger().log(LogLevel.INFO, "[" + PowerMitoDoNukkit.pluginName + "] Um problema ocorreu ao tentar verificar updates.");
				}
			}
		});
		t.start();
	}

	public String getVersion() {
		return m.getServer().getVersion();
	}

	public String getPlugins() {
		String plugins = "";
		for (Entry<String, Plugin> p : m.getServer().getPluginManager().getPlugins().entrySet()) {
			if (plugins.equals("")) {
				plugins = p.getKey();
			} else {
				plugins = plugins + "," + p.getKey();
			}
		}
		return plugins;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission(PowerMitoDoNukkit.pluginName + ".ReloadConfig")) {
			if (!newVersion.equals("")) {
				e.getPlayer().sendMessage("§7[§b§l" + PowerMitoDoNukkit.pluginName + "§7] §6Uma nova atualização para o " + PowerMitoDoNukkit.pluginName + " está disponível! (" + newVersion + ")");
			}
		}
	}
}