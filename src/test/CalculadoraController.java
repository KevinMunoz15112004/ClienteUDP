package test;

import entidades.Cliente;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CalculadoraController {
    @FXML private TextField tfN1;
    @FXML private TextField tfN2;
    @FXML private ChoiceBox<String> cbOp;
    @FXML private TextField tfIP;
    @FXML private TextField tfPort;
    @FXML private Button btnEnviar;
    @FXML private TextArea taRespuesta;

    private final Cliente cliente = new Cliente();

    @FXML
    private void initialize() {
        cbOp.getItems().addAll("+", "-", "*", "/");
        cbOp.setValue("+");
        tfIP.setText("127.0.0.1");
        tfPort.setText("5000");
    }

    @FXML
    private void onEnviar() {
        String n1s = tfN1.getText().trim();
        String n2s = tfN2.getText().trim();
        String op = cbOp.getValue();
        String ip = tfIP.getText().trim();
        String portS = tfPort.getText().trim();

        double n1, n2;
        int port;
        try {
            n1 = Double.parseDouble(n1s);
            n2 = Double.parseDouble(n2s);
            port = Integer.parseInt(portS);
        } catch (NumberFormatException e) {
            taRespuesta.setText("Entrada inválida: revisa números o puerto.");
            return;
        }

        taRespuesta.setText("Enviando...");
        btnEnviar.setDisable(true);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    String respuesta = cliente.enviarYRecibir(ip, port, n1, n2, op);
                    Platform.runLater(() -> taRespuesta.setText(respuesta));
                } catch (Exception ex) {
                    Platform.runLater(() -> taRespuesta.setText("Error: " + ex.getMessage()));
                } finally {
                    Platform.runLater(() -> btnEnviar.setDisable(false));
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    private void onLimpiar() {
        tfN1.clear();
        tfN2.clear();
        taRespuesta.clear();
    }
}

