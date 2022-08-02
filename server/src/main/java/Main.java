import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 8079;
        String host = "127.0.0.1";
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(host, port));

        while (true) {
            try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                final ByteBuffer byteBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(byteBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(byteBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    byteBuffer.clear();
                    System.out.println("Данные от клиента: " + msg);
                    msg.replaceAll("\\s", "");

                    socketChannel.write(ByteBuffer.wrap(msg.replaceAll("\\s", "").getBytes(StandardCharsets.UTF_8)));


                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }
}
