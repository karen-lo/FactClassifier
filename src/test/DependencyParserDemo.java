package edu.stanford.nlp.parser.nndep.demo;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.logging.Redwood;

import java.io.StringReader;
import java.util.Collection;
import java.util.List;

/**
 * Demonstrates how to first use the tagger, then use the NN dependency
 * parser. Note that the parser will not work on untagged text.
 *
 * @author Jon Gauthier
 */
public class DependencyParserDemo  {

  /** A logger for this class */
  private static Redwood.RedwoodChannels log = Redwood.channels(DependencyParserDemo.class);

  public static void main(String[] args) {
    String modelPath = "edu/stanford/nlp/models/parser/nndep/UD_Chinese.gz";
    String taggerPath = "edu/stanford/nlp/models/pos-tagger/chinese-distsim/chinese-distsim.tagger";

    for (int argIndex = 0; argIndex < args.length; ) {
      switch (args[argIndex]) {
        case "-tagger":
          taggerPath = args[argIndex + 1];
          argIndex += 2;
          break;
        case "-model":
          modelPath = args[argIndex + 1];
          argIndex += 2;
          break;
        default:
          throw new RuntimeException("Unknown argument " + args[argIndex]);
      }
    }

    String text = "本院 认为 上诉人河南工程学院 申请 撤回 上诉 起诉 是 其 真实 意思 表示 应予 准许 鉴于 河南工程学院 已 撤回 一审 起诉 一审 判决 亦 无 存在 的 必要 应予 撤销 本案 应 终结 诉讼";

    // Experimenting with different methods
    MaxentTagger tagger = new MaxentTagger(taggerPath);
    DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);

    DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
    for (List<HasWord> sentence : tokenizer) {
      List<TaggedWord> tagged = tagger.tagSentence(sentence);
      GrammaticalStructure gs = parser.predict(tagged);

      Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

      // Print typed dependencies
      //log.info(gs);
      System.out.println(gs.toString());

    }
  }

}
