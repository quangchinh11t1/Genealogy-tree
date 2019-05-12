package entity;

/**
 * cây quan hệ
 * xử lí các thao tác tác động lên cây
 */

import controller.ControllerForDangNhap;
import sql.MySQL;

import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class GenealogyTree implements GenealogyCall {
    private ArrayList<Member> tree = new ArrayList<Member>();

    public GenealogyTree(ArrayList<Member> tree) {
        this.tree = tree;
    }

    public GenealogyTree() {
        try {
            getData();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Member> getTree() {
        return tree;
    }

    public void setTree(ArrayList<Member> tree) {
        this.tree = tree;
    }

    /**
     *
     * lấy dữ liệu từ sql ném vào tree
     */
    private void getData() throws Exception {
        ControllerForDangNhap login = new ControllerForDangNhap();
        MySQL sql = new MySQL();
        sql.setUrl(sql.getUrl() + login.getLogin());
        ResultSet result = sql.getMemberData();
        while (result.next()){
            Member dataMember = new Member();
            dataMember.setFullName(result.getString("hotenTV"));
            dataMember.setMaTV(Integer.valueOf(result.getString("maTV")));
            dataMember.setDoi(Integer.valueOf(result.getString("maDoi")));
            dataMember.setAddress(result.getString("diaChi"));
            dataMember.setJob(result.getString("ngheNghiep"));
            if(null == result.getString("maCha") || "" == result.getString("maCha"))
                dataMember.setMaCha(null);
            else
                dataMember.setMaCha(Integer.valueOf(result.getString("maCha")));
            if(String.valueOf(result.getString("gioiTinh")).equals("nam"))
                dataMember.setMen(true);
            else
                dataMember.setMen(false);
            dataMember.setNgaySinh(Integer.valueOf(result.getString("namSinh")));
            //System.out.println(dataMember.getFullName()+ " "+ dataMember.getNgaySinh());
            dataMember.setMaVkCk(Integer.valueOf(result.getString("maVkCk")));
            tree.add(dataMember);
        }
        for (int i=0; i<tree.size(); i++){
          //  System.out.println(tree.get(i).getFullName() + " " + tree.get(i).isMen());
        }
        System.out.println(tree.size());
    }

    public Member getMember(Integer maTV){
        Member member = new Member() ;
        for (int i=0; i<tree.size(); i++){
            if(maTV ==  tree.get(i).getMaTV())
                member = tree.get(i);
        }
        return member;
    }

    void addNode(){

    }

    void deleteNode(){

    }

    void update(){

    }

    /**
     * đảo ngược quan hệ
     */
    public String reverseRelation(String relation){
        String reverseTmp = new String() ;
        String [] words = relation.split("-");
        reverseTmp = words[1] + "-" + words[0];
        //System.out.println(reverseTmp);
        return reverseTmp;
    }

    /**
     * mang luu cac node cha
     */
    public Member[] ancestor(Member member){
        final Integer soDoi = 4;        // lưu ancestor của cây có height max = 5
        Member[] ances = new Member[soDoi];
        for(int i=0; i<soDoi; i++){
            ances[i] = new Member();
        }
        if(null == member.getMaCha())
            return ances;
        ances[0] = getMember(member.getMaCha());
        for (int i=1; i<soDoi; i++){
            ances[i] = getMember(ances[i-1].getMaCha());
        }
        return ances;
    }

    /**
     * chỉ xét trường hợp m1 là đời lớn hơn nếu nhỏ thì relation(m2,m1)
     */
    @Override
    public String  relation(Member m1, Member m2){
        String relation = new String();     // biến quan hệ của m1, m2 trả về
        int hieuDoi = m2.getDoi() - m1.getDoi();
        boolean isElderBranch = false;
        boolean flagFindRoot = false;
        final Integer soDoi = 4;
        Member[] ancestorM1 = ancestor(m1);
        Member[] ancestorM2 = ancestor(m2);
        for (int i=0; i<soDoi; i++){
            System.out.println(ancestorM1[i].getMaTV()+ " " + ancestorM2[i].getMaTV());
        }
        Integer rootDoi = null;
        /**
         * tim root chung cua m1 vs m2
         * tìm nhánh lớn
         */
        if(hieuDoi >=0){
            /**
             * root chung
             */
            // cùng cha
            if(m1.getMaCha() == m2.getMaCha()){
                rootDoi = m1.getMaCha();
                flagFindRoot = true;
            }

            // nếu là ngoại tộc return relation luôn
            if(m1.getMaCha() == null || m2.getMaCha() == null){
                Integer lifePartner ;
                String switchKey ;
                if(m1.getMaCha() == null){
                    lifePartner = m1.getMaVkCk();
                    switchKey = callRelationship(getMember(lifePartner),m2);
                }
                else{
                    lifePartner = m2.getMaVkCk();
                    switchKey = callRelationship(getMember(lifePartner),m1);
                }
                switch (switchKey){
                    case "bác-cháu":
                        relation = relationArray[2];
                        break;
                    case "chú-cháu":
                        relation = relationArray[5];
                        break;
                    case "dì-cháu":
                        relation = relationArray[3];
                        break;
                    case "cậu-cháu":
                        relation = relationArray[8];
                        break;
                    case "cô-cháu":
                        relation = relationArray[3];
                }
                return relation;
            }
            // m1 là gốc cây
            if((m1.getMaCha().equals("")) ){
                rootDoi = m1.getMaTV();
                flagFindRoot = true;
                isElderBranch = true;
            }
            // khác cha
            int chay = 0;
            while (!flagFindRoot){
                if(ancestorM1[chay].getMaTV() != null){
                    for (int j=0; j<soDoi; j++){
                        if(ancestorM2[j].getMaTV() != null){
                            if(ancestorM1[chay].getMaTV() == ancestorM2[j].getMaTV()){
                                rootDoi = ancestorM1[chay].getMaTV();
                                flagFindRoot = true;
                                //nhánh lớn
                            /*if(ancestorM1[chay-1].getNgaySinh()>ancestorM2[j-1].getNgaySinh())
                                isElderBranch = true;*/
                            }
                        }
                    }
                }
                chay++;
            }
            /**
             * tìm nhánh lớn
             */
            int root = getMember(rootDoi).getDoi(); // đời của root
            System.out.println("root " + root+ " ");
            // lưu chỉ số của ancestor của m1, m2 mà có quan hệ anh em (con của root)
            int rootIndexAncestorM1 = m1.getDoi() - (root+1) -1;
            int rootIndexAncestorM2 = m2.getDoi() - (root+1) -1 ;
            System.out.println("rootAnces: " + rootIndexAncestorM1);

            //TH thông thường. trùng lặp ???
            if(0 == rootIndexAncestorM1 && 0 == rootIndexAncestorM2){
                if(ancestorM1[rootIndexAncestorM1].getNgaySinh() < ancestorM2[rootIndexAncestorM2].getNgaySinh())
                    isElderBranch = true;
            }
            //m1 là con của rootDoi or m1 là gốc cây
            if(-1 == rootIndexAncestorM1 || null == m1.getMaCha()){
                if(m1.getNgaySinh() < ancestorM2[rootIndexAncestorM2].getNgaySinh())
                    isElderBranch = true;
            }
            else {
                if(ancestorM1[rootIndexAncestorM1].getNgaySinh() < ancestorM2[rootIndexAncestorM2].getNgaySinh())
                    isElderBranch = true;
            }

            System.out.println(isElderBranch);
            System.out.println("rootDoi: " + rootDoi);

        }
        /**
         * goi ten
         */
        switch (hieuDoi){
            case 0:             //quan hệ anh em
                System.out.println("case 0");
                if(m1.getMaCha() == m2.getMaCha()){
                    if(m1.getNgaySinh() < m2.getNgaySinh()){
                        if(m1.isMen())
                            relation = relationArray[0];
                        else
                            relation = relationArray[1];
                    }
                    else
                        relation = reverseRelation(relation(m2,m1));
                }
                else {
                    if(true == isElderBranch){
                        if(m1.isMen())
                            relation = relationArray[0];
                        else
                            relation = relationArray[1];
                    }
                    else
                        relation = reverseRelation(relation(m2,m1));
                }
                break;
            case 1:             // m1 ngang hàng với parent của m2
                System.out.println("case 1");
                if(isElderBranch)
                    relation = relationArray[2];    // bác
                else{
                    if(m1.isMen()){     // chú or cậu    ko xét ngoại tộc
                        if(getMember(m2.getMaCha()).isMen()) // M1.cha la nam? -> nhánh nội/ngoại
                            relation = relationArray[3];
                        else
                            relation = relationArray[6];
                    }
                    else{               // cô or dì(em họ của mẹ cũng là dì)
                        System.out.println(m1.getFullName()+ " " +  m1.isMen());
                        if(getMember(m2.getMaCha()).isMen())
                            relation = relationArray[4];
                        else
                            relation = relationArray[7];
                    }
                }
                break;
            case 2:
                System.out.println("case 2");
                if(m1.isMen())  // ông hay bà?
                    relation = relationArray[9];
                else
                    relation = relationArray[10];
                break;
            case 3:             // cụ
                System.out.println("case 3");
                relation = relationArray[11];
        }

        if(hieuDoi < 0)
            relation = reverseRelation(relation(m2,m1));

        /**
         * detail
         */


        return relation;
    }

    @Override
    public boolean checkAnces(Member m1, Member m2) {
        boolean kq = false;
        int hieuDoi = m2.getDoi() - m1.getDoi();
        if(hieuDoi >=0){
            Member[] tmp = ancestor(m2);
            for (int i=0; i<tmp.length; i++){
                //System.out.println(tmp[i].getMaTV()+"-"+ tmp[i].getMaVkCk() + " " + m1.getMaTV());
                if(m1.getMaTV() == tmp[i].getMaTV() || m1.getMaTV() == tmp[i].getMaVkCk()) {
                    kq = true;
                }
            }
        }
        else {
            checkAnces(m2, m1);
        }
        System.out.println("check ances: " + kq);
        return kq;
    }

    @Override
    public String callRelationship(Member m1, Member m2) {
        String kq = new String();
        //checkAnces(m1,m2);
        if(checkAnces(m1,m2) || checkAnces(m2,m1)){
            System.out.println("ancestor");
            int stt = m1.getDoi() - m2.getDoi() ;
            if(stt >= 0){
                if(m2.getMaCha() == null){ // check ngoại tộc
                    System.out.println("ngoai toc");
                    String[] tmp = ancesRelation(m1,ancestor(m1));
                    kq = tmp[stt-1];
                    switch (kq){
                        case "bố-con":
                            kq = ancesRelationArray[1];
                            break;
                        case "mẹ-con":
                            kq = ancesRelationArray[0];
                            break;
                        case "ông nội-cháu":
                            kq = ancesRelationArray[3];
                            break;
                        case "bà nội-cháu":
                            kq = ancesRelationArray[2];
                            break;
                        case "ông ngoại-cháu":
                            kq = ancesRelationArray[5];
                            break;
                        case "bà ngoại-cháu":
                            kq = ancesRelationArray[4];
                            break;
                    }
                }
                else{
                    String[] tmp = ancesRelation(m1,ancestor(m1));
                    kq = tmp[stt-1];
                }
            }
            else {
                /*String[] tmp = ancesRelation(m2,ancestor(m2));
                kq = reverseRelation(tmp[abs(stt)-1]);*/
                callRelationship(m2,m1);
            }
        }
        else kq = relation(m1,m2);
        System.out.println(kq);
        return kq;
    }

    @Override
    public String[] ancesRelation(Member m1, Member[] ances) {
        String[] ancesRela = new String[4];
        int count = 0;

        for(int i=0; i<ances.length; i++){
           // System.out.println(ances[i].getFullName());
        }

        while(count < ances.length){
            switch (count){
                case 0:
                    System.out.println(ances[count].getFullName()+ " " + ances[count].isMen());
                    if(ances[count].isMen())    //bố or mẹ
                        ancesRela[0] = ancesRelationArray[0];
                    else
                        ancesRela[0] = ancesRelationArray[1];
                    break;
                case 1:
                    //System.out.println(count + " " + ances[count].getFullName());
                    if(null == ances[count].getFullName())
                        break;
                    else {
                        if(ances[count].isMen()){   // ong
                            if(ances[0].isMen())  // check noi ngoai
                                ancesRela[1] = ancesRelationArray[2];
                            else
                                ancesRela[1] = ancesRelationArray[4];
                        }
                        else {
                            if(ances[0].isMen())
                                ancesRela[1] = ancesRelationArray[3];
                            else
                                ancesRela[1] = ancesRelationArray[5];
                        }
                    }
                    break;
                case 2:
                    if(null == ances[count].getFullName())
                        break;
                    else {
                        if(ances[0].isMen())
                            ancesRela[2] = ancesRelationArray[6];
                        else
                            ancesRela[2] = ancesRelationArray[7];
                    }
                    break;
            }
            count++;
        }
        // check ket qua
        for (int i=0; i<ances.length; i++){
            //System.out.println(ancesRela[i]);
        }
        return ancesRela;
    }
}
