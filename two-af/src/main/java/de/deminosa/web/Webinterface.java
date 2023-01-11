package de.deminosa.web;

import java.util.ArrayList;
import java.util.List;

import de.deminosa.web.response.QueryResponse;

public class Webinterface {
    
    private static WebServerManager webServerManager;
	private static int port;

    private final List<QueryResponse> queryResponse;

    public Webinterface() {
        queryResponse = new ArrayList<>();
    }

    public void onEnable() {
		port = 20000;
		webServerManager = new WebServerManager(port);
		webServerManager.start();
	}

    public void onDisable() {
        webServerManager.stop();
    }

    public static WebServerManager getWebServerManager() {
		return webServerManager;
	}
	
	public static int getCurrentPort() {
		return port;
	}
	
	public void addQueryResponse(QueryResponse query) {
		if(!queryResponse.contains(query)) {
			queryResponse.add(query);
		}
	}

	public List<QueryResponse> getQueryResponse() {
		return queryResponse;
	}

}
