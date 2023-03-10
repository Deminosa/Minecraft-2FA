package de.deminosa.web;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import de.deminosa.bungeecord.BungeeApp;

public class WebServerManager {
    private HttpServer server;
	
	public WebServerManager(int port) {
		BungeeApp.log("Loading Webinterface...");
		try {
			String ip = BungeeApp.getInstance().getConfig().ReadString("config", "ip", "127.0.0.1");
			server = HttpServer.create(new InetSocketAddress(ip, port), 0);
		} catch (IOException e) {
			e.printStackTrace();
			BungeeApp.log("[ERROR] Fail to start the HTTP Server!");
			BungeeApp.log("[E-LOG] " + e.fillInStackTrace());
		}
	}
	
	public void start() {
		BungeeApp.log("Start Webinterface...");
		server.start();
		BungeeApp.log("Webinterface can fond at: http:/" + server.getAddress());
	}
	
	public void stop() {
		server.stop(0);
	}
	
	public HttpServer getServer() {
		return server;
	}
}
