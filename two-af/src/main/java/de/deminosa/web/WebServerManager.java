package de.deminosa.web;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class WebServerManager {
    private HttpServer server;
	
	public WebServerManager(int port) {
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		server.start();
	}
	
	public void stop() {
		server.stop(0);
	}
	
	public HttpServer getServer() {
		return server;
	}
}
