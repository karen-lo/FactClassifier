package main;

/*
    Class Party - parties of the court case.
    Commented out class variables may be needed later.
 */

import java.util.ArrayList;
import java.util.HashSet;

public class Party {
//    private String address;
//    private String[] agentsForIds;
//    private String entityType;
    private int id;
    private String name;
    private PreviousRole[] previousRoles;
    private String role;
//    private boolean roleAssigned;
//    private String[] actions;
    private HashSet<String> aliases;

    public Party(int id, String name, String role, PreviousRole[] prevR) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.previousRoles = prevR;
        this.aliases = new HashSet<>();
        this.aliases.add(name);
    }

    public void setAliases() {
        addAlias(this.role);
        for(int i=0; i<this.previousRoles.length; i++) {
            addAlias(this.previousRoles[i].getRole());
        }
    }

    private void addAlias(String r) {
        if(r.equals("Complainant") || r.equals("Appellant")) {
            this.aliases.add("上诉人");
            this.aliases.add("申诉人");
        }
        if(r.equals("Respondent") || r.equals("Appellee")) {
            this.aliases.add("被上诉人");
            this.aliases.add("被申诉人");
        }
        if(r.equals("Plaintiff") || r.equals("ExecutionApplicant")) {
            this.aliases.add("原告");
        }
        if(r.equals("Defendant") || r.equals("PersonSubjectToEnforcement")) {
            this.aliases.add("被告");
        }
    }

    public String getName() {
        return this.name;
    }

    public PreviousRole[] getPreviousRoles() {
        return this.previousRoles;
    }

    public String getRole() { return this.role; }

    public HashSet<String> getAliases() {
        return this.aliases;
    }
}
