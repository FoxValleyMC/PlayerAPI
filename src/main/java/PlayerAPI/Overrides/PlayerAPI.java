package PlayerAPI.Overrides;

import PlayerAPI.Impl.*;
import NukkitDB.NukkitDB;
import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.plugin.Plugin;

public class PlayerAPI extends Player implements IPlayer, NetWorthImpl, AfterlifeImpl {

    public PlayerAPI(SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);
    }

    // mechanics
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

    public Plugin getPlugin(String name) {
        return this.getServer().getPluginManager().getPlugin(name);
    }

    public String getUuid() {
        return this.getUniqueId().toString();
    }

    @Override
    public String toString() {
        return "PlayerAPI(name='" + this.getAlias() + "', location=" + super.toString() + ')';
    }

    // PlayerAPI - AliasPro
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

    // NetWorth
    public int getMoney() {
//        if (getPlugin("NetWorth") != null) {
//            String database = NetWorth.Main.getInstance().getConfig().getString("database");
//            String collection = NetWorth.Main.getInstance().getConfig().getString("collection");
//            return Integer.parseInt(
//                    NukkitDB.query(
//                            getUuid(), "uuid", database, collection
//                    ).get("balance").toString()
//            );
//        }
        return 0;
    }

    public void setMoney(int amount) {
//        if (getPlugin("NetWorth") != null) {
//            String database = NetWorth.Main.getInstance().getConfig().getString("database");
//            String collection = NetWorth.Main.getInstance().getConfig().getString("collection");
//            NukkitDB.updateDocument(
//                    getUuid(), "uuid", "balance", amount, database, collection
//            );
//        }
    }

    public void addMoney(int amount) {
//        if (getPlugin("NetWorth") != null) {
//            String database = NetWorth.Main.getInstance().getConfig().getString("database");
//            String collection = NetWorth.Main.getInstance().getConfig().getString("collection");
//            int currentBalance = getMoney();
//            int newBalance = currentBalance + amount;
//            NukkitDB.updateDocument(
//                    getUuid(), "uuid", "balance", newBalance, database, collection
//            );
//        }
    }

    public void subtractMoney(int amount) {
//        if (getPlugin("NetWorth") != null) {
//            String database = NetWorth.Main.getInstance().getConfig().getString("database");
//            String collection = NetWorth.Main.getInstance().getConfig().getString("collection");
//            int currentBalance = getMoney();
//            int newBalance;
//            if (currentBalance <= 0) {
//                newBalance = 0;
//            } else {
//                newBalance = currentBalance - amount;
//            }
//            NukkitDB.updateDocument(
//                    getUuid(), "uuid", "balance", newBalance, database, collection
//            );
//        }
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
