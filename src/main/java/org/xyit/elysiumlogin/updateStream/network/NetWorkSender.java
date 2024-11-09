package org.xyit.elysiumlogin.updateStream.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 发送网络消息的类
 */
public class NetWorkSender {
    /**
     * 发送网络传递消息
     * @param url 地址
     * @param port 端口
     * @param message 消息
     */
    public static boolean updateSender(String url, int port, String message){
        return firstSender(url, port, message);
    }

    /**
     * 发送返回确认消息
     * @param url 地址
     * @param port 端口
     * @param message 消息
     */
    public static boolean returnSender(String url, int port, String message){
        return firstSender(url, port, message);
    }

    /**
     * 发送第一次加入集群消息
     * @param url 地址
     * @param port 端口
     * @param message 消息
     */
    public static boolean firstSender(String url, int port, String message){
        return send(url, port, message).equals("ok");
    }

    //不要在意这些细节...............
    private static String send(String url, int port, String message) {
        try (Socket socket = new Socket(url, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 发送消息到服务器
            out.println(message);

            // 读取服务器的响应
            return in.readLine();
        } catch (UnknownHostException e) {
            return "Error connecting to server: " + e.getMessage();
        } catch (IOException e){
            return "Error sending message: " + e.getMessage();
        }
    }
}
