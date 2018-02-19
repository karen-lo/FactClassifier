package main;

import java.util.ArrayList;

public class Record {
    private String file_id;
    private String case_type;
    private String[] holding;
    private String[] facts;
    private Party[] parsed_parties;
    private ArrayList<Party> plaintiffs;
    private ArrayList<Party> defendants;

    public Record(String f_id, String t, String[] h, String[] f, Party[] p) {
        this.file_id = f_id;
        this.case_type = t;
        this.holding = h;
        this.facts = f;
        this.parsed_parties = p;
        this.plaintiffs = new ArrayList<>();
        this.defendants = new ArrayList<>();
    }

    public void setPlaintiffsAndDefendants() {
        for(Party p : this.parsed_parties) {
            String r = p.getRole();
            String n = p.getName();
            PreviousRole[] pr = p.getPreviousRoles();


            if(r != null) {
//                System.out.println(n + "'s role: " + r);
                if (r.equals("Defendant") || r.equals("PersonSubjectToEnforcement")) {
                    this.defendants.add(p);
//                  System.out.println(p.getName() + " is the defendant.");
                } else if (r.equals("Plaintiff") || r.equals("ExecutionApplicant")) {
                    this.plaintiffs.add(p);
//                  System.out.println(p.getName() + " is the plaintiff.");
                } else if (pr != null) {
                    for (PreviousRole prev : pr) {
                        r = prev.getRole();
//                    System.out.println("previous role: " + r);

                        if (r.equals("Defendant") || r.equals("PersonSubjectToEnforcement")) {
                            this.defendants.add(p);
//                        System.out.println(p.getName() + " is the defendant.");
                        } else if (r.equals("Plaintiff") || r.equals("ExecutionApplicant")) {
                            this.plaintiffs.add(p);
//                        System.out.println(p.getName() + " is the plaintiff.");
                        }
                    }
                }
            }
        }
    }

    public String getFile_id() {
        return file_id;
    }

    public ArrayList<Party> getPlaintiffs() {
        return plaintiffs;
    }

    public ArrayList<Party> getDefendants() {
        return defendants;
    }

}
