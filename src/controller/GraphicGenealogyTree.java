package controller;

import entity.GenealogyTree;
import entity.Member;
import entity.TreeNode;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;

public class GraphicGenealogyTree {
    /*private GenealogyTree tree = new GenealogyTree();

    private TreeItem<Member> root =new TreeItem<>(tree.getTree().get(0));*/


    private TreeTableView<Member> treeTableView;

    public GraphicGenealogyTree() {
        treeTableView = getTree();
        selectItem();
    }

    /**
     * lấy từ genealogyTree add vào treeTableView
     * @return
     */
    public TreeTableView getTree(){
        GenealogyTree tree = new GenealogyTree();
        TreeItem<Member> root =new TreeItem<>(tree.getTree().get(0));
        root.setExpanded(true);
        for(int i=1; i<tree.getTree().size(); i++){
            root.getChildren().add(new TreeItem<>(tree.getTree().get(i)));
        }


        TreeTableColumn<Member, String> nameColumn = new TreeTableColumn<>("Name");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Member, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getFullName())
        );

        TreeTableView<Member> treeTableView = new TreeTableView<>(root);
        treeTableView.getColumns().setAll(nameColumn);

        /**
         * lựa chọn được nhiều ô
         */
        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeTableView.getSelectionModel().setCellSelectionEnabled(true);


        return treeTableView;
    }

    public TreeTableView<Member> getTreeTableView() {
        return treeTableView;
    }

    public void setTreeTableView(TreeTableView<Member> treeTableView) {
        this.treeTableView = treeTableView;
    }
    public void selectItem(){
        System.out.println(treeTableView.getSelectionModel().getSelectedItem().getValue().getFullName());
        //treeTableView.getSelectionModel().select();
    }
}
