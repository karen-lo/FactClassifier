package main;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private Properties property;
    private StanfordCoreNLP pipeline;


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

        this.property = new Properties();
        this.property.setProperty("annotators", "tokenize, ssplit, pos, depparse");
        this.property.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/chinese-distsim/chinese-distsim.tagger");
        this.property.setProperty("depparse.model", "edu/stanford/nlp/models/parser/nndep/UD_Chinese.gz");
        this.property.setProperty("depparse.language", "chinese");
        this.pipeline = new StanfordCoreNLP(this.property);
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
        /* Annotate sentence */
        Annotation sentence = new Annotation(s);
        pipeline.annotate(sentence);

        List<CoreMap> annotated_s = sentence.get(CoreAnnotations.SentencesAnnotation.class);
        if(annotated_s.size() == 0) return new Metadata("");

        CoreMap m = annotated_s.get(0);
        SemanticGraph dependencies = m.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
        IndexedWord root = dependencies.getFirstRoot();
        Metadata metadata = new Metadata(root.word());

        System.out.println(m.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.LIST));
//        System.out.println(root.toString());
        /* Get subject */
        for(SemanticGraphEdge e : dependencies.edgeIterable()) {
            if(e.getGovernor().word().equals(root.word()) && e.getRelation().toString().equals("nsubj")) {
                System.out.println(e.getDependent().word());
                metadata.setRelationship(e.getRelation().toString());
                metadata.setNsubj(e.getDependent().word());
                metadata.setClassification(translateRole(e.getDependent().word()));
                return metadata;
            }
        }

        metadata.setRelationship("none");
        metadata.setNsubj("none");
        metadata.setClassification("Unknown");
        return metadata;
    }

    private String translateRole(String s) {
        for(Party p : this.complainants) {
            HashSet<String> hs = p.getAliases();
            for(String alias : hs) {
                if(alias.contains(s) || s.contains(alias)) return p.getRole();
            }
        }
        for(Party p : this.respondents) {
            HashSet<String> hs = p.getAliases();
            for(String alias : hs) {
                if(alias.contains(s) || s.contains(alias)) return p.getRole();
            }
        }
        return "Unknown";
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
