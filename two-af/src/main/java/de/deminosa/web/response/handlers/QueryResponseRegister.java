package de.deminosa.web.response.handlers;

import java.util.HashMap;
import java.util.UUID;

import de.deminosa.bungeecord.BungeeApp;
import de.deminosa.bungeecord.databasesystem.DataBaseSystem;
import de.deminosa.utils.mysql.builder.ColumValue;
import de.deminosa.utils.mysql.builder.Search;
import de.deminosa.utils.mysql.builder.Update;
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

        DataBaseSystem db = BungeeApp.getInstance().getMysql().getDataBaseSystem();
        Search search = new Search("TOKEN", uuid.toString());
        ColumValue tokenValue = new ColumValue("TOKEN", uuid.toString());

        if(db.getCreateTable().exsistValue(new Update(search, tokenValue))){
            response = response.replace("%state%", "");
            String key = BungeeApp.getInstance().getAuthManager().generateSecretKey();
            String barcode = BungeeApp.getInstance().getAuthManager().getGoogleAuthenticatorBarCode(key, name, "Minecraft 2FA");

            response = response.replace("%QR-Code%", BungeeApp.getInstance().getAuthManager().createQRCode(barcode, 64*4, 64*4));
        }else {
            response = response.replace("%state%", "Token is invalid!");
        }

        
        return response;
    }
    
}
