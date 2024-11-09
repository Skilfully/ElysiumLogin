package org.xyit.elysiumlogin.task.tasks;

import org.bukkit.entity.Player;
import org.xyit.elysiumlogin.var.GlobalVar;

import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.playerList;
import static org.xyit.elysiumlogin.var.OpenMethod.searchPlayer;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

public class SendMessage implements Runnable {

    //循环向未登入玩家发送信息
    @Override
    public void run() {
        String value = (GlobalVar.prefix + YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "login.message"));
        //获取玩家列表并储存为数组
        String[] PlayerList = playerList.toArray(new String[0]);
        int length = PlayerList.length;
        if (length != 0) {
            for (String element : PlayerList) {
                Player player = searchPlayer(element);
                if (player != null) {
                    player.sendMessage(value);
                }
            }
        }
    }
}
