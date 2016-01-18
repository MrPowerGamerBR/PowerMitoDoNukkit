package com.mrpowergamerbr.powermitodonukkit.api;

import com.mrpowergamerbr.powermitodonukkit.PowerMitoDoNukkit;

public class PowerMitoAPI {
	static PowerMitoDoNukkit m;
	
	public PowerMitoAPI(PowerMitoDoNukkit m) {
		PowerMitoAPI.m = m;
	}
	public static void setarMito(String s) {
		m.mitoAtual = s;
	}
	
	public static String getMito() {
		return m.mitoAtual;
	}
}
