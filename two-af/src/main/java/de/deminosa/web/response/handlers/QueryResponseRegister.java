package de.deminosa.web.response.handlers;

import java.util.HashMap;
import java.util.UUID;

import de.deminosa.bungeecord.BungeeApp;
import de.deminosa.bungeecord.databasesystem.DataBaseSystem;
import de.deminosa.utils.mojang.MojangAPI;
import de.deminosa.utils.mojang.sessionserver.MojangName;
import de.deminosa.utils.mysql.builder.ColumValue;
import de.deminosa.utils.mysql.builder.Search;
import de.deminosa.utils.mysql.builder.Update;
import de.deminosa.web.response.QueryResponse;

public class QueryResponseRegister implements QueryResponse{

    String QR_CODE = """
        <img src="%QR-Code%"></img>
        """;

    String registerContent = """
        %state%
        %image%
            """;

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
            response = response.replace("%content%", "");
            return response;
        }

        String type = map.get("type");
        if(!type.equalsIgnoreCase("reg")) return response;

        response = response.replace("%content%", registerContent);
        String name = map.get("username");
        String code = map.get("code");
        System.out.print("[Webinterface] new Register from " + name + " with Token " + code);

        UUID uuid = null;
        try{
            uuid = UUID.fromString(code);
        }catch (Exception e){
            response = response.replace("%state%", wrong);
            response = response.replace("%image%", "");
            return response;
        }

        DataBaseSystem db = BungeeApp.getInstance().getDataBaseSystem();
        Search search = new Search("TOKEN", uuid.toString());
        ColumValue tokenValue = new ColumValue("TOKEN", uuid.toString());

        // Checking if Create Token exsist
        if(db.getCreateTable().exsistValue(new Update(search, tokenValue))){
            MojangAPI mojang = BungeeApp.getInstance().getMojangAPI();
            String playerUUID = mojang.getSessionServer().getUUID(new MojangName(name));

            // Checking if Player exsist
            if(playerUUID == null || playerUUID == "null"){
                response = response.replace("%state%", wrong);
                response = response.replace("%image%", "");
                return response;
            }
            response = response.replace("%state%", "");
            String key = BungeeApp.getInstance().getAuthManager().generateSecretKey();
            String barcode = BungeeApp.getInstance().getAuthManager().getGoogleAuthenticatorBarCode(key, name, "Minecraft 2FA");

            // remove create token
            db.getCreateTable().deletRow(search);

            // set key
            db.getTable().setFirstColum(new ColumValue("UUID", playerUUID));
            db.getTable().updateColum(new Update(new Search("UUID", playerUUID), new ColumValue("TOKEN_KEY", key)));
            
            // create QR-Code
            response = response.replace("%image%", QR_CODE);
            response = response.replace("%QR-Code%", BungeeApp.getInstance().getAuthManager().createQRCode(barcode, 64*4, 64*4));
        }else {
            response = response.replace("%image%", "");
            response = response.replace("%state%", wrong);
        }

        return response;
    }
    
}
