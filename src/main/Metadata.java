package main;

public class Metadata {
    private String subject;
    private String subject_name;

    public Metadata() {}
    public Metadata(String s, String n) {
        this.subject = s;
        this.subject_name = n;
    }

    public String getSubject() {
        return subject;
    }

    public String getSubjectName() {
        return subject_name;
    }
}
