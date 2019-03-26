package com.github.vedenin.atoms.nlp;

import lombok.Data;

@Data(staticConstructor = "of")
public class TokenDataAtom
{
    private final String word;
    private final String type;
}
