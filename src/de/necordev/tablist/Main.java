package de.necordev.tablist;


import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	
	}
	
	 @EventHandler
	 public void onJoin(PlayerJoinEvent e) {
		 sendTablistHeaderAndFooter(e.getPlayer(),
				 "§6YOUR HEADER TEXT", "§6YOUR FOOTER TEXT");
	 }
	
	public void sendTablistHeaderAndFooter(Player p, String header, String footer) {
		if(header == null) header = "";
		if(footer == null) footer = "";
		
		IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\":\"" + header + "\"}");
		IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\":\"" + footer + "\"}");
	
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabHeader);
		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFooter);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(headerPacket);
		}
	}
	
}	
