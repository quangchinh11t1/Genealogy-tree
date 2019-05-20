package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sql.MySQL;
import sql.MySQLConnUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerForSuaTV implements Initializable {

    @FXML
    TextField name, maDoi, date, diaChi, maVkCk, ngheNghiep, maCha;

    public void showDataMember(){
        GenealogyTreeController inforMember = new GenealogyTreeController();

        name.setText(inforMember.getMember().getFullName());
        maDoi.setText(inforMember.getMember().getDoi().toString());
        date.setText(inforMember.getMember().getNgaySinh().toString());
        diaChi.setText(inforMember.getMember().getAddress());
        maVkCk.setText(inforMember.getMember().getMaVkCk().toString());
        ngheNghiep.setText(inforMember.getMember().getJob());
        if(inforMember.getMember().getMaCha() == null){
            maCha.setText("");
        }else{
            maCha.setText(inforMember.getMember().getMaCha().toString());
        }
    }

    public void suaTV(ActionEvent e) throws IOException, SQLException {
        ControllerForDangNhap login = new ControllerForDangNhap();
        GenealogyTreeController inforMember = new GenealogyTreeController();

        Statement statement = null;
        try {
            statement = MySQLConnUtils.getJDBCConnection().createStatement();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String sql = "UPDATE " + login.getLogin() + ".member SET hotenTV = '"
                    + name.getText() +"', maDoi = '" + maDoi.getText() + "', namSinh = '"
                    + date.getText() + "', diaChi = '" + diaChi.getText() + "', maVkCk = '"
                    + maVkCk.getText() + "', nghenghiep = '" + ngheNghiep.getText()
                    + "'WHERE maTV = " + inforMember.getMember().getMaTV();

        statement.executeUpdate(sql);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("THÔNG BÁO");
        alert.setHeaderText("Sửa thông tin thành công!");
        alert.setContentText("Successfully!");
        alert.showAndWait();

        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("test1.fxml"));
        Parent dangnhapParent = loader.load();
        Scene scene = new Scene(dangnhapParent);
        stage.setScene(scene);

    }

    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("test1.fxml"));
        Parent dangnhapParent = loader.load();
        Scene scene = new Scene(dangnhapParent);
        stage.setScene(scene);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDataMember();
    }


}
