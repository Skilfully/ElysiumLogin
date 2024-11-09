package org.xyit.elysiumlogin.sql;

import java.sql.*;
import java.util.Objects;

import static org.xyit.elysiumlogin.message.MessageOperate.*;
import static org.xyit.elysiumlogin.message.MessageSender.console;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

public class SQL {
    public static void CreateSQLite() {
        String UrlSetting = YamlValueString("plugins\\ElysiumLogin","setting.yml","SQLite.Path");
        String thisurl;
        if (Objects.equals(UrlSetting, "this")) {
            //选中本地目录
            thisurl = "plugins/ElysiumLogin";
        } else {
            thisurl = UrlSetting;
        }
        String database = YamlValueString("plugins\\ElysiumLogin","setting.yml","SQLite.Database");
        String url = ("jdbc:sqlite:" + thisurl + "/" + database + ".db");
        //开始连接并创建数据库
        try {
            Class.forName("org.sqlite.JDBC");
            //连接数据库
            Connection connection = DriverManager.getConnection(url);
            //创建表
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Data (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "PlayerName TEXT NOT NULL, " +
                    "PlayerMail TEXT, " +
                    "PlayerPassword LONGTEXT NOT NULL)";
            //提交任务
            connection.createStatement().executeUpdate(createTableSQL);
            //关闭数据库和指针
            connection.close();
        } catch (ClassNotFoundException e) {
            Message_SQLiteClassNotFound();
        } catch (SQLException e) {
            Message_SQLiteCreateError();
        }
    }

    /**
     * SQLite本地数据库查询
     * @param PlayerName 玩家名称
     * @param Key 需要查询的值,可用:PlayerMail\PlayerPassword\ID
     * @return 返回查询到的Key的值，String. || 为NoValue时表明没有查询到数据
     */
    public static String SQLiteGetValue(String PlayerName,String Key) {
        String UrlSetting = YamlValueString("plugins\\ElysiumLogin","setting.yml","SQLite.Path");
        String thisurl;
        String database = YamlValueString("plugins\\ElysiumLogin","setting.yml","SQLite.Database");
        String sql = ("SELECT " + Key + " FROM Data WHERE PlayerName=?");
        String value = "%TYPE_ERROR%";
        //判断值是否为this
        if(Objects.equals(UrlSetting, "this")){
            //为"this"，使用默认地址
            thisurl = "plugins/ElysiumLogin";
        } else {
            //非"this"，使用提供的地址
            thisurl = UrlSetting;
        }
        //拼接url
        String url = ("jdbc:sqlite:" + thisurl + "/" + database + ".db");
        //加载驱动
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            Message_SQLiteClassNotFound();
        }
        //连接数据库
        try (Connection conn = DriverManager.getConnection(url);PreparedStatement stmt = conn.prepareStatement(sql)){
            //替换占位符
            stmt.setString(1, PlayerName);
            //查询
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    value = rs.getString(1);
                } else {
                    value = "NoValue";
                }
            } catch (Exception e) {
                console(e.getMessage());
            }
        } catch (SQLException e) {
            console(e.getMessage());
        }
        return value;
    }
}
