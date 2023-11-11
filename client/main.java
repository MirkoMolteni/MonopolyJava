package client;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        netUtil net = new netUtil();
        net.connect("172.22.182.242", 8080);
        net.send("hi");
        System.out.println(net.receive());
        net.close();
        }
}
