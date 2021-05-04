package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.ast.node.ArgNode;
import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.statement.BlockNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

public class DecFunNode implements Node {
    final private TypeNode type;
    final private IdNode id;
    final private List<ArgNode> args;
    final private BlockNode block;

    public DecFunNode(final TypeNode type, final IdNode id, final List<ArgNode> args, final BlockNode block) {
        this.type = type;
        this.id = id;
        this.args = args;
        this.block = block;
    }

    @Override
    public String toPrint(String indent) {
        final String declaration = indent
                + "Function declaration\t>>\n" + id.toPrint(" ") + " : " + args.stream().map((arg) -> arg.toPrint(" "))
                        .reduce("", (arg1, arg2) -> (arg1.isEmpty() ? "(" + arg1 + ")" : arg1) + " x " + "(" + arg2 + ")")
                + " -> " + type.toPrint("");
        final String body = indent + "Function body\t>>\n" + block.toPrint(indent + "  ");

        return declaration + body;
    }

    @Override
    public Node typeCheck() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // TODO Auto-generated method stub
        return null;
    }

}
