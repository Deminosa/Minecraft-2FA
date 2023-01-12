package de.deminosa.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.deminosa.App;
import de.deminosa.web.response.QueryResponse;

public class Webinterface implements HttpHandler{
    
    private final WebServerManager webServerManager;
	private final int port;

    private final List<QueryResponse> queryResponse;

    public Webinterface() {
        queryResponse = new ArrayList<>();
        port = 20000;
		webServerManager = new WebServerManager(port);
    }

    public void onEnable() {
		webServerManager.start();

        loadFiles();
	}

    public void onDisable() {
        webServerManager.stop();
    }

    public WebServerManager getWebServerManager() {
		return webServerManager;
	}
	
	public int getCurrentPort() {
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

    private void loadFiles() {
        File dir = new File(App.getInstance().getDataFolder() + "//webpages//");
        if(!dir.getParentFile().exists()) {
            dir.mkdirs();
        }

        if(!dir.exists()) {
            dir.mkdir();
        }

        File[] files = dir.listFiles();
        if(files == null) return;

        for(int i = 0; i < files.length; i++){
            if(!files[i].isDirectory()){
                /* TODO: Why VSCode?
                 * 
                 * The method getServer() from the type WebServerManager refers to the missing type HttpServer // Java(67108984)
                 */
                webServerManager.getServer().createContext("/" + files[i].getName(), this);
            }
        }
    }

    @Override
    public void handle(HttpExchange arg0) throws IOException {
        
    }


}
