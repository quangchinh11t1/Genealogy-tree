package app;

import entity.GenealogyTree;
import sql.MySQL;

public class MainTest {
    public static void main(String[] args) throws Exception {
        GenealogyTree tree = new GenealogyTree();
        tree.callRelationship(tree.getMember(11), tree.getMember(12));


    }
}
