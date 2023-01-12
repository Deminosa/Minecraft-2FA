package de.deminosa.web;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import de.deminosa.App;

public class WebServerManager {
    private HttpServer server;
	
	public WebServerManager(int port) {
		App.log("Loading Webinterface...");
		try {
			server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
		} catch (IOException e) {
			e.printStackTrace();
			App.log("[ERROR] Fail to start the HTTP Server!");
			App.log("[E-LOG] " + e.fillInStackTrace());
		}
		
	}
	
	public void start() {
		App.log("Start Webinterface...");
		server.start();
		App.log("Webinterface can fond at: http:/" + server.getAddress());
	}
	
	public void stop() {
		server.stop(0);
	}
	
	public HttpServer getServer() {
		return server;
	}
}
