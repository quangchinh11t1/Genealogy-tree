package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerForKhoidong {
    public void changeToDangKy(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("dangky.fxml"));
        Parent dangnhapParent = loader.load();
        Scene scene = new Scene(dangnhapParent);
        stage.setScene(scene);
    }
    public void changeToDangNhap(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("dangnhap.fxml"));
        Parent dangnhapParent = loader.load();
        Scene scene = new Scene(dangnhapParent);
        stage.setScene(scene);
    }
}
