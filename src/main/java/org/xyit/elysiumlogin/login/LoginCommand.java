package org.xyit.elysiumlogin.login;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.bukkit.potion.PotionEffectType.BLINDNESS;
import static org.xyit.elysiumlogin.ElysiumLogin.countdownTasks;
import static org.xyit.elysiumlogin.command.CommandOperate.bytesToHex;
import static org.xyit.elysiumlogin.message.MessageOperate.*;
import static org.xyit.elysiumlogin.message.MessageSender.console;
import static org.xyit.elysiumlogin.mysql.MySQLOperate2.SQLValueString;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.*;
import static org.xyit.elysiumlogin.sql.SQL.SQLiteGetValue;
import static org.xyit.elysiumlogin.var.OpenMethod.searchPlayer;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

public class LoginCommand {

    public static boolean Login(CommandSender sender, String[] args) {
        //判断是否已经登录
        if (!(isPlayer(sender.getName()))) {
            //true；已经登入
            Message_LoginAlready(sender);
            return true;
        }
        //获取玩家输入的值
        String name = sender.getName();
        String value = args[0];
        String SQLType = YamlValueString("plugins\\ElysiumLogin", "setting.yml", "SQL");
        Player player = (Player) sender;
        //判断使用的数据库类型
        String HashPassword = "";
        if (Objects.equals(SQLType, "MySQL")) {
            HashPassword = SQLValueString(sender,name);
        } else if (Objects.equals(SQLType, "SQLite")) {
            HashPassword = SQLiteGetValue(name,"PlayerPassword");
        } else {
            HashPassword = "%TYPE_ERROR%";
        }
        if (Objects.equals(HashPassword, "%TYPE_ERROR%")) {
            Message_LoginServerError(sender);
            return true;
        }
        String hash256 = "";
        //将玩家的value转化为hash_value
        try {
            // 获取SHA-256 MessageDigest实例
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // 更新MessageDigest对象
            md.update(value.getBytes());
            // 获取散列值
            byte[] digest = md.digest();
            // 将散列值转换为十六进制字符串
            hash256 = bytesToHex(digest);
            // 得到hash256
        } catch (NoSuchAlgorithmException e) {
            Message_LoginServerError(sender);
        }
        //对比hash256的值
        if(Objects.equals(hash256, HashPassword)) {
            //一样，密码正确
            Message_Login_Successful(sender);
            removePlayer(sender.getName());
            if (countdownTasks.containsKey(player)) {
                BukkitRunnable task = countdownTasks.get(player);
                task.cancel();
                player.removePotionEffect((BLINDNESS));
                player.sendTitle("", "", 0, 1, 0);
                countdownTasks.remove(player);
            }
        } else {
            //不一样，密码错误

            if(Objects.equals(HashPassword, "NoValue")) {
                Message_PlayerNotFound(sender);
                return true;
            }
            Message_Login_Error(sender);
        }
        return true;
    }
}
