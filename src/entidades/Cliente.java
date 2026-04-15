package entidades;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
    /**
     * Envía una petición UDP y no devuelve la respuesta (método compatible con código previo).
     */
    public void enviar(String IP, int puerto, double n1, double n2, String op) throws Exception{
        String resp = enviarYRecibir(IP, puerto, n1, n2, op);
        // mantener comportamiento previo: imprimir en consola
        System.out.println("Respuesta del servidor: " + resp);
    }

    /**
     * Envía la petición UDP y devuelve la respuesta del servidor como String.
     */
    public String enviarYRecibir(String IP, int puerto, double n1, double n2, String op) throws Exception{
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress direccion = InetAddress.getByName(IP);

            // construir datos: n1,n2 o n1,n2,op
            String datos;
            if (op == null || op.trim().isEmpty()) {
                datos = n1 + "," + n2; // compatibilidad: suma por defecto
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

