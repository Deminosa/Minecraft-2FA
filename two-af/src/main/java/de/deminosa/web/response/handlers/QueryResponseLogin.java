package de.deminosa.web.response.handlers;

import java.util.HashMap;

import de.deminosa.web.response.QueryResponse;

public class QueryResponseLogin implements QueryResponse{

    @Override
    public void incomingResponse(HashMap<String, String> map) {
        String name = map.get("username");
        String code = map.get("code");
        System.out.print("[Webinterface] new Login / Auth from " + name + " with code " + code);
    }
    
}
