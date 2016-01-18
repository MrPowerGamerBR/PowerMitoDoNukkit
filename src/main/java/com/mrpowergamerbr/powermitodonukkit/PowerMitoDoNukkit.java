package com.mrpowergamerbr.powermitodonukkit;

import com.mrpowergamerbr.powermitodonukkit.api.PowerMitoAPI;
import com.mrpowergamerbr.powermitodonukkit.listeners.Listeners;
import com.mrpowergamerbr.powermitodonukkit.utils.AsrielNukkitConfig;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class PowerMitoDoNukkit extends PluginBase {
	public AsrielNukkitConfig asriel;
	
    String helloMessage;

	public String mitoAtual = "Ningu�m";

    private static PowerMitoDoNukkit plugin;

    public static PowerMitoDoNukkit getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable(){
        plugin = this;
        
        this.initConfig();
        this.loadCfg();
        
        asriel = new AsrielNukkitConfig(this);
        
        new Listeners(this);
        new PowerMitoAPI(this);
        
        if (getConfig().exists("MitoAtual")) {
        	mitoAtual = (String) getConfig().get("MitoAtual");
        }
    }

    @Override
    public void onDisable() {
    	getConfig().set("MitoAtual", mitoAtual);
    	
    	saveConfig();
    }
    
    public void initConfig(){
        this.getDataFolder().mkdirs();
        this.saveResource("config.yml");
    }

    public void loadCfg(){
        this.reloadConfig();
    }
    
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg1.getName().equalsIgnoreCase("mito")) {
			String s = (String) asriel.get("Mensagens.MitoAtual");
			String prefixo = (String) asriel.get("Mensagens.Prefixo");

			prefixo = TextFormat.colorize(prefixo);

			s = TextFormat.colorize(s);
			System.out.println(PowerMitoAPI.getMito());
			s = s.replace("{mito}", PowerMitoAPI.getMito());

			arg0.sendMessage(prefixo + s);
			return true;
		}

		if (arg1.getName().equalsIgnoreCase("setmito")) {
			if (arg0.hasPermission("PowerMitoDoPvP.SetMito")) {
				if (arg3.length == 1) {
					PowerMitoAPI.setarMito(arg3[0]);
					String s = (String) asriel.get("Mensagens.MitoAlterado");

					s = TextFormat.colorize(s);

					arg0.sendMessage(s);
				} else {
					String s = (String) asriel.get("Mensagens.ComandoSetMito");

					s = TextFormat.colorize(s);

					arg0.sendMessage(s);
				}
			} else {
				String s = (String) asriel.get("Mensagens.SemPermissao");

				s = TextFormat.colorize(s);

				arg0.sendMessage(s);
			}
			return true;
		}

		if (arg1.getName().equalsIgnoreCase("powermitodopvp")) {
			if (arg0.hasPermission("PowerMitoDoPvP.RecarregarConfig")) {
				asriel.resetToReload();
				arg0.sendMessage("�aConfig recarregada!");
			} else {
				arg0.sendMessage("�6�lPowerMitoDoNukkit �6v1.0.0 �8- �3Criado por �bMrPowerGamerBR");
				arg0.sendMessage("�eWebsite:�6 http://mrpowergamerbr.com/");
				arg0.sendMessage("�eSparklyPower:�6 http://sparklypower.net/");
			}
			return true;
		}
		return false;
	}
}