package org.xyit.elysiumlogin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.xyit.elysiumlogin.login.LoginCommand;
import org.xyit.elysiumlogin.message.MessageSender;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import static org.xyit.elysiumlogin.command.CommandOperate.elysiumlogin_exit;
import static org.xyit.elysiumlogin.message.MessageOperate.*;
import static org.xyit.elysiumlogin.message.MessageSender.console;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.isPlayer;
import static org.xyit.elysiumlogin.var.GlobalVar.SettingIsSet;
import static org.xyit.elysiumlogin.var.OpenMethod.playerGetLocation;
import static org.xyit.elysiumlogin.var.OpenMethod.setYamlFile;

/**
 * 指令监听类
 */
public class GetCommand extends JavaPlugin implements CommandExecutor {
    /**
     *
     * @param sender 发送者
     * @param command 命令
     * @param label 根指令
     * @param args 传入指令
     */
    public static boolean command(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("elysiumlogin")) {
            //获取内容
            if (args.length != 0) {
                String Key = args[0];

                //此处如果Key为空则玩家/控制台只输入了elysiumlogin，直接给出info信息
                //但是Key不为空也会执行该语句，因此在该句结尾放出info法
                switch (Key) {
                    case "reload":

                        return CommandOperate.elysiumlogin_reload(sender);

                    case "help":

                        return CommandOperate.elysiumlogin_help(sender);

                    case "info":

                        return CommandOperate.elysiumlogin_info(sender);

                    case "test":

                        MessageSender.console("test");
                        return true;

                    case "set":

                        if (!(args.length >= 2)) {
                            Message_command_WrongParameters(sender);
                            return true;
                        }
                        String Key_Set = args[1];
                        if (Key_Set == null) {
                            Message_command_WrongParameters(sender);
                            return true;
                        }
                        switch (Key_Set) {
                            case "LoginLocation" :
                                //设置登入地点
                                if (sender instanceof Player) {
                                    Player player = (Player) sender;
                                    double x = Double.parseDouble((playerGetLocation(player))[0]);
                                    double y = Double.parseDouble((playerGetLocation(player))[1]);
                                    double z = Double.parseDouble((playerGetLocation(player))[2]);
                                    String world = (playerGetLocation(player))[3];

                                    try {
                                        File configFile = new File("plugins\\ElysiumLogin\\setting.yml");
                                        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                                        config.set("Login.Location.X",x);
                                        config.set("Login.Location.Y",y);
                                        config.set("Login.Location.Z",z);
                                        config.save(new File("plugins\\ElysiumLogin\\setting.yml"));
                                        setYamlFile("plugins\\ElysiumLogin\\setting.yml","Login.Location.World",world);
                                        return true;
                                    } catch (IOException ex) {
                                        return true;
                                    }
                                } else {
                                    Message_OnlyPlayer();
                                }
                        }
                        return true;

                    default:
                        Message_command_notFound(sender);
                }
            } else {
                return CommandOperate.elysiumlogin(sender);//此处为只输入【elysium】返回info信息
            }
        }
        //判断输入的指令为【l】
        if(label.equalsIgnoreCase("l")) {
            if (!(sender instanceof Player)) {
                Message_OnlyPlayer();
            } else {
                if (args.length != 1) {
                    Message_Help_l(sender);
                } else {
                    return LoginCommand.Login(sender, args);
                }
            }
        }

        //判断输入的指令为【login】
        if(label.equalsIgnoreCase("login")) {
            if (!(sender instanceof Player)) {
                Message_OnlyPlayer();
            } else {
                if (args.length != 1) {
                    Message_Help_login(sender);
                } else {
                    return LoginCommand.Login(sender, args);
                }
            }
        }

        //判断输入的指令为【exit】
        if(label.equalsIgnoreCase("exit")) {
            if (!(sender instanceof Player)) {
                Message_OnlyPlayer();
            } else {
                return elysiumlogin_exit(sender);
            }
        }
        return true;
    }
}
