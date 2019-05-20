package entity;

/**
 * cây quan hệ
 * xử lí các thao tác tác động lên cây
 */

import controller.ControllerForDangNhap;
import sql.MySQL;

import java.sql.ResultSet;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

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
        Member member = new Member();
        for (int i=0; i<tree.size(); i++){
            //System.out.println(tree.get(i).getMaTV());
            if(maTV ==  tree.get(i).getMaTV()){
                member = tree.get(i);
            }
        }
        System.out.println(member.getFullName());
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
        System.out.println("this is reverse: " + relation);
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
         * gọi tên luôn ngoại tộc
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
                if(m1.getMaTV() == m2.getMaVkCk() || m1.getMaVkCk() == m2.getMaTV())
                    return relation = relationArray[12];
                if(m1.getMaCha() == null){
                    lifePartner = m1.getMaVkCk();
                    switchKey = callRelationship(getMember(lifePartner),m2);
                }
                else{
                    System.out.println("m2.getmacha null");
                    lifePartner = m2.getMaVkCk();
                    System.out.println(getMember(lifePartner).getFullName());
                    //switchKey = callRelationship(getMember(lifePartner),m1);
                    switchKey = callRelationship(m1, getMember(lifePartner));
                }
                switch (switchKey){     // chỉ ngoại tộc
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
                        break;
                    case "anh-em":
                        relation = "anh-em";
                        break;
                    case "chị-em":
                        relation = "chị-em";
                        break;
                    case "em-chị":
                        relation = "em-chị";
                        break;
                    case "em-anh":
                        relation = "em-anh";
                        break;
                    case "ông nội-cháu":
                        relation = ancesRelationArray[3];
                        break;
                    case "bà-nội-cháu":
                        relation = ancesRelationArray[2];
                        break;
                    case "ông ngoại-cháu":
                        relation = ancesRelationArray[5];
                        break;
                    case "bà ngoại-cháu":
                        relation = ancesRelationArray[4];
                        break;
                    case "ông-cháu":
                        relation = "bà-cháu";
                        break;
                    case "bà-cháu":
                        relation = "ông-cháu";
                        break;
                    default:
                        relation = "khong biet";
                }
                return relation;
            }
            // m1 là gốc cây
            if((m1.getMaCha().equals("-1")) ){
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
                if(-1 == rootIndexAncestorM1 && -1 == rootIndexAncestorM2){
                    if(m1.getNgaySinh() < m2.getNgaySinh())
                        isElderBranch = true;
                }
                else if(m1.getNgaySinh() < ancestorM2[rootIndexAncestorM2].getNgaySinh())
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
                System.out.println("day roi");
                System.out.println(m1.getMaTV());
                if(m1.isMen())  // ông hay bà?
                {
                    relation = relationArray[9];
                    System.out.println(relation);
                }
                else
                    relation = relationArray[10];
                System.out.println(relation);
                break;
            case 3:             // cụ
                System.out.println("case 3");
                relation = relationArray[11];
                break;
        }

        if(hieuDoi < 0)
            relation = reverseRelation(relation(m2,m1));

        /**
         * detail
         */


        return relation;
    }

    @Override
    public Boolean checkAnces(Member m1, Member m2) {
        boolean kq = false;
        int hieuDoi = m2.getDoi() - m1.getDoi();
        if(m1.getMaVkCk() == m2.getMaTV())
            return false;
        if(hieuDoi >=0){
            if(m2.getMaCha() != null){
                Member[] tmp = ancestor(m2);
                for (int i=0; i<tmp.length; i++){
                    System.out.println(tmp[i].getMaTV()+"-"+ tmp[i].getMaVkCk() + " " + m1.getMaTV());
                    System.out.println(tmp[i].getMaTV()+"-"+ tmp[i].getMaVkCk() + " " + m1.getMaVkCk());
                    //if(tmp[i].getMaVkCk() == m1.getMaTV())
                    if(m1.getMaTV() == tmp[i].getMaTV() || m1.getMaTV() == tmp[i].getMaVkCk()
                            ||m1.getMaVkCk() == tmp[i].getMaTV() || m1.getMaVkCk() == tmp[i].getMaVkCk() ) {
                        System.out.println("check ancess true");
                        kq = true;
                        return kq;
                    }
                }
            }
            else{
                System.out.println("ca 2 null");
                return checkAnces(m1, getMember(m2.getMaVkCk()));
            }

        }
        else {
            return checkAnces(m2, m1);
        }
        System.out.println("check ances: " + kq);
        return kq;
    }

    @Override
    public String callRelationship(Member m1, Member m2) {
        String kq = new String();
        //checkAnces(m1,m2);
        int hieuDoi = m2.getDoi() - m1.getDoi();
        if(hieuDoi >=0){
            if(m1.getMaTV() == m2.getMaVkCk()){
                return relationArray[12];
            }
            if(/*null == m1.getMaCha() &&*/ null == m2.getMaCha()){
                System.out.println("ca 2 null ");
                return callRelationship(m1, getMember(m2.getMaVkCk()));
            }
            else{
                System.out.println("hello" + checkAnces(m1,m2));
                if(checkAnces(m1,m2) || checkAnces(m2,m1)){
                    String[] tmp = ancesRelation(m2, ancestor(m2));
                    int cs = (int) pow(2,(hieuDoi-1));
                    System.out.println(cs);
                    System.out.println(m2.getMaTV());
                    if(hieuDoi == 1){
                        if(null == m1.getMaCha())
                            kq = tmp[hieuDoi];
                        else
                            kq = tmp[hieuDoi-1];
                    }
                    else {
                        if(null == m1.getMaCha()){
                            System.out.println("dmm null ");
                            kq = tmp[cs+1];
                        }
                        else
                            kq = tmp[cs];
                    }
                    System.out.println(kq);
                    return kq;
                }
                else{
                    System.out.println(relation(m1,m2));
                    return relation(m1,m2);
                }

            }
        }
        else{
            kq = callRelationship(m2,m1);
            System.out.println(kq);
            return reverseRelation(callRelationship(m2,m1));
        }


        //setRelation(kq);
        //System.out.println("kq cuoi: "+ kq);
        //return kq;
        //return null;
    }

    public void swap(String m1, String m2){
        String tmp = m1;
        m1 = m2;
        m2 = m1;
    }
    @Override
    public String[] ancesRelation(Member m1, Member[] ances) {
        String[] ancesRela = new String[8];
        int count = 0;

        for(int i=0; i<ances.length; i++){
           // System.out.println(ances[i].getFullName());
        }

        while(count < ances.length){
            switch (count){
                case 0:
                    System.out.println(ances[count].getFullName()+ " " + ances[count].isMen());
                    ancesRela[0] = ancesRelationArray[0];
                    ancesRela[1] = ancesRelationArray[1];
                    if(!ances[count].isMen())
                        swap(ancesRela[0], ancesRela[1]);
                    break;
                case 1:
                    //System.out.println(count + " " + ances[count].getFullName());
                    if(null == ances[count].getFullName())
                        break;
                    else {
                        if(ances[0].isMen()){
                            ancesRela[2] = ancesRelationArray[2];
                            ancesRela[3] = ancesRelationArray[3];
                            System.out.println("nội 2: " + ancesRela[2]);
                            System.out.println(ances[count].getMaTV());
                            if(!ances[count].isMen())
                                swap(ancesRela[2], ancesRela[3]);
                            System.out.println("nội 2 after: " + ancesRela[2]);
                        }
                        else {
                            System.out.println("ngoai 2:" );
                            ancesRela[2] = ancesRelationArray[4];
                            ancesRela[3] = ancesRelationArray[5];
                            if(!ances[count].isMen())
                                swap(ancesRela[4], ancesRela[5]);
                        }
                        break;
                    }
                case 2:
                    if(null == ances[count].getFullName())
                        break;
                    else {
                        if(ances[0].isMen()){
                            ancesRela[4] = ancesRelationArray[6];
                        }
                        else
                            ancesRela[4] = ancesRelationArray[7];
                    }
                    break;
                case 3:

                    break;
            }
            count++;
        }
        // check ket qua
        for (int i=0; i<ancesRela.length; i++){
            System.out.println(i + " " + ancesRela[i]);
        }
        return ancesRela;
    }

    public void  kq(Member m1, Member m2){
        //String kq = new String();
        System.out.println(callRelationship(m1,m2));
        System.out.println("this is kq" );
        //return kq;
    }
}
