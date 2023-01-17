package de.deminosa.bungeecord.channel;

import java.util.Collection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.deminosa.bungeecord.BungeeApp;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PluginChannelManager {
    
    public static void sendData(String id, String data1, String data2) {
        Collection<ProxiedPlayer> networkPlayers = BungeeApp.getInstance().getProxy().getPlayers();

        if(networkPlayers == null || networkPlayers.isEmpty()) return;
        
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("2fa:"+id);
        out.writeUTF(data1);
        out.writeUTF(data2);

        networkPlayers.forEach(pp -> {
            pp.getServer().getInfo().sendData("2fa:"+id, out.toByteArray());
        });
    }

    /**
     * <p>0 = subchannel<br>
     * 1 = data1<br>
     * 2 = data2</p>
     * 
     * @param bytes
     * @return String[]
     */
    public static String[] getData(byte[] bytes) {
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

        String[] data = new String[3];
        data[0] = in.readUTF();
        data[1] = in.readUTF();
        data[2] = in.readUTF();

        return data;
    }

}
