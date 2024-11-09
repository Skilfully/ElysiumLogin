package org.xyit.elysiumlogin.mysql;

import org.bukkit.configuration.file.YamlConfiguration;
import org.xyit.elysiumlogin.var.GlobalVar;
import org.xyit.elysiumlogin.yaml.YamlOperate;

import java.sql.*;
import java.util.ArrayList;

/**
 * MySQL操作类
 */
public class MySQLOperate {
    /**
     * 重复的操作格式
     * @return 返回一个连接对象
     */
    private static Connection doQuery() throws SQLException {
        YamlConfiguration config = GlobalVar.settingConfig;
        //加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(GlobalVar.MySQLUrl, GlobalVar.MySQLUsername, GlobalVar.MySQLPassword);
    }

    /**
     * @param key 查询的键
     * @return 返回值列表
     * @throws SQLException 抛出异常
     */
    public static ArrayList<Object> find(String key) throws SQLException {
        ArrayList<Object> result = new ArrayList<>();

        Connection conn = doQuery();

        //执行语句
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT " + key + " FROM test");

        //处理结果
        while (rs.next()) {
            result.add(rs.getString(key));
        }

        //关闭资源
        rs.close();
        stmt.close();
        conn.close();

        return result;
    }
}
