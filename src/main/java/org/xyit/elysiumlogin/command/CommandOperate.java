package org.xyit.elysiumlogin.command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.Plugin;
import org.xyit.elysiumlogin.message.MessageSender;
import org.xyit.elysiumlogin.mysql.MySQLOperate;
import org.xyit.elysiumlogin.playerctrl.CtrlPlayerList;
import org.xyit.elysiumlogin.playerctrl.PlayerCtrl;
import org.xyit.elysiumlogin.var.GlobalVar;

import java.sql.SQLException;
import java.util.Objects;

import static org.bukkit.Bukkit.getWorld;
import static org.xyit.elysiumlogin.ElysiumLogin.startAsyncCountdown;
import static org.xyit.elysiumlogin.message.GetMessageValue.Load;
import static org.xyit.elysiumlogin.message.MessageOperate.*;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.*;
import static org.xyit.elysiumlogin.yaml.YamlOperate.*;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueDouble;

/**
 * 指令的实际执行类
 */
public class CommandOperate {
    /**
     * elysiumlogin指令
     * @param sender 命令发送者
     */
    public static boolean elysiumlogin(CommandSender sender){
        elysiumlogin_info(sender);
        return true;
    }

    /**
     * elysiumlogin reload指令
     * @param sender 命令发送者
     */
    public static boolean elysiumlogin_reload(CommandSender sender){
        Plugin plugin = GlobalVar.plugin;
        //reload法
        new PlayerCtrl();
        Load();
        ReloadConfig(sender);
        return true;
    }

    /**
     * elysiumlogin help指令
     * @param sender 命令发送者
     * @return 是否成功
     */
    public static boolean elysiumlogin_help(CommandSender sender){
        Message_Help(sender);
        return true;
    }

    /**
     * elysiumlogin info指令
     * @param sender 命令发送者
     * @return 是否成功
     */
    public static boolean elysiumlogin_info(CommandSender sender){
        Message_Info(sender);
        return true;
    }

    public static boolean elysiumlogin_exit(CommandSender sender){
        if (isPlayer(sender.getName())) {
            Message_NoLogin(sender);
        } else {
            Player player = (Player) sender;
            String StringWorld = YamlValueString("plugins\\ElysiumLogin","setting.yml","Login.Location.World");
            double X = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.X");
            double Y = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.Y");
            double Z = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.Z");
            World world = getWorld(StringWorld);
            Location location = new Location(world,X,Y,Z);
            player.teleport(location);
            Message_exit(sender);
            addPlayer(sender.getName());
            startAsyncCountdown(player, YamlValueDouble("plugins\\ElysiumLogin","setting.yml","TimeOut"));
            return true;
        }
        return true;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param bytes 需要转换的字节数组
     * @return 返回十六进制字符串(String)
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
