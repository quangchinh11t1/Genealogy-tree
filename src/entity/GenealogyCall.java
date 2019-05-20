package entity;

/**
 * Interface phục vụ cho hoạt động gọi tên quan hệ
 */
public interface GenealogyCall {
    final String[] relationArray = {"anh-em", "chị-em",
            "bác-cháu", "chú-cháu", "cô-cháu", "thím-cháu",
            "cậu-cháu", "dì-cháu", "mợ-cháu",
            "ông-cháu", "bà-cháu",
            "cụ-chắt", "vợ-chồng"
    };
    final String[] ancesRelationArray = {
        "bố-con", "mẹ-con",
        "ông nội-cháu", "bà nội-cháu", "ông ngoại-cháu", "bà ngoại-cháu",
        "cụ nội-chắt", "cụ ngoại-chắt"
    };

    /**
     * gọi tên chuẩn dựa vào relation() và checkAnces()
     */
    public String callRelationship(Member m1, Member m2);

    /**
     * quan hệ họ hàng
     */
    public String relation(Member m1, Member m2);

    /**
     * lưu tên các ancestor của member
     */
    public String[] ancesRelation(Member member, Member[] ancesMember);

    /**
     *check quan hệ huyết thống
     */
    public Boolean checkAnces(Member m1, Member m2);

}

