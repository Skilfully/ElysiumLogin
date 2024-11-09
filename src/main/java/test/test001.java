package test;

import org.xyit.elysiumlogin.updateStream.network.NetWorkOperate;
import org.xyit.elysiumlogin.updateStream.var.Var;
import org.xyit.elysiumlogin.var.GlobalVar;
import org.xyit.elysiumlogin.yaml.YamlOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xyit.elysiumlogin.updateStream.network.NetWorkListener;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * 这是专门的测试类，项目上线时请删除
 */
//TODO:上线时删除
public class test001 {
    public static void main(String[] args) {
        //throw new NullPointerException();
//        GlobalVar.var = new Var(new ArrayList<>());
//        GlobalVar.var.addVar("tag=update&selfID=5&sourceID=1&databasesActive={databasesActive:add$name:test$password:ws12354}&RoutTableActive={null}");
//        NetWorkOperate.start();

    }

    public static void b() {
    }

    public static void a() {
        String url = "localhost";
        int port = 12345;

        System.out.println("start server");
        Thread startServer = new Thread(() -> NetWorkListener.start(port));

        startServer.start();

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("connect server");

        try (Socket socket = new Socket(url, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 发送消息到服务器
            out.println("Hello, server!");

            // 读取服务器的响应
            String response = in.readLine();
            System.out.println("Received from server: " + response);

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
