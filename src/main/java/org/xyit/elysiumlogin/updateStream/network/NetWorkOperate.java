package org.xyit.elysiumlogin.updateStream.network;

import org.xyit.elysiumlogin.message.MessageSender;
import org.xyit.elysiumlogin.updateStream.var.Var;
import org.xyit.elysiumlogin.var.GlobalVar;

import java.util.ArrayList;
import java.util.Arrays;

public class NetWorkOperate {
    public static boolean test() {
        return true;
    }

    /**
     *开始进行网络操作
     */
    public static void start() {
        Var var = GlobalVar.var;
        if (var == null){
            throw new NullPointerException("var is null");
        }
        if (var.getVar().size() != 1){
            //System.out.println("?");
            MessageSender.console("？");
            return;
        }
        //分割大块
        ArrayList<String> tmp = new ArrayList<>(Arrays.asList(var.getVar().get(0).toString().split("&")));
        ArrayList<String[]> all = new ArrayList<>();
        for (int i = 0; i < tmp.size(); i++) {
            all.add(tmp.get(i).split("="));
        }
        switch (all.get(0)[0]){
            case "update":
                update(all);
                break;
            case "join":
                join(all);
                break;
            case "rejoin":
                rejoin(all);
                break;
        }
    }

    private static void update(ArrayList<String[]> all){
        //tag=update&selfID=5&sourceID=1&databasesActive={databasesActive:add$name:test$password:ws12354}&RoutTableActive={null}
        MessageSender.console("§b[ServerGroupStream]§a 接收到数据库更新请求");
        MessageSender.console("§b[ServerGroupStream]§a 尝试更新");

    }

    private static void join(ArrayList<String[]> all){

    }

    private static void rejoin(ArrayList<String[]> all){

    }
}
