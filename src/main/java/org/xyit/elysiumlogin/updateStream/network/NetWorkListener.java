package org.xyit.elysiumlogin.updateStream.network;

import org.xyit.elysiumlogin.message.MessageSender;
import org.xyit.elysiumlogin.updateStream.var.Var;
import org.xyit.elysiumlogin.var.GlobalVar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 服务器网络监听器类，负责监听指定端口的入站连接。
 */
public class NetWorkListener {
    /**
     * 启动网络监听器，等待客户端连接。
     *
     * @param port 要监听的端口号。
     */
    public static void start(int port){
        try{
            // 创建一个服务器套接字并绑定到指定端口
            ServerSocket serverSocket = new ServerSocket(port);
            MessageSender.console("§b[ServerGroupStream] §l§n§4监听服务器已开启！！");
            // 无限循环，等待客户端连接
            while (true) {
                // 接受客户端连接并打印连接信息
                Socket clientSocket = serverSocket.accept();
                MessageSender.console("§b[ServerGroupStream] §l§n§4新的客户端连接！");

                // 创建一个新的线程来处理客户端连接
                // 创建新线程处理客户端请求
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        }catch (IOException e){
            // 打印异常信息，用于错误追踪
            System.err.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/**
 * 客户端处理程序类，负责处理与单个客户端的通信。
 */
class ClientHandler implements Runnable {
    private final Socket clientSocket;

    /**
     * 构造函数，初始化客户端处理程序。
     *
     * @param clientSocket 客户端套接字，用于与客户端的通信。
     */
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * 当线程运行时，此方法处理与客户端的通信。
     */
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // 获取客户端的 IP 地址
            InetAddress clientAddress = clientSocket.getInetAddress();
            String clientIP = clientAddress.getHostAddress();
            MessageSender.console("§b[ServerGroupStream] §l§n§4连接IP为:" + clientIP);

            ArrayList<Object> data = new ArrayList<>();
            String inputLine;
            // 循环读取客户端发送的消息并回显
            while ((inputLine = in.readLine()) != null) {
                //处理客户端发送的消息
                data.add(inputLine);
            }

            if (!data.isEmpty()){
                GlobalVar.var.setVar(data);
                out.println("ok");
                NetWorkOperate.start();
            }
        } catch (IOException e) {
            // 打印异常信息，用于错误追踪
            System.err.println("§4§l§n[ServerGroupStream] 处理错误的客户端:" + e.getMessage());
        } finally {
            try {
                // 关闭客户端套接字
                clientSocket.close();
            } catch (IOException e) {
                // 打印异常信息，用于错误追踪
                System.err.println("§4§l§n[ServerGroupStream] 关闭客户端套接字时出错:" + e.getMessage());
            }
        }
    }
}
