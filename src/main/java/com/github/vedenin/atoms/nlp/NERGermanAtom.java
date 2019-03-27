package com.github.vedenin.atoms.nlp;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.AtomException;
import com.github.vedenin.atom.annotations.AtomTest;
import com.github.vedenin.atoms.nlp.exceptions.NERAtomException;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Atom(StanfordCoreNLP.class)
@AtomTest("NERGermanAtomTest")
@AtomException(NERAtomException.class)
public class NERGermanAtom implements NERAtomInterface {
    private final StanfordCoreNLP pipeline;

    public NERGermanAtom() {
        Properties props = getProperties();
        pipeline = new StanfordCoreNLP(props);
    }

    public NERGermanAtom(Properties props) {
        Properties defaults = getProperties();
        defaults.putAll(props);
        pipeline = new StanfordCoreNLP(defaults);
    }

    @Override
    public List<TokenDataAtom> getItems(final String text) {
        try {
            final CoreDocument doc = new CoreDocument(text);
            pipeline.annotate(doc);
            return doc.tokens()
                    .stream()
                    .map(e -> TokenDataAtom.of(e.word(), e.ner()))
                    .collect(
                            Collectors.toList());
        } catch (Exception exp) {
            throw new NERAtomException(exp);
        }
    }


    private Properties getProperties() {
        String germanModel = getModels("eunews.de.crf.gz");
        final Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/german/german-hgc.tagger");
        props.setProperty("ner.model",
                "edu/stanford/nlp/models/ner/german.conll.germeval2014.hgc_175m_600.crf.ser.gz,"+germanModel);
        props.setProperty("parse.model", "edu/stanford/nlp/models/lexparser/germanFactored.ser.gz");
        props.setProperty("depparse.model", "edu/stanford/nlp/models/parser/nndep/UD_German.gz");
        props.setProperty("ner.language", "german");
        props.setProperty("tokenize.language", "german");
        props.setProperty("ner.applyNumericClassifiers", "false");
        props.setProperty("ner.applyFineGrained", "false");
        props.setProperty("ner.useSUTime", "false");
        props.setProperty("coref.algorithm", "neural");
        return props;
    }

    private String getModels(String modelName) {
        return NERGermanAtom.class.getResource("/models/" + modelName).getFile();
    }
    public static NERGermanAtom create() {
        return new NERGermanAtom();
    }

    public static NERGermanAtom create(Properties props) {
        return new NERGermanAtom(props);
    }
}