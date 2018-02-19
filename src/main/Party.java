package main;

/*
    Class Party - parties of the court case.
    Commented out class variables may be needed later.
 */

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

    public Party(int id, String name, String role, PreviousRole[] prevR) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.previousRoles = prevR;
    }

    public String getRole() {
        return this.role;
    }

    public PreviousRole[] getPreviousRoles() {
        return this.previousRoles;
    }

    public String getName() {
        return this.name;
    }
}
