package org.xyit.elysiumlogin.var;

//import com.sun.source.util.Plugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.xyit.elysiumlogin.updateStream.var.Var;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getWorld;

/**
 * 全局变量存放处
 */
public class GlobalVar {
    /**
     * 版本信息
     */
    final public static double version = 1.0;

    /**
     *
     */
    public static String AllowChat;

    /**
     * 出生点X坐标
     */
    public static double SpawnPointX = 0;

    /**
     * 出生点Y坐标
     */
    public static double SpawnPointY = 0;

    /**
     * 出生点Z坐标
     */
    public static double SpawnPointZ = 0;

    /**
     * language文件默认配置-键名
     */
    public static String[] HelpIsSet;

    /**
     *
     * language文件默认配置-键值
     */
    public static String[] HelpValue;

    /**
     * setting文件默认配置-键名
     */
    public static String[] SettingIsSet;

    /**
     * setting文件默认配置-键值
     */
    public static String[] SettingValue;

    /**
     * MySQL数据库URL
     */
    public static String MySQLUrl;

    /**
     * MySQL数据库密码
     */
    public static String MySQLPassword;

    /**
     * MySQL数据库用户名
     */
    public static String MySQLUsername;

    /**
     * 当前选择的语言文件
     */
    public static String language;

    /**
     * 当前选择的语言前缀
     */
    public static String prefix;

    /**
     *  插件工作路径
     */
    public static File WorkPath;
    /**
     * setting.yaml YAML文件
     */
    public static YamlConfiguration settingConfig;

    /**
     *  Language_CN.yml YAML文件
     */
    public static YamlConfiguration languageCNConfig;

    /**
     * 插件实例
     */
    public static Plugin plugin;

    /**
     * 服务器集群配置文件
     */
    public static YamlConfiguration SeverGroupStreamConfig;

    /**
     * 服务器集群路由表
     */
    public static YamlConfiguration RoutTable;

    /**
     * 服务器集群日志
     */
    public static YamlConfiguration SeverGroupStreamLog;

    /**
     * 网络局部全局变量
     */
    public static Var var;

    /**
     * 检查腐竹是否已经填写配置文件
     */
    public static boolean steamIsSet;

    public static void NewFiles() {
        HelpIsSet = new String[]{
                "prefix",
                "command.notFound",
                "join.already",
                "join.NameViolation",
                "help.command.elysiumlogin",
                "help.command.elysiumlogin_info",
                "help.command.elysiumlogin_help",
                "help.command.elysiumlogin_reload",
                "help.command.l",
                "help.command.login",
                "login.already",
                "login.Error",
                "login.exit",
                "login.message",
                "login.onLogin",
                "login.ServerError",
                "login.Successful",
                "login.TimeOut",
                "login.Title",
                "login.Title2",
                "onlyPlayer",
                "playerNotFound",
                "register.Already",
                "register.NoSame",
                "register.ServerError",
                "register.Successfully",
                "reload.Language.Filed",
                "reload.Language.Successful",
                "reload.Setting.Filed",
                "reload.Setting.Successful",
                "SQLite.ClassNotFound",
                "SQLite.CreateError",
                "WorldNotFound",
                "WrongParameters",
                "web"
        };
        HelpValue = new String[]{
                "§7| §3服务器 §7| ",
                "§c未知的命令",
                "§c你已经登入此服务器了\n§a若你本人并未登入，请尽快联系管理员",
                "§c请使用由数字,字母和下划线组成的游戏名,进入游戏",
                "§a /elysiumlogin §f- §e查看插件信息",
                "§a /elysiumlogin info §f- §e查看插件信息",
                "§a /elysiumlogin help §f- §e查看帮助信息",
                "§a /elysiumlogin reload §f- §e重新加载配置文件",
                "§a /l <密码> §f- §e登入",
                "§a /login <密码> §f- §e登入",
                "§c你已经登入了！",
                "§c密码错误！",
                "§a已退出登入",
                "§c请使用 §e/l <密码> §c来登入",
                "§c你还未登入",
                "§c登入失败，服务器内部错误，请联系管理员！",
                "§a登入成功！",
                "§c你因 §e[TIME] §c秒内没有登入而被踢出服务器",
                "§e§ka§r §c使用 §e/l <密码> §c来登入 §e§ka",
                "§a你还有 §e[TIME] 秒 §a的登入时间",
                "§c只有玩家才能执行该命令！",
                "§c您还未注册，请先到官网注册！",
                "§c你已经注册过了！",
                "§c两次输入的密码不相同！",
                "§c注册失败，服务器内部错误",
                "§a注册成功！",
                "§eLanguage §c配置文件重载失败",
                "§eLanguage §a配置文件重载成功",
                "§eSetting §c配置文件重载失败",
                "§eSetting §a配置文件重载成功",
                "§c未找到数据库依赖！",
                "§c数据库创建/连接失败！",
                "§c未知的世界",
                "§c参数错误",
                "§e官网 §f: §awww.login.com"
        };
        SettingIsSet = new String[]{
                "language",
                "Login.Location.World",
                "Verification.Type", //Type = Local:本地验证 SelfService:自有服务 Official:官方
                "Verification.SelfService.TargetServer.Url",  //目标服务器的URL
                "Verification.SelfService.TargetServer.Port",  //目标服务器的端口
                "Verification.Official.UserName",  //官方服务-用户名
                "Verification.Official.Token",  //官方服务-验证令牌
                "AllowMove",
                "Return",
                "Back",
                "SQL",
                "MySQL.Address",
                "MySQL.Port",
                "MySQL.Database",
                "MySQL.User",
                "MySQL.Password",
                "SQLite.Path",
                "SQLite.Database",
                "Title",
                "AllowChat",
                "AllowCommands",
        };
        SettingValue = new String[]{
                "Language_CN.yml",
                "world",
                "Local",
                "",
                "",
                "",
                "",
                "no",
                "yes",
                "yes",
                "SQLite",
                "localhost",
                "3306",
                "Data",
                "root",
                "123456",
                "this",
                "Data",
                "yes",
                "no",
                "AllowCommands.txt",
        };
    }
}
