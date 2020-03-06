package PlayerAPI.Overrides;

import NukkitDB.NukkitDB;
import NukkitDB.Provider.MongoDB;
import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.plugin.Plugin;

import java.util.Map;

public class PlayerAPI extends Player implements IPlayer {

    public PlayerAPI(SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);
    }

    public static PlayerAPI get(String name) {
        PlayerAPI found = null;
        name = name.toLowerCase();
        int delta = 2147483647;
        for (Player value : Server.getInstance().getOnlinePlayers().values()) {
            PlayerAPI player = (PlayerAPI) value;
            if (player.getName().toLowerCase().startsWith(name)) {
                int curDelta = player.getName().length() - name.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) {
                    break;
                }
            } else if (player.getAlias().toLowerCase().startsWith(name)) {
                int curDelta = player.getAlias().length() - name.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) {
                    break;
                }
            }
        }
        return found;
    }

    public static Map<String, Object> getOfflinePlayer(String name) {
        String collection = getPlugin("PlayerAPI").getConfig().getString("collection");

        if (MongoDB.getDocument(MongoDB.getCollection(collection), "defaultName", name) != null) {
            return MongoDB.getDocument(MongoDB.getCollection(collection), "defaultName", name);
        }

        if (MongoDB.getDocument(MongoDB.getCollection(collection), "alias", name) != null) {
            return MongoDB.getDocument(MongoDB.getCollection(collection), "alias", name);
        }

        return null;
    }

    public static Plugin getPlugin(String name) {
        return Server.getInstance().getPluginManager().getPlugin(name);
    }

    public String getUuid() {
        return this.getUniqueId().toString();
    }

    public void setAlias(String alias) {
        String collection = getPlugin("PlayerAPI").getConfig().getString("collection");
        setNameTag(alias);
        setDisplayName(alias);
        MongoDB.updateOne(
                MongoDB.getCollection(collection), "uuid", getUuid(), "alias", alias
        );
    }

    public String getAlias() {
        String collection = getPlugin("PlayerAPI").getConfig().getString("collection");
        return MongoDB.getDocument(
                MongoDB.getCollection(collection), "uuid", getUuid()
        ).get("alias").toString();
    }

    @Override
    public String toString() {
        return "PlayerAPI(name='" + this.getAlias() + "', location=" + super.toString() + ')';
    }

}
