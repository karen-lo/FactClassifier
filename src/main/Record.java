package main;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Record {
    private String file_id;
    private String case_type;
    private String[] holding;
    private String[] facts;
    private Party[] parsed_parties;
    private ArrayList<Party> complainants;
    private ArrayList<Party> respondents;
    private Metadata[] holding_classification;
    private Metadata[] facts_classification;


    public Record(String f_id, String t, String[] h, String[] f, Party[] p) {
        this.file_id = f_id;
        this.case_type = t;

        if(h != null) {
            this.holding = h;
            this.holding_classification = new Metadata[this.holding.length];
        }
        else {
            this.holding = new String[0];
            this.holding_classification = new Metadata[0];
        }

        if(f != null) {
            this.facts = f;
            this.facts_classification = new Metadata[this.facts.length];
        } else {
            this.facts = new String[0];
            this.facts_classification = new Metadata[0];
        }

        this.parsed_parties = p;
        this.complainants = new ArrayList<>();
        this.respondents = new ArrayList<>();
    }

    public void classifyHolding() {
        for(int i=0; i<this.holding.length; i++) {
            String sentence = this.holding[i];
            this.holding_classification[i] = classifySentence(sentence);
        }
    }

    public void classifyFacts() {
        for(int i=0; i<this.facts.length; i++) {
            String fact = this.facts[i];
            this.facts_classification[i] = classifySentence(fact);
        }
    }

    private Metadata classifySentence(String s) {
        Properties property = new Properties();
        property.setProperty("annotators", "depparse");
//        try {
//            property.load(IOUtils.readerFromString("CoreNLP/src/edu/stanford/nlp/pipeline/StanfordCoreNLP-chinese.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        StanfordCoreNLP pipeline = new StanfordCoreNLP(property);

        /* Annotate sentence */
        Annotation annotated_s = new Annotation(s);
        pipeline.annotate(annotated_s);

        return new Metadata("Unknown", "Unknown");

        /*String[] terms = s.split("\\s+", 0);
        for(String t : terms) {
            for(Party complainant : this.complainants) {
                if(complainant.getAliases().contains(t)) {
                    return new Metadata(translateRole(t, complainant), complainant.getName());
                }

                for(String alias : complainant.getAliases()) {
                    if(t.contains(alias)) {
                        return new Metadata(translateRole(t, complainant), complainant.getName());
                    }
                }
            }

            for(Party respondent : this.respondents) {
                if(respondent.getAliases().contains(t)) {
                    return new Metadata(translateRole(t, respondent), respondent.getName());
                }

                for(String alias : respondent.getAliases()) {
                    if(t.contains(alias)) {
                        return new Metadata(translateRole(t, respondent), respondent.getName());
                    }
                }
            }
        }
        return new Metadata("Unknown", "Unknown");*/
    }

    private String translateRole(String s, Party p) {
        switch(s) {
            case "上诉人":
                return "Apellant";
            case "申诉人":
                return "Apellant"
;            case "被上诉人":
                return "Apellee";
            case "被申诉人":
                return "Apellee";
            case "原告":
                return "Plaintiff";
            case "被告":
                return "Defendant";
            default:
                return p.getRole();
        }
    }

    public void setParties() {
        for(Party p : this.parsed_parties) {
            String r = p.getRole();
            if(r != null) {
                if(r.equals("Defendant") || r.equals("PersonSubjectToEnforcement")) {
                    this.respondents.add(p);
                } else if(r.equals("Plaintiff") || r.equals("ExecutionApplicant")) {
                    this.complainants.add(p);
                } else if(r.equals("Respondent") || r.equals("Appellee")) {
                    this.respondents.add(p);
                } else if(r.equals("Complainant") || r.equals("Appellant")) {
                    this.complainants.add(p);
                }
            }
        }
    }

    public String getFile_id() { return file_id; }

    public Metadata[] getFactsClassification() { return this.facts_classification; }

    public Metadata[] getHoldingClassification() { return this.holding_classification; }

    public ArrayList<Party> getComplainants() {
        return this.complainants;
    }

    public ArrayList<Party> getRespondants() {
        return this.respondents;
    }
}
