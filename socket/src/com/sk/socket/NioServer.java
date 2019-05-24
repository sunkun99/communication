package com.sk.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sk on 2019/5/24 11:08.
 */
public class NioServer {
    public void init() throws IOException {
        Charset charset = Charset.forName("UTF-8");
        // 创建一个选择器，可用close()关闭，isOpen()表示是否处于打开状态，他不隶属于当前线程
        Selector selector = Selector.open();
        // 创建ServerSocketChannel，并把它绑定到指定端口上
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(7777),1024);
        // 设置为非阻塞模式, 这个非常重要
        server.configureBlocking(false);
        // 在选择器里面注册关注这个服务器套接字通道的accept事件
        // ServerSocketChannel只有OP_ACCEPT可用，OP_CONNECT,OP_READ,OP_WRITE用于SocketChannel
        server.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            //休眠时间为1s，无论是否有读写等事件发生，selector每隔1s都被唤醒一次
            selector.select(1000);
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            SelectionKey key = null;
            while (it.hasNext()) {
                //如果key对应的Channel包含客户端的链接请求
                // OP_ACCEPT 这个只有ServerSocketChannel才有可能触发
                key=it.next();
                // 由于select操作只管对selectedKeys进行添加，所以key处理后我们需要从里面把key去掉
                it.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc  = (ServerSocketChannel) key.channel();
                    // 得到与客户端的套接字通道
                    //ServerSocketChannel的accept接收客户端的连接请求并创建SocketChannel实例，完成上述操作后，相当于完成了TCP的三次握手，TCP物理链路正式建立。
                    //我们需要将新创建的SocketChannel设置为异步非阻塞，同时也可以对其TCP参数进行设置，例如TCP接收和发送缓冲区的大小等。此处省掉
                    SocketChannel channel = ssc.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                    //将key对应Channel设置为准备接受其他请求
                    key.interestOps(SelectionKey.OP_ACCEPT);
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    String content = "";
                    try {
                        int readBytes = channel.read(byteBuffer);
                        if (readBytes > 0) {
                            byteBuffer.flip(); //为write()准备
                            byte[] bytes = new byte[byteBuffer.remaining()];
                            byteBuffer.get(bytes);
                            content+=new String(bytes);
                            System.out.println(content);
                            //回应客户端
                            doWrite(channel);
                        }
                        // 写完就把状态关注去掉，否则会一直触发写事件(改变自身关注事件)
                        key.interestOps(SelectionKey.OP_READ);
                    } catch (IOException i) {
                        //如果捕获到该SelectionKey对应的Channel时出现了异常,即表明该Channel对于的Client出现了问题
                        //所以从Selector中取消该SelectionKey的注册
                        key.cancel();
                        if (key.channel() != null) {
                            key.channel().close();
                        }
                    }
                }
            }
        }
    }
    private void doWrite(SocketChannel sc) throws IOException{
        byte[] req ="服务器已接受".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        sc.write(byteBuffer);
        if(!byteBuffer.hasRemaining()){
            System.out.println("Send 2 Service successed");
        }
    }

    public static void main(String[] args) {
        try {
            new NioServer().init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
