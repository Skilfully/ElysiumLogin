package org.xyit.elysiumlogin.updateStream.builder;

public class ActiveBuilder {
    public static String databasesActive(String active, String name, String password){
        return "{databasesActive:" + active + "$name:" + name + "$password:" + password + "}";
    }

    public static String RoutTableActive(String active, int id, String IP, int port){
        return "{RoutTableActive:" + active + "$id:" + id + "$IP:" + IP + "$port:" + port + "}";
    }
}
