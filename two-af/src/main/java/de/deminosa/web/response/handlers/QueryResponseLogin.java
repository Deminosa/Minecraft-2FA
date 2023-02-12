package de.deminosa.web.response.handlers;

import java.util.HashMap;

import de.deminosa.bungeecord.BungeeApp;
import de.deminosa.bungeecord.databasesystem.DataBaseSystem;
import de.deminosa.utils.mojang.MojangAPI;
import de.deminosa.utils.mojang.sessionserver.MojangName;
import de.deminosa.utils.mysql.builder.ColumValue;
import de.deminosa.utils.mysql.builder.Search;
import de.deminosa.utils.mysql.builder.Update;
import de.deminosa.web.response.QueryResponse;

public class QueryResponseLogin implements QueryResponse{

    String wrong = """
        <div class="red">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        Somthing is Wrong!
        </div>
            """;
    String suc = """
        <div class="green">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        Authentication successful!
        </div>
            """;

    @Override
    public String incomingResponse(HashMap<String, String> map, String response) {
       
        if(map == null) {
            response = response.replace("%state%", "");
            return response;
        } 

        String type = map.get("type");

        if(!type.equalsIgnoreCase("login")) return response;

        String name = map.get("username");
        String code = map.get("code");

        MojangAPI mojang = BungeeApp.getInstance().getMojangAPI();
        String playerUUID = mojang.getSessionServer().getUUID(new MojangName(name));

        if(playerUUID == null || playerUUID == "null") {
            BungeeApp.log("Login Fail for " + name + " user not found in Session Server!");
            response = response.replace("%state%", wrong);
            return response;
        }

        DataBaseSystem db = BungeeApp.getInstance().getDataBaseSystem();
        Search search = new Search("UUID", playerUUID);
        
        if(!db.getTable().exsistValue(new Update(search, new ColumValue("UUID", playerUUID)))) {
            BungeeApp.log("Login Fail for " + name + " user not registert!");
            response = response.replace("%state%", wrong);
            return response;
        }

        String key = db.getTable().getValue(new Update(search, new ColumValue("TOKEN_KEY", "null")), String.class, "null");

        if(BungeeApp.getInstance().getAuthManager().getTOTPCode(key).equals(code)) {
            BungeeApp.log("Login successful for " + name);
            response = response.replace("%state%", suc);
            return response;
        }

        BungeeApp.log("Somthing is wrong!");
        response = response.replace("%state%", wrong);

        return response;
    }
    
}
