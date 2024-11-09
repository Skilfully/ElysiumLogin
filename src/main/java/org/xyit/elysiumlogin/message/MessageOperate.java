package org.xyit.elysiumlogin.message;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.xyit.elysiumlogin.var.GlobalVar;

import static org.xyit.elysiumlogin.message.MessageSender.console;
import static org.xyit.elysiumlogin.var.GlobalVar.HelpIsSet;
import static org.xyit.elysiumlogin.var.GlobalVar.HelpValue;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueDouble;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

/**
 * 消息操作类
 */
public class MessageOperate {
    //送信息方法
    public static void Message_Info(CommandSender sender) {
        /*
         * 接受一个CommandSender sender即发送命令的人
         * 因为是INFO，所以只需要读取前缀即可
         */
        String Message = (GlobalVar.prefix + ("§rElysiumLogin 版本v") + GlobalVar.version + (" By Skilfully Made Group "));
        if (!(sender instanceof Player)) {
            //如果是控制台使用的命令
            console(("§e当前语言文件：| Current Language File: ") + (GlobalVar.language));
            console(("§rElysiumLogin 版本v") + GlobalVar.version + (" By Skilfully Made Group"));
        } else {
            //如果是玩家执行的命令
            sender.sendMessage(Message);
        }
    }

    /**
     * help信息的格式化发送
     *
     * @param sender 命令使用者
     */
    public static void Message_Help(CommandSender sender) {
        for (String Key : HelpIsSet) {
            if (Key.contains("help.command")) {
                String Value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, Key);
                String Message = (GlobalVar.prefix + Value);
                if (!(sender instanceof Player)) {
                    //如果是控制台使用的命令
                    console(Message);
                } else {
                    //如果是玩家执行的命令
                    sender.sendMessage(Message);
                }
            }
        }
    }

    /**
     * Login.Filed信息的格式化发送
     *
     * @param sender 命令使用者
     */
    public static void Message_Login_Error(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "login.Error");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    /**
     * Login.Successful信息的格式化发送
     *
     * @param sender 命令使用者
     */
    public static void Message_Login_Successful(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "login.Successful");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    /**
     * Reload.Successful信息的格式化发送
     *
     * @param sender 命令使用者
     */
    public static void Message_Reload_Setting_Successful(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "reload.Setting.Successful");
        String Message = (GlobalVar.prefix + value);
        if (!(sender instanceof Player)) {
            //如果是控制台使用的命令
            console(Message);
        } else {
            //如果是玩家执行的命令
            sender.sendMessage(Message);
        }
    }

    /**
     * Reload.Filed信息的格式化发送
     *
     * @param sender 命令使用者
     */
    public static void Message_Reload_Setting_Filed(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "reload.Setting.Filed");
        String Message = (GlobalVar.prefix + value);
        if (!(sender instanceof Player)) {
            //如果是控制台使用的命令
            console(Message);
        } else {
            //如果是玩家执行的命令
            sender.sendMessage(Message);
        }
    }

    /**
     * Reload.Successful信息的格式化发送
     *
     * @param sender 命令使用者
     */
    public static void Message_Reload_Language_Successful(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "reload.Language.Successful");
        String Message = (GlobalVar.prefix + value);
        if (!(sender instanceof Player)) {
            //如果是控制台使用的命令
            console(Message);
        } else {
            //如果是玩家执行的命令
            sender.sendMessage(Message);
        }
    }

    /**
     * Reload.Filed信息的格式化发送
     *
     * @param sender 命令使用者
     */
    public static void Message_Reload_Language_Filed(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "reload.Language.Filed");
        String Message = (GlobalVar.prefix + value);
        if (!(sender instanceof Player)) {
            //如果是控制台使用的命令
            console(Message);
        } else {
            //如果是玩家执行的命令
            sender.sendMessage(Message);
        }
    }

    /**
     * WorldNotFind的信息的格式化发送
     */
    public static void Message_WorldNotFound() {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "WorldNotFound");
        console(value);
    }

    /**
     * onlyPlayer 只有玩家能执行命令 的格式化信息
     * 因为该方法只有控制台能触发所以只需狐返回控制台即可
     */
    public static void Message_OnlyPlayer() {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"onlyPlayer");
        String Message = (GlobalVar.prefix + value);
        console(Message);
    }

    /**
     * 对于没有注册的玩家
     * @param sender 命令发送者
     */
    public static void Message_PlayerNotFound(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"playerNotFound");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    /**
     * 服务器内部错误
     * @param sender 命令发送者
     */
    public static void Message_LoginServerError(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"login.ServerError");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    public static void Message_SQLiteClassNotFound() {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"SQLite.ClassNotFound");
        String Message = (GlobalVar.prefix + value);
        console(Message);
    }

    public static void Message_SQLiteCreateError() {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"SQLite.CreateError");
        String Message = (GlobalVar.prefix + value);
        console(Message);
    }

    public static void Message_LoginAlready(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"login.already");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    public static void Message_Help_l(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"help.command.l");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    public static void Message_Help_login(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"help.command.login");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }





    public static void Message_exit(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"login.exit");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    public static void Message_NoLogin(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"login.onLogin");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    public static void Message_command_notFound(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"command.notFound");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }

    public static void Message_command_WrongParameters(CommandSender sender) {
        String value = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"WrongParameters");
        String Message = (GlobalVar.prefix + value);
        sender.sendMessage(Message);
    }
}
