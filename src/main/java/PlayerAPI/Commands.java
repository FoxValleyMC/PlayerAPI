package PlayerAPI;

import NukkitDB.NukkitDB;
import PlayerAPI.Overrides.PlayerAPI;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;

public class Commands extends PluginCommand {

    public Commands(String name, Main plugin) {
        super(name, plugin);
        this.setAliases(new String[]{"nick"});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof PlayerAPI) {
            if (sender.hasPermission("alias.set") || sender.isOp()) {
                if (args.length == 1) {
                    PlayerAPI player = (PlayerAPI) sender;
                    player.setAlias(args[0]);
                    player.sendMessage("Set Alias/nickname to: "+args[0]);
                } else {
                    sender.sendMessage("Usage: { /nick : /alias } <new name>");
                }
            } else {
                sender.sendMessage("You do not have permission to set aliases!");
            }
        } else {
            sender.sendMessage("You cannot run command though console!");
        }

        return true;
    }
}
