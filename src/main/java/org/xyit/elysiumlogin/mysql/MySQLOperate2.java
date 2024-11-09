package org.xyit.elysiumlogin.mysql;

import org.bukkit.command.CommandSender;
import org.xyit.elysiumlogin.var.GlobalVar;
import org.xyit.elysiumlogin.yaml.YamlOperate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import static org.xyit.elysiumlogin.message.MessageOperate.*;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.removePlayer;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

public class MySQLOperate2 {
    public static void CreateSQL(){
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立连接
            String url = ("jdbc:mysql://" +
                    YamlOperate.YamlValueString("plugins\\ElysiumLogin","setting.yml","MySQL.Address") + ":" +
                    YamlOperate.YamlValueString("plugins\\ElysiumLogin","setting.yml","MySQL.Port"));
            Connection conn = DriverManager.getConnection(url, GlobalVar.MySQLUsername, GlobalVar.MySQLPassword);
            // 创建Statement对象
            Statement stmt = conn.createStatement();

            // 执行SQL命令创建数据库
            String SQL_CreateSQL = ("CREATE DATABASE IF NOT EXISTS " + YamlValueString("plugins\\ElysiumLogin","setting","Database"));
            stmt.executeUpdate(SQL_CreateSQL);
            // 查询并创建数据表
            stmt.close();
            conn.close();
            Connection conn2 = DriverManager.getConnection(GlobalVar.MySQLUrl, GlobalVar.MySQLUsername, GlobalVar.MySQLPassword);
            // 创建Statement对象
            Statement stmt2 = conn2.createStatement();
            DatabaseMetaData dbMetaData = conn2.getMetaData();
            String tableName = "DATA";
            boolean exists = checkIfTableExists(dbMetaData, tableName);
            if (!exists) { //如果不存在
                // 执行SQL命令创建数据表
                String SQL_CreateTable = "CREATE TABLE DATA (ID INT AUTO_INCREMENT PRIMARY KEY, PlayerUUID TEXT, PlayerName TEXT, PlayerPassword LONGTEXT, PlayerMail TEXT)";
                stmt2.executeUpdate(SQL_CreateTable);
            }
            // 关闭资源
            stmt2.close();
            conn2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MySQL查询模块
     * @param PlayerName 需要查询的用户名
     */
    public static String SQLValueString(CommandSender sender, String PlayerName) {
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立连接
            Connection conn = DriverManager.getConnection(GlobalVar.MySQLUrl, GlobalVar.MySQLUsername, GlobalVar.MySQLPassword);

            //判断是否存于数据库
            String sql = "SELECT COUNT(*) FROM DATA WHERE Name = ?";
            PreparedStatement IF_pstmt = conn.prepareStatement(sql);
            IF_pstmt.setString(1, PlayerName);
            ResultSet rsa = IF_pstmt.executeQuery();
            if (rsa.next()) {
                // 获取计数结果
                int count = rsa.getInt(1);
                // 判断是否存在
                if (count == 0) {
                    //不存在
                    Message_PlayerNotFound(sender);
                }
            }
            // 关闭资源
            rsa.close();
            IF_pstmt.close();
            //查询
            String SELECT = "SELECT Password FROM Data WHERE PlayerName = ?";
            PreparedStatement SELECT_pstmt = conn.prepareStatement(SELECT);
            SELECT_pstmt.setString(1, PlayerName);
            ResultSet rs = SELECT_pstmt.executeQuery();
            // 处理查询结果
            if (rs.next()) {
                // 获取Password字段的值
                String PasswordValue = rs.getString("PlayerPassword");
            }
            rs.close();
            SELECT_pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "%TYPE_ERROR%";
    }

    /**
     * 检查数据表是否存在
     * @param dbMetaData 数据库已有表
     * @param tableName 要检查的表
     * @return
     * @throws Exception 展示错误信息
     */
    private static boolean checkIfTableExists(DatabaseMetaData dbMetaData, String tableName) throws Exception {
        ResultSet rs = dbMetaData.getTables(null, null, tableName, null);
        return rs.next();
    }

    public static String Hash256(CommandSender sender,String originalString) {
        try {
            // 创建一个SHA-256哈希生成器
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 将字符串转换为字节数组
            byte[] encodedhash = digest.digest(originalString.getBytes());
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 输出哈希值
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Message_LoginServerError(sender);
        }
        return "";
    }
}