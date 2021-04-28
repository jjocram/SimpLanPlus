// Generated from .\src\main\java\it\azzalinferrati\lexer\SimpLanPlus.g4 by ANTLR 4.9.1
package it.azzalinferrati.ast;
import it.azzalinferrati.parser.SimpLanPlusParser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpLanPlusParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpLanPlusVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SimpLanPlusParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assigtStat}
	 * labeled alternative in {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssigtStat(SimpLanPlusParser.AssigtStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code deletStat}
	 * labeled alternative in {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeletStat(SimpLanPlusParser.DeletStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStat(SimpLanPlusParser.PrintStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code retStat}
	 * labeled alternative in {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetStat(SimpLanPlusParser.RetStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iteStat}
	 * labeled alternative in {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteStat(SimpLanPlusParser.IteStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callStat}
	 * labeled alternative in {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStat(SimpLanPlusParser.CallStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link SimpLanPlusParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStat(SimpLanPlusParser.BlockStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declarateFun}
	 * labeled alternative in {@link SimpLanPlusParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarateFun(SimpLanPlusParser.DeclarateFunContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declarateVar}
	 * labeled alternative in {@link SimpLanPlusParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarateVar(SimpLanPlusParser.DeclarateVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#decFun}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecFun(SimpLanPlusParser.DecFunContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#decVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecVar(SimpLanPlusParser.DecVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SimpLanPlusParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#funType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunType(SimpLanPlusParser.FunTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(SimpLanPlusParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(SimpLanPlusParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#lhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLhs(SimpLanPlusParser.LhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#deletion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeletion(SimpLanPlusParser.DeletionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(SimpLanPlusParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#ret}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRet(SimpLanPlusParser.RetContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#ite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIte(SimpLanPlusParser.IteContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpLanPlusParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(SimpLanPlusParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code baseExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseExp(SimpLanPlusParser.BaseExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinExp(SimpLanPlusParser.BinExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code derExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDerExp(SimpLanPlusParser.DerExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExp(SimpLanPlusParser.NewExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValExp(SimpLanPlusParser.ValExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegExp(SimpLanPlusParser.NegExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExp(SimpLanPlusParser.BoolExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExp(SimpLanPlusParser.CallExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExp}
	 * labeled alternative in {@link SimpLanPlusParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExp(SimpLanPlusParser.NotExpContext ctx);
}
