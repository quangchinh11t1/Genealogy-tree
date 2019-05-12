package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sql.MySQLConnUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerForDangNhap {
    @FXML
    TextField id,password;

    public static String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    static String login;


    public void login(ActionEvent e) throws IOException {
        Statement statement = null;
        try {
            statement = MySQLConnUtils.getJDBCConnection().createStatement();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String sql = "SELECT * FROM " + id.getText() + ".users where username = '" +id.getText() +
                "' and password = '" + password.getText() +"' limit 1;" ;
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
            if(rs.next()){
                setLogin(id.getText());
                System.out.println("*******ten dang nhap la        " + getLogin());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("THÔNG BÁO");
                alert.setHeaderText("ĐĂNG NHẬP THÀNH CÔNG!");
                alert.setContentText("Connect successfully!");
                alert.showAndWait();

                Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("test1.fxml"));
                Parent menuParent = loader.load();
                Scene scene = new Scene(menuParent);
                stage.setScene(scene);

            } else
                {System.out.println("dang nhap that bai");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("THÔNG BÁO");
                alert.setHeaderText("                       ĐĂNG NHẬP THẤT BẠI!");
                alert.setContentText("*WARNING: FBI");
                alert.show();
                }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public String getId(){
        return id.getText();
    }
    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("khoidong.fxml"));
        Parent dangnhapParent = loader.load();
        Scene scene = new Scene(dangnhapParent);
        stage.setScene(scene);
    }
}
