package org.xyit.elysiumlogin.task;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.xyit.elysiumlogin.task.tasks.SendMessage;
import org.xyit.elysiumlogin.task.tasks.SendTitle;

import java.util.Objects;

import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

public class Run implements Listener {

    private final Plugin plugin; // 引用Plugin实例

    // 构造函数，接收Plugin实例
    public Run(Plugin plugin) {
        this.plugin = plugin;
    }

    // 加载线程
    public void run() {
        SendMessage task_SendMessage = new SendMessage();
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task_SendMessage, 0, 20 * 3);
        if (Objects.equals(YamlValueString("plugins\\ElysiumLogin", "setting.yml", "Title"), "yes")) {
            SendTitle task_SendTitle = new SendTitle();
            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task_SendTitle, 0, 5);
        }
    }
}