package PlayerAPI.Overrides;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.network.SourceInterface;

public class PlayerAPI extends Player implements IPlayer {

    public PlayerAPI(SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);
    }

    @Override
    public String toString() {
        return "PlayerAPI(name='" + this.getName() + "', location=" + super.toString() + ')';
    }
}
