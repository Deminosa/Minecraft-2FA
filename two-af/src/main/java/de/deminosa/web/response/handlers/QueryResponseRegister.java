package de.deminosa.web.response.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.deminosa.App;
import de.deminosa.web.response.QueryResponse;

public class QueryResponseRegister implements QueryResponse{

    @Override
    public String incomingResponse(HashMap<String, String> map, String response) {
        String type = map.get("type");

        if(!type.equalsIgnoreCase("reg")) return response;

        String name = map.get("username");
        String code = map.get("code");
        System.out.print("[Webinterface] new Register from " + name + " with Token " + code);

        UUID uuid = null;
        try{
            uuid = UUID.fromString(code);
        }catch (Exception e){
            response = response.replace("%state%", "Somthing is Wrong!");
            return response;
        }

        response = response.replace("%state%", "");
        String key = App.getInstance().getAuthManager().generateSecretKey();
        String barcode = App.getInstance().getAuthManager().getGoogleAuthenticatorBarCode(key, name, "Minecraft 2FA");

        response = response.replace("%QR-Code%", App.getInstance().getAuthManager().createQRCode(barcode, 64*4, 64*4));
        return response;
    }
    
}
