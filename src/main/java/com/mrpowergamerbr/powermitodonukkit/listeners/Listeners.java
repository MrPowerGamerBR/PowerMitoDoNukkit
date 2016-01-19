package com.mrpowergamerbr.powermitodonukkit.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.mrpowergamerbr.powermitodonukkit.PowerMitoDoNukkit;
import com.mrpowergamerbr.powermitodonukkit.api.PowerMitoAPI;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityData;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.utils.TextFormat;

public class Listeners implements Listener {
	PowerMitoDoNukkit m;
	HashMap<Player, Player> lastHit = new HashMap<Player, Player>();

	public Listeners(PowerMitoDoNukkit m) {
		this.m = m;
		m.getServer().getPluginManager().registerEvents(this, m);
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player killer = (Player) e.getDamager();
			Player victim = (Player) e.getEntity();
			lastHit.put(victim, killer);
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e1) {
		Player killer = lastHit.get(e1.getEntity());
		Player victim = (Player) e1.getEntity();

		if (victim.getName().equals(PowerMitoAPI.getMito())) {
			if ((boolean) m.asriel.get("Mito.BloquearMundos")) {
				ArrayList<String> mundos = (ArrayList<String>) m.asriel.get("Mito.MundosBloqueados");
				if (mundos.contains(victim.getLevel().getName())) {
					return;
				}
			}
			if ((boolean) m.asriel.get("Mito.AnunciarAoMudarDeMito")) {
				String s = (String) m.asriel.get("Mensagens.AnuncioAoMudar");
				String prefixo = (String) m.asriel.get("Mensagens.Prefixo");
				prefixo = TextFormat.colorize(prefixo);

				s = TextFormat.colorize(s);
				s = s.replace("{player}", killer.getName());
				s = s.replace("{display_name}", killer.getDisplayName());
				s = s.replace("{old_mito}", PowerMitoAPI.getMito());

				m.getServer().broadcastMessage(prefixo + s);
			}

			if ((boolean) m.asriel.get("Mito.RodarComandos")) {
				ArrayList<String> comandos = (ArrayList<String>) m.asriel.get("Mito.Comandos");

				for (String cmd : comandos) {
					cmd = cmd.replace("{player}", killer.getName());
					cmd = cmd.replace("{old_mito}", PowerMitoAPI.getMito());

					m.getServer().dispatchCommand(m.getServer().getConsoleSender(), cmd);
				}

			}

			if ((boolean) m.asriel.get("Mito.Raios")) {
				AddEntityPacket aep = new AddEntityPacket();
				aep.type = 93;
				aep.eid = Entity.entityCount + 1;
				aep.metadata = new HashMap<Integer, EntityData>();
				aep.speedX = 0;
				aep.speedY = 0;
				aep.speedZ = 0;
				aep.yaw = (float) killer.getYaw();
				aep.pitch = (float) killer.getPitch();
				aep.x = (float) killer.x;
				aep.y = (float) killer.y;
				aep.z = (float) killer.z;
				for(Entry<String, Player> p : m.getServer().getOnlinePlayers().entrySet()) {
					p.getValue().dataPacket(aep);
				}
			}
		}

		PowerMitoAPI.setarMito(killer.getName());
	}

	@EventHandler
	public void onConnect(PlayerJoinEvent e) {
		if (PowerMitoAPI.getMito().equals(e.getPlayer().getName()) && (boolean) m.asriel.get("Mito.AnunciarAoEntrar")) {
			String s = (String) m.asriel.get("Mensagens.AnuncioAoEntrar");
			String prefixo = (String) m.asriel.get("Mensagens.Prefixo");

			prefixo = TextFormat.colorize(prefixo);

			s = TextFormat.colorize(s);
			s = s.replace("{player}", e.getPlayer().getName());
			s = s.replace("{display_name}", e.getPlayer().getDisplayName());

			m.getServer().broadcastMessage(prefixo + s);
		}
	}

	@EventHandler
	public void onDisconnect(PlayerQuitEvent e) {
		if (PowerMitoAPI.getMito().equals(e.getPlayer().getName()) && (boolean) m.asriel.get("Mito.AnunciarAoSair")) {
			String s = (String) m.asriel.get("Mensagens.AnuncioAoSair");
			String prefixo = (String) m.asriel.get("Mensagens.Prefixo");

			prefixo = TextFormat.colorize(prefixo);

			s = TextFormat.colorize(s);
			s = s.replace("{player}", e.getPlayer().getName());
			s = s.replace("{display_name}", e.getPlayer().getDisplayName());

			m.getServer().broadcastMessage(prefixo + s);
		}
	}
}
