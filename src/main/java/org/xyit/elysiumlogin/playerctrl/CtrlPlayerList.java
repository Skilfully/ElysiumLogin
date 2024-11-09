package org.xyit.elysiumlogin.playerctrl;

import org.bukkit.event.EventHandler;

import java.util.AbstractList;

/**
 * 需要控制的玩家列表
 */
public class CtrlPlayerList {
    /**
     * 玩家列表
     */
    public static AbstractList<String> playerList;

    /**
     * 空构造器
     */
    public CtrlPlayerList() {
    }

    /**
     * 获取列表对象
     * @return 列表对象
     */
    public static AbstractList<String> getPlayerList() {
        return playerList;
    }

    /**
     * 设置列表对象
     * @param playerList 玩家列表对象
     */
    public static void setPlayerList(AbstractList<String> playerList) {
        CtrlPlayerList.playerList = playerList;
    }

    /**
     * 判断玩家是否在列表中
     * @param player 玩家名称
     * @return true/false 是否在列表中
     */
    public static boolean isPlayer(String player) {
        return playerList.contains(player);
    }

    /**
     * 添加玩家
     * @param player 玩家名称
     */
    public static void addPlayer(String player) {
        if (isPlayer(player)){
            return;
        }
        playerList.add(player);
    }

    /**
     * 移除玩家
     * 将会循环删除所以名字相同的玩家
     * @param player 玩家名称
     */
    public static void removePlayer(String player) {
        if (!isPlayer(player)){
            return;
        }
        while (isPlayer(player)) {
            playerList.remove(player);
        }
    }
}
