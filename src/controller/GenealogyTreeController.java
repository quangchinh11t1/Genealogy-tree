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
import sql.MySQLConnUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GenealogyTreeController implements Initializable {
    @FXML
    TextField text1, text2;
    @FXML
    Button callButton,selectButton, clearButton ;
    @FXML
    TreeTableView<Member> treeTableView;
    @FXML
    TreeTableColumn<Member, String> rootColumn, ngoaitocColumn;

    private GenealogyTree tree = new GenealogyTree();
    // lưu mảng member ng chọn click
    private ArrayList<Member> selectedMember = new ArrayList<>();

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

            treeTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            treeTableView.getSelectionModel().setCellSelectionEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void select(ActionEvent event){
        selectedMember.add(treeTableView.getSelectionModel().getSelectedItem().getValue());
        Member m1 = selectedMember.get(0);
        System.out.println(m1.getFullName());
        text1.setText(m1.getFullName());
        if (selectedMember.size() >= 2){
            Member m2 = selectedMember.get(1);
            text2.setText(m2.getFullName());
        }

    }
    public void clear(ActionEvent event){
        selectedMember.clear();
        text1.setText("");
        text2.setText("");
    }
    private String relation;
    public void call (ActionEvent event){

        //String relation; //= new String();
        //System.out.println(selectedMember.get(0)+ " and " + selectedMember.get(1));
        //System.out.println(relation);
        Member m1 = selectedMember.get(0);
        Member m2 = selectedMember.get(1);
        relation = tree.callRelationship(m1,m2);
        System.out.println(relation);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("THÔNG BÁO");
        alert.setHeaderText("                       quan hệ là: " + relation + " !");
        alert.setContentText("Successfully!");
        alert.showAndWait();
        selectedMember.clear();
        //treeTableView.getSelectionModel().clearSelection();


    }

    public void getInfor(){
        Member m = treeTableView.getSelectionModel().getSelectedItem().getValue();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông tin thành viên");
        alert.setHeaderText(m.getFullName());
        alert.setContentText("Sinh năm " + m.getNgaySinh() + "\nQuê quán : " + m.getAddress()
                + "\nCông việc là " + m.getJob()
                + "\nMã cha là " + m.getMaCha());
        alert.showAndWait();
    }

    public void add(ActionEvent e) throws IOException {
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
    public void deleteMember() throws  SQLException {
        ControllerForDangNhap login = new ControllerForDangNhap();
        Member m = treeTableView.getSelectionModel().getSelectedItem().getValue();

        Statement statement = null;
        try {
            statement = MySQLConnUtils.getJDBCConnection().createStatement();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        String sql = "DELETE FROM " + login.getLogin() + ".member WHERE maTV = " + m.getMaTV();
        Alert alert = new Alert(Alert.AlertType.NONE, "BẠN CÓ MUỐN XÓA!", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            statement.executeUpdate(sql);

            Alert delete = new Alert(Alert.AlertType.WARNING);
            delete.setTitle("THÔNG BÁO");
            delete.setHeaderText("               ĐÃ XÓA!");
            delete.setContentText("*WARNING: FBI");
            delete.show();

        }
    }

    public void suaTV(ActionEvent e) throws IOException{
        Member m = treeTableView.getSelectionModel().getSelectedItem().getValue();
        setMember(m);

        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("suathanhvien.fxml"));
        Parent dangnhapParent = loader.load();
        Scene scene = new Scene(dangnhapParent);
        stage.setScene(scene);
    }
    public void setMember(Member m1){
        this.m = m1;
    }
    public static Member getMember(){
        return m;
    }
    static Member m;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }
}
