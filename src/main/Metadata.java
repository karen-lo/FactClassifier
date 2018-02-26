package main;

public class Metadata {
    private String root_term;
    private String relationship;
    private String nsubj;
    private String classification;

    public Metadata(String root_term) {
        this.root_term = root_term;
    }

    public String getRootTerm() {
        return this.root_term;
    }

    public String getRelationship() {
        return this.relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getNsubj() {
        return nsubj;
    }

    public void setNsubj(String nsubj) {
        this.nsubj = nsubj;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
