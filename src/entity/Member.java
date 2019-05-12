package entity;

/**
 * class đặc trưng cho 1 thành viên trong cơ sở dữ liệu
 * class chỉ bao gồm constructor và getter setter
 */
public class Member {
    private String fullName;
    private Integer ngaySinh;
    private Integer ngayMat;
    private Integer maTV ;
    private Integer doi ;
    private Integer maCha;
    private Integer maVkCk;
    private String address;
    private Boolean isMen;
    private String job;

    public Member() {
        this.fullName = null;
        this.ngaySinh = null;
        this.ngayMat = null;
        this.maTV = null;
        this.doi = null;
        this.maCha = null;
        this.maVkCk = null;
        this.address = null;
        this.isMen = null;
        this.job = null;
    }

    public Member(Member member ) {
        this.fullName = member.fullName;
        this.ngaySinh = member.ngaySinh;
        this.ngayMat = member.ngayMat;
        this.maTV = member.maTV;
        this.doi = member.doi;
        this.maCha = member.maCha;
        this.maVkCk = member.maVkCk;
        this.address = member.address;
        this.isMen = member.isMen;
        this.job = member.job;
    }

    //constructor for treeNode
    public Member(String fullName, Integer ngaySinh, Integer maTV, Integer doi, Integer maCha, Integer maVkCk, boolean isMen, String job) {
        this.fullName = fullName;
        this.ngaySinh = ngaySinh;
        this.maTV = maTV;
        this.doi = doi;
        this.maCha = maCha;
        this.maVkCk = maVkCk;
        this.isMen = isMen;
        this.job = job;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Integer ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Integer getNgayMat() {
        return ngayMat;
    }

    public void setNgayMat(Integer ngayMat) {
        this.ngayMat = ngayMat;
    }

    public Integer getMaTV() {
        return maTV;
    }

    public void setMaTV(Integer maTV) {
        this.maTV = maTV;
    }

    public Integer getDoi() {
        return doi;
    }

    public void setDoi(Integer doi) {
        this.doi = doi;
    }

    public Integer getMaCha() {
        return maCha;
    }

    public void setMaCha(Integer maCha) {
        this.maCha = maCha;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isMen() {
        return isMen;
    }

    public void setMen(boolean men) {
        isMen = men;
    }

    public Integer getMaVkCk() {
        return maVkCk;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setMaVkCk(Integer maVkCk) {
        this.maVkCk = maVkCk;
    }
}
