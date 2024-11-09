package org.xyit.elysiumlogin.updateStream;

import org.bukkit.configuration.file.YamlConfiguration;
import org.xyit.elysiumlogin.message.MessageSender;
import org.xyit.elysiumlogin.updateStream.builder.NetWorkMessageBuilder;
import org.xyit.elysiumlogin.updateStream.network.NetWorkListener;
import org.xyit.elysiumlogin.updateStream.network.NetWorkSender;
import org.xyit.elysiumlogin.updateStream.var.Var;
import org.xyit.elysiumlogin.var.GlobalVar;

import java.io.File;
import java.io.IOException;

/**
 * 网络通信的主程序
 */
/*
* 1.检测是否为第一次启动
*   是：
*   1.创建文件夹
*   2.创建配置文件
*   3.创建路由表
*   4.崩溃并让腐竹填写路由表和配置文件
*
*   否：
*   1.检测配置文件和路由表是否存在
*   2.检测配置文件和路由表是否有需要的值
*       否：
 *      崩溃并让腐竹填写路由表和配置文件
 *
*       是：
*       1.连接配置里指定的服务器
*       2.获取当前路由表
*       3.将自己添加进路由表最后一位
*       4.发出路由表更新请求
*       5.从配置文件里指定的服务器拉去数据库
*       6.将状态从拉去改为更新
* 2.状态十分为更新
*   是：
*   1.打开监听
*   2.接受到更新时解析并更新本地
*   3.传递更新
*  */
public class Index {
    /**
     * 新开一个线程
     */
    public static boolean start() throws IOException {
        //TODO:你想想办法加上多线程
        return begain();
    }

    /**
     * 开始主程序
     * @throws IOException IOException
     */
    public static boolean begain() throws IOException {
        //1.检测是否为第一次启动
        if (setFirst()){
            return false;
        }

        //判断功能是否开启
        if (!checkOpen()){
            return true;
        }

        //初始化
        setDefault();

        //2.检测配置文件和路由表是否有需要的值
        if (!ckeckConfig()){
            MessageSender.console("§b[ServerGroupStream]§4 配置文件异常！");
            throw new RuntimeException("配置文件异常");
        }
        //1.连接配置里指定的服务器
        //打开监听
        NetWorkListener.start(GlobalVar.SeverGroupStreamConfig.getInt("Server.localPort"));

        //2.获取当前路由表
        //发送消息
        int i = 0;
        while (i < GlobalVar.SeverGroupStreamConfig.getInt("retry")) {
            if (NetWorkSender.firstSender(GlobalVar.SeverGroupStreamConfig.getString("Server.firstIP"),
                    GlobalVar.SeverGroupStreamConfig.getInt("Server.firstPort"), NetWorkMessageBuilder.getJoin())){
                MessageSender.console("§b[ServerGroupStream]§a 入组消息发送成功！");
                break;
            }
            MessageSender.console("§b[ServerGroupStream]§4 入组消息发送失败, 将尝试重新连接！");
            i++;
            MessageSender.console("§b[ServerGroupStream]§4 剩余尝试次数" + i);
        }
        //设置路由表


        //3.将自己添加进路由表最后一位


        //4.发出路由表更新请求

        //5.从配置文件里指定的服务器拉去数据库

        //6.将状态从拉去改为更新
        return true;
    }

    private static boolean ckeckConfig() {
        GlobalVar.var = new Var();
        return GlobalVar.SeverGroupStreamConfig.isSet("Server.firstIP") &&
                GlobalVar.SeverGroupStreamConfig.isSet("Server.firstPort") &&
                GlobalVar.SeverGroupStreamConfig.isSet("retry");
    }

    /**
     * 初始化
     */
    public static void setDefault() {
        //写入文件
        GlobalVar.SeverGroupStreamConfig = YamlConfiguration.loadConfiguration(
                new File("plugins\\ElysiumLogin\\SeverGroupStream","SeverGroupStream.yml"));
        GlobalVar.RoutTable =  YamlConfiguration.loadConfiguration(new File
                ("plugins\\ElysiumLogin\\SeverGroupStream", "RoutTable.yml"));
    }

    /**
     * 初始化
     * @return 是否是第一次启动
     * @throws IOException IOException
     */
    public static boolean setFirst() throws IOException {
        if (!new File("plugins\\ElysiumLogin\\SeverGroupStream").exists() ||
                !new File("plugins\\ElysiumLogin\\SeverGroupStream\\RoutTable.yml").exists() ||
                !new File("plugins\\ElysiumLogin\\SeverGroupStream\\SeverGroupStream.yml").exists()){
            MessageSender.console("§b[ServerGroupStream]§a 检测到初次启动，正在准备初始化0/5");
            // 1.创建文件夹
            new File("plugins\\ElysiumLogin\\SeverGroupStream").mkdirs();
            MessageSender.console("§b[ServerGroupStream]§a 初始化进度 1/5");

            // 2.1 创建配置文件
            new File("plugins\\ElysiumLogin\\SeverGroupStream","SeverGroupStream.yml").createNewFile();
            // 2.2 写入全局变量
            GlobalVar.SeverGroupStreamConfig = YamlConfiguration.loadConfiguration(
                    new File("plugins\\ElysiumLogin\\SeverGroupStream","SeverGroupStream.yml"));
            MessageSender.console("§b[ServerGroupStream]§a 初始化进度 2/5");

            // 3.1 创建路由表
            new File("plugins\\ElysiumLogin\\SeverGroupStream", "RoutTable.yml").createNewFile();
            // 3.2 写入全局变量
            GlobalVar.RoutTable =  YamlConfiguration.loadConfiguration(new File
                    ("plugins\\ElysiumLogin\\SeverGroupStream", "RoutTable.yml"));
            MessageSender.console("§b[ServerGroupStream]§a 初始化进度 3/5");

            // 4.1 初始化配置文件
            //TODO:有大大滴问题待解决
            GlobalVar.SeverGroupStreamConfig.set("enable","true");
            GlobalVar.SeverGroupStreamConfig.set("Server.firstIP",null);
            GlobalVar.SeverGroupStreamConfig.set("Server.firstPort",null);
            try {
                GlobalVar.SeverGroupStreamConfig.save(new File("plugins\\ElysiumLogin\\SeverGroupStream","SeverGroupStream.yml"));
            } catch (IOException e) {
                //Send
                e.printStackTrace();
                MessageSender.console("§b[ServerGroupStream]§4 初始化失败！");
            }
            MessageSender.console("§b[ServerGroupStream]§a 初始化进度 4/5");

            // 4.崩溃并让腐竹填写配置文件
            MessageSender.console("§b[ServerGroupStream]§a 初始化进度 5/5");
            MessageSender.console("§b[ServerGroupStream]§c 初次启动成功！，请填写路由表和配置文件！");
            return true;
        }
        return false;
    }

    /**
     * 检查功能是否开启
     * @return 是否开启
     */
    public static boolean checkOpen(){
        if (!GlobalVar.SeverGroupStreamConfig.getBoolean("enable")){
            MessageSender.console("§b[ServerGroupStream]§e 功能未开启");
            return false;
        } else if (GlobalVar.SeverGroupStreamConfig.getBoolean("enable")) {
            MessageSender.console("§b[ServerGroupStream]§c 已开启功能，启动自检");
            return true;
        }else{
            MessageSender.console("§b[ServerGroupStream]§c 请检查enable的值为true/false!");
            throw new RuntimeException("enable值异常");
        }
    }
}
