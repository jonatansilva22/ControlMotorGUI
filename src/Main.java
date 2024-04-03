import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;

public class Main extends Application {
    private Label etiqueta;
    private Label etiqueta2;
    private Button boton;
    private Button boton2;
    private Button boton3;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mi GUI con JavaFX");

        // Crear una etiqueta
        etiqueta = new Label("Estado del motor: Detenido");
        etiqueta.setStyle("-fx-font-size: 20; -fx-text-fill: blue;");
        
        // Crear una etiqueta para la fecha y hora
        etiqueta2 = new Label("");
        etiqueta2.setStyle("-fx-font-size: 20; -fx-text-fill: blue;");
        
        // Crear un botón
        Image imagen = new Image(getClass().getResourceAsStream("flecha-izquierda.png"));
boton = new Button("", new ImageView(imagen));
boton.setOnAction(e -> izquierda());

Image imagen2 = new Image(getClass().getResourceAsStream("pausa.png"));
boton2 = new Button("", new ImageView(imagen2));
boton2.setOnAction(e -> detener());
boton2.setDisable(true);

Image imagen3 = new Image(getClass().getResourceAsStream("flecha-correcta.png"));
boton3 = new Button("", new ImageView(imagen3));
boton3.setOnAction(e -> derecha());
        
        actualizarFechaHora();

        HBox hbox = new HBox(boton, boton2, boton3);
        hbox.setSpacing(10);
        
        StackPane layout = new StackPane();
        layout.getChildren().addAll(etiqueta, etiqueta2, hbox);
        layout.setPadding(new Insets(10));
        StackPane.setMargin(etiqueta, new Insets(100, 0, 0, 0));
        StackPane.setMargin(etiqueta2, new Insets(200, 0, 0, 0));
        StackPane.setMargin(hbox, new Insets(0, 0, 0, 0));

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void izquierda() {
        etiqueta.setText("Estado del motor: Girando en sentido antihorario");
        boton2.setDisable(false);
        boton3.setDisable(false);
        boton.setDisable(true);
    }

    private void derecha() {
        etiqueta.setText("Estado del motor: Girando en sentido horario");
        boton2.setDisable(false);
        boton3.setDisable(true);
        boton.setDisable(false);
    }

    private void detener() {
        etiqueta.setText("Estado del motor: Detenido");
        boton2.setDisable(true);
        boton3.setDisable(false);
        boton.setDisable(false);
    }
    
    private void actualizarFechaHora() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss - yyyy-MM-dd");
    
    Thread thread = new Thread(() -> {
        try {
            while (true) {
                Thread.sleep(1000);
                String fechaHora = LocalDateTime.now().format(formatter);
                Platform.runLater(() -> etiqueta2.setText(fechaHora));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    thread.setDaemon(true);
    thread.start();
}

    public static void main(String[] args) {
        launch(args);
    }
}
