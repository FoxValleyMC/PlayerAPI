package PlayerAPI.Overrides;

import NetWorth.Overrides.NetWorthImpl;
import NukkitDB.NukkitDB;
import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.plugin.Plugin;
import com.steve.nukkit.AfterLife.utils.AfterlifeImpl;

public class PlayerAPI extends Player implements IPlayer, NetWorthImpl, AfterlifeImpl {

    public PlayerAPI(SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);
    }

    // mechanics
    public Plugin getPlugin(String name) {
        return this.getServer().getPluginManager().getPlugin(name);
    }

    public String getUuid() {
        return this.getUniqueId().toString();
    }

    @Override
    public String toString() {
        return "PlayerAPI(name='" + this.getName() + "', location=" + super.toString() + ')';
    }

    // PlayerAPI
    public void setAlias(String alias) {
        setNameTag(alias);
        setDisplayName(alias);
    }

    // NetWorth
    public int getMoney() {
        if (getPlugin("NetWorth") != null) {
            String database = NetWorth.Main.getInstance().getConfig().getString("database");
            String collection = NetWorth.Main.getInstance().getConfig().getString("collection");
            return Integer.parseInt(
                    NukkitDB.query(
                            getUuid(), "uuid", database, collection
                    ).get("balance").toString()
            );
        }
        return 0;
    }

    public void setMoney(int amount) {
        if (getPlugin("NetWorth") != null) {
            String database = NetWorth.Main.getInstance().getConfig().getString("database");
            String collection = NetWorth.Main.getInstance().getConfig().getString("collection");
            NukkitDB.updateDocument(
                    getUuid(), "uuid", "balance", amount, database, collection
            );
        }
    }

    public void addMoney(int amount) {
        if (getPlugin("NetWorth") != null) {
            String database = NetWorth.Main.getInstance().getConfig().getString("database");
            String collection = NetWorth.Main.getInstance().getConfig().getString("collection");
            int currentBalance = getMoney();
            int newBalance = currentBalance + amount;
            NukkitDB.updateDocument(
                    getUuid(), "uuid", "balance", newBalance, database, collection
            );
        }
    }

    public void subtractMoney(int amount) {
        if (getPlugin("NetWorth") != null) {
            String database = NetWorth.Main.getInstance().getConfig().getString("database");
            String collection = NetWorth.Main.getInstance().getConfig().getString("collection");
            int currentBalance = getMoney();
            int newBalance;
            if (currentBalance <= 0) {
                newBalance = 0;
            } else {
                newBalance = currentBalance - amount;
            }
            NukkitDB.updateDocument(
                    getUuid(), "uuid", "balance", newBalance, database, collection
            );
        }
    }

    // Afterlife
    public int getKills() {
        return 0;
    }

    public int getDeaths() {
        return 0;
    }

    public int getLevels() {
        return 0;
    }

    public int getNeededXp() {
        return 0;
    }

    public void addKill() {

    }

    public void addDeath() {

    }

    public void addXp(int amount) {

    }

    public void removeXp(int amount) {

    }

    public void addLevel() {

    }

    public void removeLevel() {

    }
}
