package com.github.vedenin.atoms.nlp;

import java.util.List;

public interface NERAtomInterface {
    List<TokenDataAtom> getItems(final String text);
}
