package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sql.MySQLConnUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerForDangky {
    @FXML
    TextField name , phoneNumber, id;
    @FXML
    DatePicker dateOfBirth;
    @FXML
    PasswordField password;


    public void registration(ActionEvent e) throws IOException, SQLException {
        Statement statement = null;
        try {
            statement = MySQLConnUtils.getJDBCConnection().createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        //Tạo cơ sở dữ liệu cho mỗi tài khoản đăng ký
        String dataBaseDemo = "CREATE DATABASE " + id.getText();
        statement.executeUpdate(dataBaseDemo);

        String tableGiaDinh = "CREATE TABLE " +id.getText()+ ".`giadinh` (\n" +
                "  `maGD` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `tenGD` text NOT NULL,\n" +
                "  `hotenChuGD` text NOT NULL,\n" +
                "  `diaChi` text NOT NULL,\n" +
                "  `SDT` int(11) NOT NULL,\n" +
                "  PRIMARY KEY (`maGD`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;";
        statement.executeUpdate(tableGiaDinh);

        String tableThanhVien = "CREATE TABLE "+id.getText()+ ".`thanhvien` (\n" +
                "  `maTV` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `maGD` int(11) NOT NULL,\n" +
                "  `hotenTV` text NOT NULL,\n" +
                "  `ngaysinh` date NOT NULL,\n" +
                "  `nghenghiep` text NOT NULL,\n" +
                "  `diachi` text NOT NULL,\n" +
                "  `hotenCha` text NOT NULL,\n" +
                "  `maTVcha` int(11) NOT NULL,\n" +
                "  PRIMARY KEY (`maTV`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;";
        statement.executeUpdate(tableThanhVien);

        String tableUsers = "CREATE TABLE "+id.getText()+ ".`users` (\n" +
                "  `username` varchar(255) NOT NULL,\n" +
                "  `password` varchar(255) NOT NULL,\n" +
                "  `fullname` varchar(255) NOT NULL,\n" +
                "  `birthday` varchar(10) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        statement.executeUpdate(tableUsers);

        String tableMember = "CREATE TABLE "+id.getText()+ ".`member` (\n" +
                "  `maTV` int(11) DEFAULT NULL,\n" +
                "  `maDoi` int(11) DEFAULT NULL,\n" +
                "  `hotenTV` text CHARACTER SET utf8,\n" +
                "  `gioiTinh` text CHARACTER SET utf8,\n" +
                "  `namSinh` int(11) DEFAULT NULL,\n" +
                "  `maVkCk` int(11) DEFAULT NULL,\n" +
                "  `maCha` tinytext,\n" +
                "  `diaChi` text CHARACTER SET utf8,\n" +
                "  `ngheNghiep` text CHARACTER SET utf8\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1";
        statement.executeUpdate(tableMember);

        /*String userList = "SELECT * FROM" + id.getText() + ".users;";
        ResultSet rs = statement.executeQuery(userList);

        int count = 0;
        while (rs.next()){
            System.out.println(rs);
            if(rs.getString("username").equals(id.getText())){
                count ++;
            }
        }
        System.out.println( count);*/

        String sql = " INSERT INTO " + id.getText() + ".users(username,password,fullname,birthday)VALUES ('"
                    + id.getText() + "', '" + password.getText() + "', '" + name.getText()
                    + "', '" + dateOfBirth.getAccessibleText() + "')";
        //System.out.println(sql);

        if (id.getText().equals("") || password.getText().equals("") || name.getText().equals("") || dateOfBirth.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("THÔNG BÁO");
            alert.setHeaderText("                       Bạn chưa nhập hết thông tin!");
            alert.setContentText("*WARNING: FBI");
            alert.show();
        } else {
            try {

                statement.executeUpdate(sql);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("THÔNG BÁO");
                alert.setHeaderText("ĐĂNG Ký THÀNH CÔNG!");
                alert.setContentText("Successfully!");
                alert.showAndWait();

                Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("dangnhap.fxml"));
                Parent dangnhapParent = loader.load();
                Scene scene = new Scene(dangnhapParent);
                stage.setScene(scene);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
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
