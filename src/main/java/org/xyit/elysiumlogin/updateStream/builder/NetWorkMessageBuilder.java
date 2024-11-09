package org.xyit.elysiumlogin.updateStream.builder;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 构建标准消息的类
 */
public class NetWorkMessageBuilder {
    /**
     * 构建更新消息
     * @param selfID 本机ID
     * @param sourceID 源ID
     * @param databasesActive 对数据库的操作
     * @param RoutTableActive 对路由表的操作
     * @return 标准格式的消息
     */
    @Contract(pure = true)
    public static @NotNull String updatePassMessage(int selfID, int sourceID, String databasesActive, String RoutTableActive){
        return  "tag=" + "update" +
                "selfID=" + selfID +
                "&sourceID=" + sourceID +
                "&databasesActive=" + databasesActive +
                "&RoutTableActive=" + RoutTableActive;
    }

    /**
     * 构建返回确认消息
     * @param selfID 本机ID
     * @param nextID 下一跳ID
     * @return 标准格式的消息
     */
    @Contract(pure = true)
    public static @NotNull String returnMessage(int selfID, int nextID){
        return "tag=" + "return" +
                "&selfID=" + selfID +
                "&nextID=" + nextID;
    }

    /**
     * 构建第一次加入集群更新路由表的消息
     * @param selfID 本机ID
     * @param selfIP 本机IP
     * @param selfPort 本机端口
     * @return 标准格式的消息
     */
    @Contract(pure = true)
    public static @NotNull String firstMessage(int selfID, String selfIP, int selfPort){
        return  "tag=" + "join" +
                "&selfID=" + selfID +
                "&selfIP=" + selfIP +
                "&selfPort" + selfPort;
    }

    /**
     * 构建第一次加入集群获取当前路由表的消息
     */
    @Contract(pure = true)
    public static @NotNull String getJoin(){
        return "tag=join";
    }

    /**
     * 构建返回路由表的消息
     */
    @Contract(pure = true)
    public static @NotNull String returnRoutTable(){

        return "";
    }
}
