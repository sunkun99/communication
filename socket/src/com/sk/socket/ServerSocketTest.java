package com.sk.socket;

import jdk.nashorn.api.scripting.ScriptUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sk on 2019/5/21 21:11.
 */
public class ServerSocketTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        while (true) {
            //等待客户端连接
            Socket socket = serverSocket.accept();
            Runnable runnable = () -> {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //获取输入流
                    String str = null;
                    while ((str = bufferedReader.readLine()) != null) {
                        System.out.println(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };

            executorService.submit(runnable);
        }
    }
}
