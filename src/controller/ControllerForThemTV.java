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
import java.util.ArrayList;

public class ControllerForThemTV {
    @FXML
    TextField maTV, maDoi, name, gioiTinh, date, maVkCk, diaChi, ngheNghiep, maCha, id;

    public void themTV(ActionEvent e) throws IOException , SQLException{

        Statement statement = null;
        try {
            statement = MySQLConnUtils.getJDBCConnection().createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        String sql1 = "SELECT * FROM " + id.getText() + ".member;";
        System.out.println(sql1);
        ResultSet rs = statement.executeQuery(sql1);

        ArrayList listMaTV = new ArrayList();
        while (rs.next()){
            listMaTV.add(rs.getString("maTV"));
        }

        String sql;

        if(maTV.getText().equals("") || maDoi.getText().equals("") || gioiTinh.getText().equals("") ||
            date.getText().equals("") ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("                       Bạn chưa nhập hết thông tin!");
            alert.setContentText("*WARNING: FBI");
            alert.show();
        }else if( !(Integer.valueOf(maTV.getText()) == listMaTV.size() + 1) ){
            int temp = listMaTV.size() + 1;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("                     Bạn hãy nhập mã TV là " + temp );
            alert.setContentText("*WARNING: FBI");
            alert.show();
        }
        else if( !(gioiTinh.getText().equals("nam") || gioiTinh.getText().equals("nu")) ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("                       Vui lòng nhập giới tính là 'nam' hoặc 'nu'!");
            alert.setContentText("*WARNING: FBI");
            alert.show();
        } else if(Integer.valueOf(date.getText()) <= 0 || Integer.valueOf(date.getText()) >= 2030 ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("                       Vui lòng nhập đúng năm sinh!");
            alert.setContentText("*WARNING: FBI");
            alert.show();
        } else {
            sql = "INSERT INTO " + id.getText() + ".member(maTV,maDoi,hotenTV,gioiTinh, namSinh, maVkCk, maCha, diaChi, ngheNghiep)VALUE " + "('"
                            + maTV.getText() + "', '" + maDoi.getText() + "', '" + name.getText()
                            +   "', '" +gioiTinh.getText() + "', '" +date.getText() + "', '"
                            + maVkCk.getText() +"', '" + maCha.getText()+ "', '"
                            + diaChi.getText() + "', '" + ngheNghiep.getText()    + "')";
            System.out.println(sql);
            statement.executeUpdate(sql);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("Thêm THÀNH CÔNG!");
            alert.setContentText("Successfully!");
            alert.showAndWait();

            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("test1.fxml"));
            Parent menuParent = loader.load();
            Scene scene = new Scene(menuParent);
            stage.setScene(scene);

        }
    }

    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("test1.fxml"));
        Parent dangnhapParent = loader.load();
        Scene scene = new Scene(dangnhapParent);
        stage.setScene(scene);
    }


}

