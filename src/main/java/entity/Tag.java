package entity;

public class Tag {
    private Integer id;
    private Integer question_id;
    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return question_id;
    }

    public void setQuestionId(Integer question_id) {
        this.question_id = question_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
