package entidades;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {

    public String enviarYRecibir(String IP, int puerto, double n1, double n2, String op) throws Exception{
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress direccion = InetAddress.getByName(IP);

            // construir datos n1,n2 o n1,n2,op
            String datos;
            if (op == null || op.trim().isEmpty()) {
                datos = n1 + "," + n2;
            } else {
                datos = n1 + "," + n2 + "," + op.trim();
            }

            byte[] bufferS = datos.getBytes();
            DatagramPacket salida = new DatagramPacket(bufferS, bufferS.length, direccion, puerto);
            socket.send(salida);

            byte[] bufferE = new byte[1024];
            DatagramPacket entrada = new DatagramPacket(bufferE, bufferE.length);
            socket.receive(entrada);

            return new String(entrada.getData(), 0, entrada.getLength());
        }
    }

}

