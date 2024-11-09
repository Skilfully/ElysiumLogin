package org.xyit.elysiumlogin.task.tasks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.xyit.elysiumlogin.var.GlobalVar;

import static org.xyit.elysiumlogin.ElysiumLogin.getRemainingTime;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.isPlayer;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.playerList;
import static org.xyit.elysiumlogin.var.GlobalVar.plugin;
import static org.xyit.elysiumlogin.var.OpenMethod.searchPlayer;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

public class SendTitle implements Runnable {

    @Override
    public void run() {
        // 获取玩家列表并创建副本
        String[] PlayerList = playerList.toArray(new String[0]);
        int length = PlayerList.length;

        if (length != 0) {
            // 安排在主线程上执行玩家遍历和效果施加的操作
            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    for (String element : PlayerList) {
                        Player player = searchPlayer(element);
                        if (player != null) {
                            // 施加药水效果
                            PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 999, 2);
                            player.addPotionEffect(blindness);
                            // 更新Title
                            String title = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "login.Title");
                            String subtitle = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "login.Title2")
                                    .replace("[TIME]", Integer.toString(getRemainingTime(player)));
                            if (isPlayer(player.getName())) {
                                player.sendTitle(title, subtitle, 0, 9999, 0);
                            }
                        }
                    }
                }
            });
        }
    }
}
