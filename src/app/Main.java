package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../controller/test1.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 550, 400));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
