package org.xyit.elysiumlogin.message;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

/**
 * 发送消息的类
 * 比如Console这类将信息格式化后发送
 */
public class MessageSender {
    /**
     * 向控制台发送消息
     * @param s 消息
     */
    public static void console(String s) {
        CommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(("§3[Elysium]§e[ElysiumLogin] §r" + s));
    }
    /**
     * 自定义信息发送-玩家，不想被修改的信息，可以直接调用该方法而不用读取Language文件，ssmfp是Send Special Message For Player （向玩家发送特定消息）的简写
     * @param sender 命令发送者
     * @param text 要发送的消息
     */
    public static void ssmfp(CommandSender sender, String text) {
        String prefix = YamlValueString("plugins\\ElysiumLogin\\Local_Languages","Language_CN.yml","prefix");
        Player player = (Player) sender;
        String NewText = (prefix + text);
        sender.sendMessage(NewText);
    }
}
