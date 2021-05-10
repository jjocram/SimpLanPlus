package it.azzalinferrati.semanticanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
    //FIXME
    //THESE VARIABLES SHOULDN'T BE PUBLIC
    //THIS CAN BE DONE MUCH BETTER

    final private List<Map<String, STEntry>> symTable;
    final private int nestingLevel;
    final private int offset;

    public Environment(List<Map<String, STEntry>> symTable, int nestingLevel, int offset) {
        this.symTable = symTable;
        this.nestingLevel = nestingLevel;
        this.offset = offset;
    }

    //livello ambiente con dichiarazioni piu' esterno � 0 (prima posizione ArrayList) invece che 1 (slides)
    //il "fronte" della lista di tabelle � symTable.get(nestingLevel)
}
