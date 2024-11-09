package org.xyit.elysiumlogin.var;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static org.xyit.elysiumlogin.message.MessageSender.console;

/**
 * 公开的方法
 */
public class OpenMethod {

    /**
     * 在服务器中查找玩家
     * @param playerName 玩家名,String
     * @return 对应的 Player 类型，未找到返回null
     */
    public static Player searchPlayer(String playerName) {
        // 遍历服务器中的所有玩家
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            // 检查玩家的昵称是否与搜索的昵称匹配
            if (player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        // 如果没有找到玩家，返回null
        return null;
    }

    /**
     * 修改指定yml文件内容
     * @param FilePath 文件路径 e.g:"plugins\\ElysiumLogin\\setting.yml"
     * @param Key 需要修改的键
     * @param value 需要修改的值
     * @return True / False
     */
    public static boolean setYamlFile(String FilePath, String Key, String value) {
        try {
            File configFile = new File(FilePath);
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            config.set(Key,value);
            config.save(new File(FilePath));
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * 判断 Player类型的 player 是否为我的世界玩家
     * @param p 需要判断的参数
     * @return 不是Minecraft玩家：True
     */
    public static boolean playerIsNotMinecraftPlayer(Player p) {
        return !p.getClass().getName().matches("org\\.bukkit\\.craftbukkit.*?\\.entity\\.CraftPlayer");
    }

    /**
     * 获取玩家所在位置
     * @param player 需要查找的玩家
     * @return String[]数组，X坐标，Y坐标，Z坐标，世界名
     */
    public static String[] playerGetLocation(Player player) {
        Location location = player.getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String WorldName = location.getWorld().getName();
        return new String[]{String.valueOf(x), String.valueOf(y), String.valueOf(z), WorldName};
    }

    /**
     * 加密字符串
     * @param str 待加密的字符串
     * @return 加密后的字符串
     **/
    public static String encrypt(String str) {
        //TODO:加密
        return str;
    }

    /**
     * 解密字符串
     * @param str 待解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String str) {
        //TODO:解密
        return str;
    }
}
