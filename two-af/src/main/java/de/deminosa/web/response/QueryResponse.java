package de.deminosa.web.response;

import java.util.HashMap;

public interface QueryResponse {
    public String incomingResponse(HashMap<String, String> map, String response);
}
