package PlayerAPI.Overrides;

import NetWorth.Overrides.NetWorthImpl;
import NukkitDB.NukkitDB;
import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.plugin.Plugin;
import Afterlife.utils.AfterlifeImpl;

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

    // PlayerAPI - AliasPro
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
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            return Integer.parseInt(
                    NukkitDB.query(
                            getUuid(), "uuid", database, collection
                    ).get("kills").toString()
            );
        }
        return 0;
    }

    public int getKillStreak() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            return Integer.parseInt(
                    NukkitDB.query(
                            getUuid(), "uuid", database, collection
                    ).get("kill-streak").toString()
            );
        }
        return 0;
    }

    public int getDeaths() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            return Integer.parseInt(
                    NukkitDB.query(
                            getUuid(), "uuid", database, collection
                    ).get("deaths").toString()
            );
        }
        return 0;
    }

    public int getLevels() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            return Integer.parseInt(
                    NukkitDB.query(
                            getUuid(), "uuid", database, collection
                    ).get("levels").toString()
            );
        }
        return 0;
    }

    public int getXp() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            return Integer.parseInt(
                    NukkitDB.query(
                            getUuid(), "uuid", database, collection
                    ).get("global-xp").toString()
            );
        }
        return 0;
    }

    public int getNeededXp() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            return Integer.parseInt(
                    NukkitDB.query(
                            getUuid(), "uuid", database, collection
                    ).get("experience").toString()
            );
        }
        return 0;
    }

    public void addKill() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            int currentData = getKills();
            int updatedData = currentData + 1;
            int currentStreak = getKillStreak();
            int updatedStreak = currentStreak + 1;
            NukkitDB.updateDocument(getUuid(), "uuid", "kills", updatedData, database, collection);
            NukkitDB.updateDocument(getUuid(), "uuid", "kill-streak", updatedStreak, database, collection);
        }
    }

    public void addDeath() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            int currentData = getDeaths();
            int updatedData = currentData + 1;
            NukkitDB.updateDocument(getUuid(), "uuid", "kills", updatedData, database, collection);
            NukkitDB.updateDocument(getUuid(), "uuid", "kill-streak", 0, database, collection);
        }
    }

    public void addXp(int amount) {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            Afterlife.Main main = Afterlife.Main.getInstance();
            int xp = getNeededXp() + amount;
            int abs = Math.abs(xp - main.getConfig().getInt("xp-levelup-amount"));
            NukkitDB.updateDocument(getUuid(), "uuid", "global-xp", xp, database, collection);
            if (xp >= main.getConfig().getInt("xp-levelup-amount")) {
                addLevel();
                NukkitDB.updateDocument(
                        getUuid(), "uuid", "experience", abs, database, collection
                );
            } else {
                NukkitDB.updateDocument(
                        getUuid(), "uuid", "experience", xp, database, collection
                );
            }
        }
    }

    public void removeXp(int amount) {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            Afterlife.Main main = Afterlife.Main.getInstance();
            int xp = getNeededXp() - amount;
            int globalXp = getXp() - amount;
            int abs = Math.abs(getNeededXp() - amount);
            int difference = Math.abs(main.getConfig().getInt("xp-levelup-amount") - abs);
            if (xp < 0) {
                if (getLevels() > 0) {
                    removeLevel();
                    NukkitDB.updateDocument(
                            getUuid(), "uuid", "experience", difference, database, collection
                    );
                } else {
                    NukkitDB.updateDocument(
                            getUuid(), "uuid", "experience", 0, database, collection
                    );
                }
            } else {
                NukkitDB.updateDocument(
                        getUuid(), "uuid", "experience", xp, database, collection
                );
            }
            NukkitDB.updateDocument(
                    getUuid(), "uuid", "global-xp", globalXp, database, collection
            );
        }
    }

    public void addLevel() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            NukkitDB.updateDocument(
                    getUuid(), "uuid", "levels", getLevels() + 1, database, collection
            );
        }
    }

    public void removeLevel() {
        if (getPlugin("Afterlife") != null) {
            String database = Afterlife.Main.getInstance().getConfig().getString("database");
            String collection = Afterlife.Main.getInstance().getConfig().getString("collection");
            if (getLevels() > 0) {
                NukkitDB.updateDocument(
                        getUuid(), "uuid", "levels", getLevels() + 1, database, collection
                );
            }
        }
    }
}
