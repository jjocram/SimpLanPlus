package it.azzalinferrati.semanticanalysis;

import it.azzalinferrati.ast.STEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment {
    //FIXME
    //THESE VARIABLES SHOULDN'T BE PUBLIC
    //THIS CAN BE DONE MUCH BETTER

    public ArrayList<HashMap<String, STEntry>> symTable = new ArrayList<HashMap<String,STEntry>>();
    public int nestingLevel = -1;
    public int offset = 0;
    //livello ambiente con dichiarazioni piu' esterno � 0 (prima posizione ArrayList) invece che 1 (slides)
    //il "fronte" della lista di tabelle � symTable.get(nestingLevel)
}
