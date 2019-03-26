package com.github.vedenin.atoms.nlp;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.AtomTest;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Atom(StanfordCoreNLP.class)
@AtomTest("NERGermanAtomTest")
public class NERGermanAtom
{

    private final StanfordCoreNLP pipeline;

    public NERGermanAtom()
    {
        // set up pipeline properties
        final Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/german/german-fast.tagger");
        props.setProperty("ner.language", "german");
        props.setProperty("tokenize.language", "german");
        props.setProperty("ner.model", "edu/stanford/nlp/models/ner/german.conll.germeval2014.hgc_175m_600.crf.ser.gz");
        props.setProperty("coref.algorithm", "neural");

        pipeline = new StanfordCoreNLP(props);
    }

    public List<TokenDataAtom> getItems(final String text)
    {

        final CoreDocument doc = new CoreDocument(text);
        pipeline.annotate(doc);
        final List<CoreLabel> items = doc.tokens();
        return items
                .stream()
                .map(e -> TokenDataAtom.of(e.word(), e.ner()))
                .collect(
                        Collectors.toList());
    }

    public static NERGermanAtom create()
    {
        return new NERGermanAtom();
    }
}