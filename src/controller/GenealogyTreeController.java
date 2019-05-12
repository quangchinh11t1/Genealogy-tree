package controller;

import entity.GenealogyTree;
import entity.Member;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GenealogyTreeController extends ControllerForDangNhap implements Initializable {
    @FXML
    Button callButton, addMember, goBack ;
    @FXML
    TreeTableView<Member> treeTableView;
    @FXML
    TreeTableColumn<Member, String> rootColumn, ngoaitocColumn;



    private GenealogyTree tree = new GenealogyTree();

    public void getData() {

        try {
            //tree.getData();
            TreeItem<Member> root =new TreeItem<>(tree.getTree().get(0));
            root.setExpanded(true);
            for(int i=1; i<tree.getTree().size(); i++){
                root.getChildren().add(new TreeItem<>(tree.getTree().get(i)));
            }
            rootColumn.setCellValueFactory(
                    (TreeTableColumn.CellDataFeatures<Member, String> param) ->
                            new ReadOnlyStringWrapper(param.getValue().getValue().getFullName())
            );
            treeTableView.setRoot(root);

            treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            treeTableView.getSelectionModel().setCellSelectionEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void call (ActionEvent event){
        Member m1 = treeTableView.getSelectionModel().getSelectedItems().get(0).getValue();
        Member m2 = treeTableView.getSelectionModel().getSelectedItems().get(1).getValue();
        System.out.println(m1.getFullName() + "   " + m2.getFullName());
        int size = treeTableView.getSelectionModel().getSelectedItems().size();

        for (int i=0 ;i<size; i++){

        }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("THÔNG BÁO");
        alert.setHeaderText("                       quan he la: " + tree.callRelationship(m1,m2) + " !");
        alert.setContentText("Successfully!");
        alert.showAndWait();
        

        System.out.println("quan he la: " + tree.callRelationship(m1,m2));
    }
    public void getInfor(){
        Member m = treeTableView.getSelectionModel().getSelectedItem().getValue();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông tin thành viên");
        alert.setHeaderText(m.getFullName());
        alert.setContentText("Sinh năm " + m.getNgaySinh() + "\nQuê quán : " + m.getAddress()
                            + "\nCông việc là " + m.getJob() );
        alert.showAndWait();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }

    @FXML
    void add(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("themthanhvien.fxml"));
        Parent menuParent = loader.load();
        Scene scene = new Scene(menuParent);
        stage.setScene(scene);
    }
    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("dangnhap.fxml"));
        Parent dangnhapParent = loader.load();
        Scene scene = new Scene(dangnhapParent);
        stage.setScene(scene);
    }
}
