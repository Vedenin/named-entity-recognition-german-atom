package com.github.vedenin.atoms.nlp;

import com.github.vedenin.atom.annotations.Atom;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

@Atom(StanfordCoreNLP.class)
public class NERGermanAtomTest
{
    private final NERGermanAtom atom = NERGermanAtom.create();

    @Test
    public void test()
    {
        final String text = "Joe Smith wurde in Kalifornien geboren. \n" +
                "2017 ging er im Sommer nach Paris, Frankreich.\n" +
                "Sein Flug ging am 10. Juli 2017 um 15:00 Uhr los.\n" +
                "Nachdem er zum ersten Mal etwas Schnecke gegessen hatte, sagte Joe: Das war lecker! . \n" +
                "Er hat seiner Schwester Jane Smith eine Postkarte geschickt.\n" +
                "Nachdem Jane von Joes Reise gehört hatte, beschloss sie, eines Tages nach Frankreich zu gehen.";
        final List<TokenDataAtom> result = atom.getItems(text);
        assertEquals("Check tokens size", 76, result.size());
        System.out.println(result);
    }
}