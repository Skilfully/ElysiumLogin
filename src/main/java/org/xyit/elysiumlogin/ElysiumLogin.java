package org.xyit.elysiumlogin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.xyit.elysiumlogin.command.GetCommand;
import org.xyit.elysiumlogin.playerctrl.CtrlPlayerList;
import org.xyit.elysiumlogin.playerctrl.PlayerCtrl;
import org.xyit.elysiumlogin.task.Run;
import org.xyit.elysiumlogin.var.GlobalVar;
import org.xyit.elysiumlogin.yaml.YamlOperate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.bukkit.Bukkit.getWorld;
import static org.xyit.elysiumlogin.message.GetMessageValue.Load;
import static org.xyit.elysiumlogin.message.MessageSender.console;
import static org.xyit.elysiumlogin.mysql.MySQLOperate2.CreateSQL;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.*;
import static org.xyit.elysiumlogin.sql.SQL.CreateSQLite;
import static org.xyit.elysiumlogin.var.GlobalVar.*;
import static org.xyit.elysiumlogin.var.OpenMethod.playerIsNotMinecraftPlayer;
import static org.xyit.elysiumlogin.var.OpenMethod.searchPlayer;
import static org.xyit.elysiumlogin.yaml.YamlOperate.*;

/**
 * 主类，程序入口
 */
public final class ElysiumLogin extends JavaPlugin implements Listener {

    private static ConcurrentHashMap<String, Boolean> playerLoginStatus = new ConcurrentHashMap<>();
    public static Map<Player, BukkitRunnable> countdownTasks = new HashMap<>();
    private static Map<Player, Integer> remainingTimes = new HashMap<>();

    /**
     * 插件启动时运行，初始化所有配置文件
     */
    @Override
    public void onEnable() {

        // 注册命令
        // 【l】悬空注释：密码
        getServer().getPluginCommand("l").setExecutor(this);
        getServer().getPluginCommand("l").setTabCompleter((commandSender, command, s, args)
                -> args.length == 1 ? Collections.singletonList("密码") : new ArrayList<>(0));
        // 【login】悬空注释：密码
        getServer().getPluginCommand("login").setExecutor(this);
        getServer().getPluginCommand("login").setTabCompleter((commandSender, command, s, args)
                -> args.length == 1 ? Collections.singletonList("密码") : new ArrayList<>(0));
        // 【elysiumlogin】悬空注释：1-[set|help|info|reload] 2-[LoginLocation]
        getServer().getPluginCommand("elysiumlogin").setExecutor(this);
        getServer().getPluginCommand("elysiumlogin").setTabCompleter((commandSender, command, s, args)
                -> {
            if (args.length == 1) {
                return Arrays.asList("help","info","reload","set");
            }
            if (args.length == 2) {
                if (args[0].equals("set")) {
                    return Collections.singletonList("LoginLocation");
                }
            }
            return Collections.emptyList();
        });

        String world = "world";//设置默认世界（String），后期改一下调用MV插件读取
        World SP = getWorld(world);//上一步的世界为String类型，所以这里转化为world类型
        Location spawnLocation = SP.getSpawnLocation();// 获取世界的默认出生点

        // 获取X、Y、Z坐标
        GlobalVar.SpawnPointX = spawnLocation.getBlockX();
        GlobalVar.SpawnPointY = spawnLocation.getBlockY();
        GlobalVar.SpawnPointZ = spawnLocation.getBlockZ();

        //获取工作路径并保存在全局变量WorkPath中
        GlobalVar.WorkPath = getDataFolder();
        console("§a[ §eOK §a] §3加载底层框架");

        //这里执行CreateFiles，该类包含了检查文件是否存在并创建、补充不存在的文件、Key
        NewFiles();
        CreateFiles();

        //注册监听器
        getServer().getPluginManager().registerEvents(new PlayerCtrl(), this);
        getServer().getPluginManager().registerEvents(new PlayerCtrl(), this);
        getServer().getPluginManager().registerEvents(this, this);
        console("§a[ §eOK §a] §3加载监听器");

        //将插件对象保存在全局变量中
        GlobalVar.plugin = this;

        //保存MySQL相关信息
        console("§a[ §eOK §a] §3初始化监听器");

        //初始化玩家列表
        CtrlPlayerList.setPlayerList(new ArrayList<>());
        console("§a[ §eOK §a] §3加载列表");

        //加载其他变量
        Load();
        AllowChat = YamlValueString("plugins\\ElysiumLogin","setting.yml","AllowChat");

        //初始化PlayerCtrl类
        new PlayerCtrl();

        //创建数据库
        GlobalVar.MySQLUsername = YamlOperate.YamlValueString("plugins\\ElysiumLogin","setting.yml","MySQL.User");
        GlobalVar.MySQLPassword = YamlOperate.YamlValueString("plugins\\ElysiumLogin","setting.yml","MySQL.Password");
        GlobalVar.MySQLUrl = "jdbc:mysql://" +
                YamlOperate.YamlValueString("plugins\\ElysiumLogin","setting.yml","MySQL.Address") + ":" +
                YamlOperate.YamlValueString("plugins\\ElysiumLogin","setting.yml","MySQL.Port") + "/" +
                YamlOperate.YamlValueString("plugins\\ElysiumLogin","setting.yml","MySQL.Database");
        String SQL = YamlValueString("plugins\\ElysiumLogin", "setting.yml", "SQL");
        if (Objects.equals(SQL, "MySQL")) {
            CreateSQL();
        } else if (Objects.equals(SQL, "SQLite")) {
            CreateSQLite();
        }
        console("§a[ §eOK §a] §3加载数据库");
//        //网络自检流程
//        try {
//            if (Index.start()){
//                console("§a[ §eOK §a] §3加载Net网络驱动");
//            }
//        } catch (IOException e) {
//            console("§c网络自检失败，请检查网络连接");
//            e.printStackTrace();
//        }
        console("§a[ §eOK §a] §3加载Net网络驱动");
        //加载异步线程
        new Run(this).run();
        console("§a[ §eOK §a] §3加载线程");
        //最后发出提示信息
        console(" ___             ___  _____         __   __");
        console("|    |    \\   / |       |   |    | |  | |  |");
        console("|___ |     \\ /  |___    |   |    | |  | |  |");
        console("|    |      |       |   |   |    | |   |   |");
        console("|___ |___   |    ___| __|__ |____| |       |");
        console("");
        console("§a[ §eOK §a] §eElysiumLogin 已加载");
        console(("§e当前语言文件：| Current Language File: ") + (GlobalVar.language));
    }

    /**
     插件关闭时运行
     */
    @Override
    public void onDisable() {
        console("ElysiumLogin 已卸载，感谢您的使用");
    }

    /**
     * 在这里执行插件的reload操作，例如重新加载配置文件
     */
    public void reloadPlugin() {
        reloadConfig();
        console("配置文件已重新加载");
    }

    /**
     * 当玩家尝试登录时运行，检查用户名是否合法
     */
    @EventHandler
    public void onPlayerPerLogin(AsyncPlayerPreLoginEvent event) {
        String name = event.getName();
        if (!name.matches("^[0-9a-zA-Z_]+$")) {
            if (!name.matches("^\\w+$")) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language,"join.NameViolation"));
            }
        } else if (playerLoginStatus.containsKey(name) && playerLoginStatus.get(name)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "join.already"));
        } else {
            CtrlPlayerList.addPlayer(name);
            playerLoginStatus.put(name, true);
            //开始倒计时
        }
    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        startAsyncCountdown(player, YamlValueDouble("plugins\\ElysiumLogin","setting.yml","TimeOut"));
        if (Objects.equals(YamlValueString("plugins\\ElysiumLogin", "setting.yml", "Title"), "yes")) {
            String title = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "login.Title");
            String subtitle = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", GlobalVar.language, "login.Title2")
                    .replace("[TIME]", Integer.toString(getRemainingTime(player)));
            player.sendTitle(title, subtitle, 0, 9999, 0);
        }
    }

    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        removePlayer(playerName);
        playerLoginStatus.remove(playerName);
        if (countdownTasks.containsKey(event.getPlayer())) {
            BukkitRunnable task = countdownTasks.get(event.getPlayer());
            task.cancel();
            countdownTasks.remove(event.getPlayer());
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return GetCommand.command(sender, command, label, args);
    }

    //----------------------------------------------------------异步任务-----------
    public static void startAsyncCountdown(Player player, double seconds) {
        // 保存初始倒计时时间
        int second = (int) seconds;
        remainingTimes.put(player, second);

        // 创建并启动异步倒计时任务
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!remainingTimes.containsKey(player)) {
                    cancel();
                    return;
                }

                int remainingSeconds = remainingTimes.get(player);

                if (remainingSeconds <= 0) {
                    String Message = YamlValueString("plugins\\ElysiumLogin\\Local_Languages", language,"login.TimeOut");
                    String value = Message.replace("[TIME]",String.valueOf(YamlValueDouble("plugins\\ElysiumLogin","setting.yml","TimeOut")));
                    Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            // 执行踢出玩家操作
                            player.kickPlayer(value);
                        }
                    });

                    // 从remainingTimes中移除玩家，防止重复踢出
                    remainingTimes.remove(player);
                    cancel();
                    return;
                }

                remainingTimes.put(player, --remainingSeconds); // 更新剩余时间
            }
        };

        countdownTasks.put(player, task);
        task.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    // 获取玩家的剩余倒计时时间
    public static int getRemainingTime(Player player) {
        return remainingTimes.getOrDefault(player, 0);
    }
}
