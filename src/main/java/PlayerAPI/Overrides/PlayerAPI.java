package PlayerAPI.Overrides;

import NukkitDB.NukkitDB;
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
        String database = getPlugin("PlayerAPI").getConfig().getString("database");
        String collection = getPlugin("PlayerAPI").getConfig().getString("collection");

        if (NukkitDB.query(name, "defaultName", database, collection) != null) {
            return NukkitDB.query(name, "defaultName", database, collection);
        }

        if (NukkitDB.query(name, "alias", database, collection) != null) {
            return NukkitDB.query(name, "alias", database, collection);
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
        String database = getPlugin("PlayerAPI").getConfig().getString("database");
        String collection = getPlugin("PlayerAPI").getConfig().getString("collection");
        setNameTag(alias);
        setDisplayName(alias);
        NukkitDB.updateDocument(
                getUuid(), "uuid", "alias", alias, database, collection
        );
    }

    public String getAlias() {
        String database = getPlugin("PlayerAPI").getConfig().getString("database");
        String collection = getPlugin("PlayerAPI").getConfig().getString("collection");
        return NukkitDB.query(
                getUuid(), "uuid", database, collection
        ).get("alias").toString();
    }

    @Override
    public String toString() {
        return "PlayerAPI(name='" + this.getAlias() + "', location=" + super.toString() + ')';
    }

}
