package de.deminosa.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.deminosa.bungeecord.BungeeApp;
import de.deminosa.web.response.QueryResponse;
import de.deminosa.web.response.handlers.QueryResponseLogin;
import de.deminosa.web.response.handlers.QueryResponseRegister;

public class Webinterface implements HttpHandler{
    
    private final WebServerManager webServerManager;
	private final int port;

    private final List<QueryResponse> queryResponseList;

    public Webinterface() {
        queryResponseList = new ArrayList<>();
        port = 20000;
		webServerManager = new WebServerManager(port);

		addQueryResponse(new QueryResponseRegister());
		addQueryResponse(new QueryResponseLogin());
    }

    public void onEnable() {
		loadFiles();
		webServerManager.start();
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
		if(!queryResponseList.contains(query)) {
			queryResponseList.add(query);
		}
	}

	public List<QueryResponse> getQueryResponse() {
		return queryResponseList;
	}

    private void loadFiles() {
        File dir = new File(BungeeApp.getInstance().getDataFolder() + "/webpages/");
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
                webServerManager.getServer().createContext("/" + files[i].getName(), this);
            }
        }
    }

    private String getFileContents(String filenname) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(BungeeApp.getInstance().getDataFolder() + "/webpages/" + filenname));
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();
			}
            bufferedReader.close();
			return stringBuilder.toString();
		}catch (Exception e) {
			return "<h1>ERROR</h1><p>Please check the URL!<br>"
					+ "<a href=\"/index.html\">Back to Main Page</a></p><hr />"
					+ "<p>" + e.fillInStackTrace() + "</p>";
		}
	}

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = getFileContents(httpExchange.getRequestURI().getPath().toString());

        if(httpExchange.getRequestURI().getQuery() != null && !queryResponseList.isEmpty()){
            String queryResponse = httpExchange.getRequestURI().getQuery();
			BungeeApp.log("Get query response!");

            HashMap<String, String> map = new HashMap<>();
			String[] args = queryResponse.split("&");
				
			for(int i = 0; i < args.length; i++) {
				String[] raw = args[i].split("=");
				String key = raw[0];
				String value = raw[1];
				
				map.put(key, value);
			}
				
			for(QueryResponse qr : queryResponseList) {
				response = qr.incomingResponse(map, response);
			}
        }else {
			for(QueryResponse qr : queryResponseList) {
				response = qr.incomingResponse(null, response);
			}
		}

        httpExchange.sendResponseHeaders(200, response.getBytes().length);

		OutputStream outputStream = httpExchange.getResponseBody();
		outputStream.write(response.getBytes());
		outputStream.close();
    }


}
