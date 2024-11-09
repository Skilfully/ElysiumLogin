package org.xyit.elysiumlogin.updateStream.logic;

import org.bukkit.configuration.file.YamlConfiguration;
import org.xyit.elysiumlogin.updateStream.network.NetWorkListener;
import org.xyit.elysiumlogin.updateStream.network.NetWorkSender;
import org.xyit.elysiumlogin.var.GlobalVar;

import java.io.File;
import java.io.IOException;

/**
 * 第一次加入集群的时候执行
 */
public class JoinGroup {
    public static void firstJoinGroup() throws IOException {
        // 1.创建路由表
        createRoutTable();

        //2.创建日志
        //TODO:记得加

        // 3.连接本地数据库(省略)

        // 4.连接服务器集群
        connectToGroup();

    }

    /**
     * 创建路由表
     * @return 是否成功
     * @throws IOException IO异常
     */
    private static boolean createRoutTable() throws IOException {
        // 创建文件 RoutTable.yml

        //将 SeverGroupStream.yml加入GlobalVar

        return true;
    }
    /**
     * 连接服务器集群
     * @return 是否成功
     */
    private static boolean connectToGroup(){
        // 1.打开监听
        NetWorkListener.start(GlobalVar.SeverGroupStreamConfig.getInt("port"));
        return true;
    }
}
