// Generated from .\src\main\java\it\azzalinferrati\svm\lexer\SVM.g4 by ANTLR 4.9.1
package it.azzalinferrati.svm.ast;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import it.azzalinferrati.svm.parser.SVMParser;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SVMParser}.
 */
public interface SVMListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SVMParser#assembly}.
	 * @param ctx the parse tree
	 */
	void enterAssembly(SVMParser.AssemblyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SVMParser#assembly}.
	 * @param ctx the parse tree
	 */
	void exitAssembly(SVMParser.AssemblyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code push}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterPush(SVMParser.PushContext ctx);
	/**
	 * Exit a parse tree produced by the {@code push}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitPush(SVMParser.PushContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pop}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterPop(SVMParser.PopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pop}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitPop(SVMParser.PopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loadWord}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterLoadWord(SVMParser.LoadWordContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loadWord}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitLoadWord(SVMParser.LoadWordContext ctx);
	/**
	 * Enter a parse tree produced by the {@code storeWord}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterStoreWord(SVMParser.StoreWordContext ctx);
	/**
	 * Exit a parse tree produced by the {@code storeWord}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitStoreWord(SVMParser.StoreWordContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loadInteger}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterLoadInteger(SVMParser.LoadIntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loadInteger}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitLoadInteger(SVMParser.LoadIntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code add}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterAdd(SVMParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by the {@code add}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitAdd(SVMParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sub}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterSub(SVMParser.SubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sub}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitSub(SVMParser.SubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mult}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterMult(SVMParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitMult(SVMParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code div}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterDiv(SVMParser.DivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code div}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitDiv(SVMParser.DivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addInt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterAddInt(SVMParser.AddIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addInt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitAddInt(SVMParser.AddIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subInt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterSubInt(SVMParser.SubIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subInt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitSubInt(SVMParser.SubIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multInt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterMultInt(SVMParser.MultIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multInt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitMultInt(SVMParser.MultIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code divInt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterDivInt(SVMParser.DivIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code divInt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitDivInt(SVMParser.DivIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code and}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterAnd(SVMParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code and}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitAnd(SVMParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code or}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterOr(SVMParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code or}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitOr(SVMParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code not}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterNot(SVMParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code not}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitNot(SVMParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andBool}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterAndBool(SVMParser.AndBoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andBool}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitAndBool(SVMParser.AndBoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orBool}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterOrBool(SVMParser.OrBoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orBool}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitOrBool(SVMParser.OrBoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notBool}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterNotBool(SVMParser.NotBoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notBool}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitNotBool(SVMParser.NotBoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code move}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterMove(SVMParser.MoveContext ctx);
	/**
	 * Exit a parse tree produced by the {@code move}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitMove(SVMParser.MoveContext ctx);
	/**
	 * Enter a parse tree produced by the {@code branchIfEqual}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterBranchIfEqual(SVMParser.BranchIfEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code branchIfEqual}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitBranchIfEqual(SVMParser.BranchIfEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code branchIfLessEqual}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterBranchIfLessEqual(SVMParser.BranchIfLessEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code branchIfLessEqual}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitBranchIfLessEqual(SVMParser.BranchIfLessEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code branch}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterBranch(SVMParser.BranchContext ctx);
	/**
	 * Exit a parse tree produced by the {@code branch}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitBranch(SVMParser.BranchContext ctx);
	/**
	 * Enter a parse tree produced by the {@code label}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterLabel(SVMParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code label}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitLabel(SVMParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpToFunction}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterJumpToFunction(SVMParser.JumpToFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpToFunction}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitJumpToFunction(SVMParser.JumpToFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpToRegister}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterJumpToRegister(SVMParser.JumpToRegisterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpToRegister}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitJumpToRegister(SVMParser.JumpToRegisterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code delete}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterDelete(SVMParser.DeleteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code delete}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitDelete(SVMParser.DeleteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code print}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterPrint(SVMParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code print}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitPrint(SVMParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code halt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterHalt(SVMParser.HaltContext ctx);
	/**
	 * Exit a parse tree produced by the {@code halt}
	 * labeled alternative in {@link SVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitHalt(SVMParser.HaltContext ctx);
}