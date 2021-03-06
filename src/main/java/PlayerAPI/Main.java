package PlayerAPI;

import NukkitDB.NukkitDB;
import NukkitDB.Provider.MongoDB;
import PlayerAPI.Overrides.PlayerAPI;
import cn.nukkit.command.CommandMap;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCreationEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

public class Main extends PluginBase implements Listener {

    @Override
    public void onLoad() {
        CommandMap commandMap = getServer().getCommandMap();
        commandMap.register("PlayerAPI", new Commands("alias", this));
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);

        if (!getConfig().getBoolean("use-MongoDB") || getConfig().getString("collection").isEmpty()) {
            getLogger().error("please edit config.");
            getServer().getPluginManager().disablePlugin(this);
        }

    }

    @EventHandler()
    public void onEvent(PlayerCreationEvent event) {
        event.setPlayerClass(PlayerAPI.class);
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        PlayerAPI player = (PlayerAPI) event.getPlayer();
        String uuid = player.getUuid();
        String collection = this.getConfig().getString("collection");

        Map<String, Object> query = MongoDB.getDocument(
                MongoDB.getCollection(collection), "uuid", uuid
        );

        if (query == null) {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("uuid", uuid);
            objectMap.put("defaultName", player.getName());
            objectMap.put("alias", player.getName());
            MongoDB.insertOne(
                    objectMap, MongoDB.getCollection(collection)
            );
        } else {
            String alias = query.get("alias").toString();
            player.setNameTag(alias);
            player.setDisplayName(alias);
            event.setJoinMessage(TextFormat.YELLOW + alias + " joined the game");
        }
    }

}
