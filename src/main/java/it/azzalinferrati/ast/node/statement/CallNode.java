package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CallNode implements Node {
    final private IdNode id;
    final private List<ExpNode> params;

    public CallNode(IdNode id, List<ExpNode> params) {
        this.id = id;
        this.params = params;
    }
    
    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        String parametersToPrint = params.stream().map((argNode) -> argNode.toPrint(indent)).reduce("(", (arg1, arg2) -> (arg1.equals("(") ? "(" : (arg1 + ",")) + arg2);
        parametersToPrint += ")";
        return indent + "call " + id.toPrint(indent) + parametersToPrint;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        TypeNode idType = id.typeCheck();

        if(!(idType instanceof FunTypeNode)) {
            throw new TypeCheckingException("ID " + id.toPrint("") + " is not a function identifier");
        }

        FunTypeNode funType = (FunTypeNode) idType;

        List<TypeNode> formalFunArgTypes = funType.getParams();
        List<TypeNode> actualFunArgTypes = new ArrayList<>();
        
        for (ExpNode exp : params) {
            actualFunArgTypes.add(exp.typeCheck());
        }

        if(formalFunArgTypes.size() != actualFunArgTypes.size()) {
            throw new TypeCheckingException("The number of actual parameters do not match that of the formal parameters of function " + id.toPrint(""));
        }

        for(int i = 0, size = formalFunArgTypes.size() ; i < size; i++) {
            if(!Node.isSubtype(formalFunArgTypes.get(i), actualFunArgTypes.get(i))) {
                throw new TypeCheckingException("In function " + id.toPrint("") + " expected argument of type " + formalFunArgTypes.get(i).toPrint("") + ", got " + actualFunArgTypes.get(i).toPrint(""));
            }
        }

        return funType.getReturned();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(id.checkSemantics(env));
        params.stream().forEach((p) -> errors.addAll(p.checkSemantics(env)));
        
        return errors;
    }

    public List<IdNode> variables() {
        return params.stream().flatMap(exp -> exp.variables().stream()).collect(Collectors.toList());
    }
}
