package main;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.util.ArrayList;
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

        int sentNo = 1;
        for(CoreMap a : annotated_s) {
            System.out.println("Sentence #" + sentNo + " tokens:");
//            for(CoreMap token : a.get(CoreAnnotations.TokensAnnotation.class)) {
//                System.out.println(token.toShorterString("Text", "CharacterOffsetBegin", "CharacterOffset", "Index", "PartOfSpeech", "NamedEntityTag"));
//            }
            System.out.println("Sentence #" + sentNo + " basic dependencies are:");
            SemanticGraph dependencies = a.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            IndexedWord root = dependencies.getFirstRoot();
//            System.out.println(a.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.LIST));
            System.out.println(root.toString());
            for(SemanticGraphEdge e : dependencies.edgeIterable()) {
                if(e.getGovernor().word().equals(root.word()) && e.getRelation().toString().equals("nsubj")) {
                    System.out.println(e.getDependent().word());
                }
            }

            sentNo++;
        }


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
