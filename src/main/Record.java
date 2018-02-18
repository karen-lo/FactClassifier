package main;

public class Record {
    private String file_id;
    private String case_type;
    private String[] holding;
    private String[] facts;
    private Party[] parsed_parties;

    public Record(String f_id, String t, String[] h, String[] f, Party[] p) {
        this.file_id = f_id;
        this.case_type = t;
        this.holding = h;
        this.facts = f;
        this.parsed_parties = p;
    }

    public String getFile_id() {
        return file_id;
    }
}
