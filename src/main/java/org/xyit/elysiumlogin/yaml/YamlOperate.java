package org.xyit.elysiumlogin.yaml;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.xyit.elysiumlogin.var.GlobalVar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;
import static org.xyit.elysiumlogin.message.MessageOperate.*;
import static org.xyit.elysiumlogin.message.MessageSender.console;
import static org.xyit.elysiumlogin.var.GlobalVar.*;
import static org.xyit.elysiumlogin.yaml.ReloadFiles.addFile;


/**
 * YAML操作类
 */
public class YamlOperate {

    public static void CreateFiles() {
        GlobalVar.language = "Language_CN.yml";
        // 获取插件数据文件夹路径
        File dataFolder = GlobalVar.WorkPath;
        //创建文件夹-ElysiumLogin
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        //创建文件夹-ElysiumLogin/Local_Languages
        File languages = new File("plugins\\ElysiumLogin\\Local_Languages");
        if (!languages.exists()) {
            languages.mkdirs();
        }
        // 创建名为"setting.yml"的配置文件
        File configFile = new File(dataFolder, "setting.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                console(("§c无法创建setting.yml文件：" + e));
            }
        }
        // 创建名为"Language_CN.yml"的配置文件
        File LanguageConfigFile = new File("plugins\\ElysiumLogin\\Local_Languages", "Language_CN.yml");
        if (!LanguageConfigFile.exists()) {
            try {
                LanguageConfigFile.createNewFile();
            } catch (IOException e) {
                console(("§c无法创建Language_CN.yml文件：" + e));
            }
        }
        // 保存默认配置到文件
        setSettingDefault();

        String filePath = ("plugins/ElysiumLogin/" + YamlValueString("plugins/ElysiumLogin", "setting.yml", "AllowCommands"));
        File file = new File(filePath);
        if (!Objects.equals(YamlValueString("plugins/ElysiumLogin", "setting.yml", "AllowCommands"), "no")) {
            if (!file.exists()) {
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write("l\nlogin\nserver\nexit");
                } catch (IOException e) {
                    console("无法创建" + YamlValueString("plugins\\ElysiumLogin", "setting.yml", "AllowCommands") + "文件");
                }
            }
        }
        setLanguageDefault();
        GlobalVar.prefix = YamlValueString("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language,"prefix");
    }

    /**
     * 重载配置文件方法
     */
    public static void ReloadConfig(CommandSender sender) {
        //将语言保存到全局变量中

        File Tempfile = new File("plugins\\ElysiumLogin","setting.yml");
        // 判断文件是否存在
        if (Tempfile.exists()) {
            GlobalVar.language = YamlValueString("plugins\\ElysiumLogin","setting.yml","language");
        } else {
            GlobalVar.language = "Language_CN.yml";
        }

        File SettingFile = new File("plugins\\ElysiumLogin","setting.yml");
        File LanguageFile = new File("plugins\\ElysiumLogin\\Local_Languages",GlobalVar.language);
        //检查文件完整性
        if (!SettingFile.exists()) {
            try {
                SettingFile.createNewFile();
                setSettingDefault();
                Message_Reload_Setting_Successful(sender);
            } catch (IOException e) {
                Message_Reload_Setting_Filed(sender);
            }
        }
        if (!LanguageFile.exists()) {
            try {
                LanguageFile.createNewFile();
                setLanguageDefault();
                Message_Reload_Language_Successful(sender);
            } catch (IOException e) {
                Message_Reload_Language_Filed(sender);
            }
        }
        //尝试重新加载
        try {
            YamlConfiguration.loadConfiguration(SettingFile);
            // 日志输出，告知管理员配置已重新加载
            Message_Reload_Setting_Successful(sender);
        } catch (Exception e) {
            Message_Reload_Setting_Filed(sender);
        }
        try {
            YamlConfiguration.loadConfiguration(LanguageFile);
            // 日志输出，告知管理员配置已重新加载
            Message_Reload_Language_Successful(sender);
        } catch (Exception e) {
            Message_Reload_Language_Filed(sender);
        }
        GlobalVar.language = YamlValueString("plugins\\ElysiumLogin","setting.yml","language");
    }

    public static void setSettingDefault(){
        File configFile = new File("plugins\\ElysiumLogin", "setting.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        for (int number = 0;number < SettingIsSet.length;number++) {
            String i = SettingIsSet[number];
            String j = SettingValue[number];
            if (!config.isSet(i)) {
                config.set(i,j);
                try {
                    config.save(new File("plugins\\ElysiumLogin\\", "Setting.yml"));
                } catch (IOException ex) {
                    console("§c无法保存文件！");
                }
            }
        }
        if (!config.isSet("Login.Location.X")) {
            config.set("Login.Location.X",GlobalVar.SpawnPointX);
        }
        if (!config.isSet("Login.Location.Y")) {
            config.set("Login.Location.Y",GlobalVar.SpawnPointY);
        }
        if (!config.isSet("Login.Location.Z")) {
            config.set("Login.Location.Z",GlobalVar.SpawnPointZ);
        }
        if (!config.isSet("TimeOut")) {
            config.set("TimeOut",120);
        }
        //尝试保存
        try {
            config.save(new File("plugins\\ElysiumLogin", "setting.yml"));
        } catch (IOException ex) {
            console("§c无法保存文件！");
        }
        //将前缀信息保存在变量中
    }

    /**
     * 设置默认语言
     */
    public static void setLanguageDefault(){
        File configFile = new File("plugins\\ElysiumLogin\\Local_Languages", "Language_CN.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        for (int number = 0;number < HelpIsSet.length;number++) {
            String i = HelpIsSet[number];
            String j = HelpValue[number];
            if (!config.isSet(i)) {
                config.set(i,j);
                try {
                    config.save(new File("plugins\\ElysiumLogin\\Local_Languages", "Language_CN.yml"));
                } catch (IOException ex) {
                    console("§c无法保存文件！");
                }
            }
        }
    }
    /**
     * 接受：
     * @param FilePath 文件路径，一般为"plugins\\ElysiumLogin"，不填写则为默认路径
     * @param FileName 文件名称，与要带后缀
     * @param Key 需要查找的值，多级之间用"."来连接
     *   比如：查找setting文件中config中的language的值
     *   可以这样写：FilePath=ElysiumLogin FileName=setting.yml Key=config.language
     * @return KeyValue，即查找到的值,类型为String
     */
    public static String YamlValueString(String FilePath, String FileName, String Key) {
        File configFile = new File(FilePath, FileName);
        // 检查文件是否存在，如果不存在则控制台报错
        if (!configFile.exists()) {
            console("§c错误的配置文件!");
            return "§c错误的配置文件!";
        }
        // 使用YamlConfiguration加载文件
        YamlConfiguration config;
        config = YamlConfiguration.loadConfiguration(configFile);//读取值
        return config.getString(Key);
    }

    /**
     * Yaml文件的double类型数据的获取
     * @param FilePath 文件路径，一般为"plugins\\ElysiumLogin"
     * @param FileName 文件名称，需要后缀
     * @param Key 需要读取的值
     * @return 返回double类型数据
     */
    public static double YamlValueDouble(String FilePath,String FileName,String Key) {
        File configFileDouble = new File(FilePath,FileName);
        //
        if (!configFileDouble.exists()) {
            console("§c错误的配置文件!");
            return 0;
        }
        // 使用YamlConfiguration加载文件
        YamlConfiguration config;
        config = YamlConfiguration.loadConfiguration(configFileDouble);//读取值
        double Value = config.getDouble(Key);
        return Value;
    }
}
