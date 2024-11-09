package org.xyit.elysiumlogin.login;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.addPlayer;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.removePlayer;

public class LoginActions implements Listener {

    /**
     * 玩家退出时
     * 将玩家从未登录名单移除
     */
    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        removePlayer(playerName);
    }

}
