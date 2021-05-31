// Generated from .\src\main\java\it\azzalinferrati\svm\lexer\SVM.g4 by ANTLR 4.9.1
package it.azzalinferrati.svm.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import it.azzalinferrati.svm.ast.SVMListener;
import it.azzalinferrati.svm.ast.SVMVisitor;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, REGISTER=31, 
		COL=32, LABEL=33, NUMBER=34, BOOL=35, WS=36, ERR=37;
	public static final int
		RULE_assembly = 0, RULE_instruction = 1;
	private static String[] makeRuleNames() {
		return new String[] {
			"assembly", "instruction"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'push'", "'pop'", "'lw'", "'('", "')'", "'sw'", "'li'", "'add'", 
			"'sub'", "'mult'", "'div'", "'addi'", "'subi'", "'multi'", "'divi'", 
			"'and'", "'or'", "'not'", "'andb'", "'orb'", "'notb'", "'mv'", "'beq'", 
			"'bleq'", "'b'", "'jal'", "'jr'", "'del'", "'print'", "'halt'", null, 
			"':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "REGISTER", "COL", "LABEL", 
			"NUMBER", "BOOL", "WS", "ERR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SVMParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class AssemblyContext extends ParserRuleContext {
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public AssemblyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assembly; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterAssembly(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitAssembly(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAssembly(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblyContext assembly() throws RecognitionException {
		AssemblyContext _localctx = new AssemblyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_assembly);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(7);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << LABEL))) != 0)) {
				{
				{
				setState(4);
				instruction();
				}
				}
				setState(9);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
	 
		public InstructionContext() { }
		public void copyFrom(InstructionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SubContext extends InstructionContext {
		public Token output;
		public Token input1;
		public Token input2;
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public SubContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitSub(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultContext extends InstructionContext {
		public Token output;
		public Token input1;
		public Token input2;
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public MultContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitMult(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitMult(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotBoolContext extends InstructionContext {
		public Token output;
		public TerminalNode BOOL() { return getToken(SVMParser.BOOL, 0); }
		public TerminalNode REGISTER() { return getToken(SVMParser.REGISTER, 0); }
		public NotBoolContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterNotBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitNotBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitNotBool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultIntContext extends InstructionContext {
		public Token output;
		public Token input;
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public MultIntContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterMultInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitMultInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitMultInt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchContext extends InstructionContext {
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public BranchContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterBranch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitBranch(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranch(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeleteContext extends InstructionContext {
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public TerminalNode REGISTER() { return getToken(SVMParser.REGISTER, 0); }
		public DeleteContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterDelete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitDelete(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitDelete(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PopContext extends InstructionContext {
		public PopContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterPop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitPop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitPop(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivContext extends InstructionContext {
		public Token output;
		public Token input1;
		public Token input2;
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public DivContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterDiv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitDiv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StoreWordContext extends InstructionContext {
		public Token output;
		public Token input;
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public StoreWordContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterStoreWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitStoreWord(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitStoreWord(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotContext extends InstructionContext {
		public Token output;
		public Token input;
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public NotContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadWordContext extends InstructionContext {
		public Token output;
		public Token input;
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public LoadWordContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterLoadWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitLoadWord(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLoadWord(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubIntContext extends InstructionContext {
		public Token output;
		public Token input;
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public SubIntContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterSubInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitSubInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitSubInt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndContext extends InstructionContext {
		public Token output;
		public Token input1;
		public Token input2;
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public AndContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JumpToFunctionContext extends InstructionContext {
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public JumpToFunctionContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterJumpToFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitJumpToFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitJumpToFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadIntegerContext extends InstructionContext {
		public TerminalNode REGISTER() { return getToken(SVMParser.REGISTER, 0); }
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public LoadIntegerContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterLoadInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitLoadInteger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLoadInteger(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddContext extends InstructionContext {
		public Token output;
		public Token input1;
		public Token input2;
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public AddContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterAdd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitAdd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MoveContext extends InstructionContext {
		public Token output;
		public Token input;
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public MoveContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterMove(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitMove(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitMove(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrContext extends InstructionContext {
		public Token output;
		public Token input1;
		public Token input2;
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public OrContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JumpToRegisterContext extends InstructionContext {
		public TerminalNode REGISTER() { return getToken(SVMParser.REGISTER, 0); }
		public JumpToRegisterContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterJumpToRegister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitJumpToRegister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitJumpToRegister(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LabelContext extends InstructionContext {
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public TerminalNode COL() { return getToken(SVMParser.COL, 0); }
		public LabelContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PushContext extends InstructionContext {
		public TerminalNode REGISTER() { return getToken(SVMParser.REGISTER, 0); }
		public PushContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterPush(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitPush(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitPush(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class HaltContext extends InstructionContext {
		public HaltContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterHalt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitHalt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitHalt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintContext extends InstructionContext {
		public TerminalNode REGISTER() { return getToken(SVMParser.REGISTER, 0); }
		public PrintContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitPrint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndBoolContext extends InstructionContext {
		public Token output;
		public Token input;
		public TerminalNode BOOL() { return getToken(SVMParser.BOOL, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public AndBoolContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterAndBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitAndBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAndBool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchIfLessEqualContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public BranchIfLessEqualContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterBranchIfLessEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitBranchIfLessEqual(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchIfLessEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchIfEqualContext extends InstructionContext {
		public Token input1;
		public Token input2;
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public BranchIfEqualContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterBranchIfEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitBranchIfEqual(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchIfEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddIntContext extends InstructionContext {
		public Token output;
		public Token input;
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public AddIntContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterAddInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitAddInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAddInt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivIntContext extends InstructionContext {
		public Token output;
		public Token input;
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public DivIntContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterDivInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitDivInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitDivInt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrBoolContext extends InstructionContext {
		public Token output;
		public Token input;
		public TerminalNode BOOL() { return getToken(SVMParser.BOOL, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(SVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(SVMParser.REGISTER, i);
		}
		public OrBoolContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).enterOrBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SVMListener ) ((SVMListener)listener).exitOrBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitOrBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction);
		try {
			setState(109);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				_localctx = new PushContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(10);
				match(T__0);
				setState(11);
				match(REGISTER);
				}
				break;
			case T__1:
				_localctx = new PopContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(12);
				match(T__1);
				}
				break;
			case T__2:
				_localctx = new LoadWordContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(13);
				match(T__2);
				setState(14);
				((LoadWordContext)_localctx).output = match(REGISTER);
				setState(15);
				match(NUMBER);
				setState(16);
				match(T__3);
				setState(17);
				((LoadWordContext)_localctx).input = match(REGISTER);
				setState(18);
				match(T__4);
				}
				break;
			case T__5:
				_localctx = new StoreWordContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(19);
				match(T__5);
				setState(20);
				((StoreWordContext)_localctx).output = match(REGISTER);
				setState(21);
				match(NUMBER);
				setState(22);
				match(T__3);
				setState(23);
				((StoreWordContext)_localctx).input = match(REGISTER);
				setState(24);
				match(T__4);
				}
				break;
			case T__6:
				_localctx = new LoadIntegerContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(25);
				match(T__6);
				setState(26);
				match(REGISTER);
				setState(27);
				match(NUMBER);
				}
				break;
			case T__7:
				_localctx = new AddContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(28);
				match(T__7);
				setState(29);
				((AddContext)_localctx).output = match(REGISTER);
				setState(30);
				((AddContext)_localctx).input1 = match(REGISTER);
				setState(31);
				((AddContext)_localctx).input2 = match(REGISTER);
				}
				break;
			case T__8:
				_localctx = new SubContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(32);
				match(T__8);
				setState(33);
				((SubContext)_localctx).output = match(REGISTER);
				setState(34);
				((SubContext)_localctx).input1 = match(REGISTER);
				setState(35);
				((SubContext)_localctx).input2 = match(REGISTER);
				}
				break;
			case T__9:
				_localctx = new MultContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(36);
				match(T__9);
				setState(37);
				((MultContext)_localctx).output = match(REGISTER);
				setState(38);
				((MultContext)_localctx).input1 = match(REGISTER);
				setState(39);
				((MultContext)_localctx).input2 = match(REGISTER);
				}
				break;
			case T__10:
				_localctx = new DivContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(40);
				match(T__10);
				setState(41);
				((DivContext)_localctx).output = match(REGISTER);
				setState(42);
				((DivContext)_localctx).input1 = match(REGISTER);
				setState(43);
				((DivContext)_localctx).input2 = match(REGISTER);
				}
				break;
			case T__11:
				_localctx = new AddIntContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(44);
				match(T__11);
				setState(45);
				((AddIntContext)_localctx).output = match(REGISTER);
				setState(46);
				((AddIntContext)_localctx).input = match(REGISTER);
				setState(47);
				match(NUMBER);
				}
				break;
			case T__12:
				_localctx = new SubIntContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(48);
				match(T__12);
				setState(49);
				((SubIntContext)_localctx).output = match(REGISTER);
				setState(50);
				((SubIntContext)_localctx).input = match(REGISTER);
				setState(51);
				match(NUMBER);
				}
				break;
			case T__13:
				_localctx = new MultIntContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(52);
				match(T__13);
				setState(53);
				((MultIntContext)_localctx).output = match(REGISTER);
				setState(54);
				((MultIntContext)_localctx).input = match(REGISTER);
				setState(55);
				match(NUMBER);
				}
				break;
			case T__14:
				_localctx = new DivIntContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(56);
				match(T__14);
				setState(57);
				((DivIntContext)_localctx).output = match(REGISTER);
				setState(58);
				((DivIntContext)_localctx).input = match(REGISTER);
				setState(59);
				match(NUMBER);
				}
				break;
			case T__15:
				_localctx = new AndContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(60);
				match(T__15);
				setState(61);
				((AndContext)_localctx).output = match(REGISTER);
				setState(62);
				((AndContext)_localctx).input1 = match(REGISTER);
				setState(63);
				((AndContext)_localctx).input2 = match(REGISTER);
				}
				break;
			case T__16:
				_localctx = new OrContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(64);
				match(T__16);
				setState(65);
				((OrContext)_localctx).output = match(REGISTER);
				setState(66);
				((OrContext)_localctx).input1 = match(REGISTER);
				setState(67);
				((OrContext)_localctx).input2 = match(REGISTER);
				}
				break;
			case T__17:
				_localctx = new NotContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(68);
				match(T__17);
				setState(69);
				((NotContext)_localctx).output = match(REGISTER);
				setState(70);
				((NotContext)_localctx).input = match(REGISTER);
				}
				break;
			case T__18:
				_localctx = new AndBoolContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(71);
				match(T__18);
				setState(72);
				((AndBoolContext)_localctx).output = match(REGISTER);
				setState(73);
				((AndBoolContext)_localctx).input = match(REGISTER);
				setState(74);
				match(BOOL);
				}
				break;
			case T__19:
				_localctx = new OrBoolContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(75);
				match(T__19);
				setState(76);
				((OrBoolContext)_localctx).output = match(REGISTER);
				setState(77);
				((OrBoolContext)_localctx).input = match(REGISTER);
				setState(78);
				match(BOOL);
				}
				break;
			case T__20:
				_localctx = new NotBoolContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(79);
				match(T__20);
				setState(80);
				((NotBoolContext)_localctx).output = match(REGISTER);
				setState(81);
				match(BOOL);
				}
				break;
			case T__21:
				_localctx = new MoveContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(82);
				match(T__21);
				setState(83);
				((MoveContext)_localctx).output = match(REGISTER);
				setState(84);
				((MoveContext)_localctx).input = match(REGISTER);
				}
				break;
			case T__22:
				_localctx = new BranchIfEqualContext(_localctx);
				enterOuterAlt(_localctx, 21);
				{
				setState(85);
				match(T__22);
				setState(86);
				((BranchIfEqualContext)_localctx).input1 = match(REGISTER);
				setState(87);
				((BranchIfEqualContext)_localctx).input2 = match(REGISTER);
				setState(88);
				match(LABEL);
				}
				break;
			case T__23:
				_localctx = new BranchIfLessEqualContext(_localctx);
				enterOuterAlt(_localctx, 22);
				{
				setState(89);
				match(T__23);
				setState(90);
				((BranchIfLessEqualContext)_localctx).input1 = match(REGISTER);
				setState(91);
				((BranchIfLessEqualContext)_localctx).input2 = match(REGISTER);
				setState(92);
				match(LABEL);
				}
				break;
			case T__24:
				_localctx = new BranchContext(_localctx);
				enterOuterAlt(_localctx, 23);
				{
				setState(93);
				match(T__24);
				setState(94);
				match(LABEL);
				}
				break;
			case LABEL:
				_localctx = new LabelContext(_localctx);
				enterOuterAlt(_localctx, 24);
				{
				setState(95);
				match(LABEL);
				setState(96);
				match(COL);
				}
				break;
			case T__25:
				_localctx = new JumpToFunctionContext(_localctx);
				enterOuterAlt(_localctx, 25);
				{
				setState(97);
				match(T__25);
				setState(98);
				match(LABEL);
				}
				break;
			case T__26:
				_localctx = new JumpToRegisterContext(_localctx);
				enterOuterAlt(_localctx, 26);
				{
				setState(99);
				match(T__26);
				setState(100);
				match(REGISTER);
				}
				break;
			case T__27:
				_localctx = new DeleteContext(_localctx);
				enterOuterAlt(_localctx, 27);
				{
				setState(101);
				match(T__27);
				setState(102);
				match(NUMBER);
				setState(103);
				match(T__3);
				setState(104);
				match(REGISTER);
				setState(105);
				match(T__4);
				}
				break;
			case T__28:
				_localctx = new PrintContext(_localctx);
				enterOuterAlt(_localctx, 28);
				{
				setState(106);
				match(T__28);
				setState(107);
				match(REGISTER);
				}
				break;
			case T__29:
				_localctx = new HaltContext(_localctx);
				enterOuterAlt(_localctx, 29);
				{
				setState(108);
				match(T__29);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\'r\4\2\t\2\4\3\t"+
		"\3\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\5\3p\n\3\3\3\2\2\4\2\4\2\2\2\u008c\2\t\3\2\2\2\4o\3\2\2\2\6"+
		"\b\5\4\3\2\7\6\3\2\2\2\b\13\3\2\2\2\t\7\3\2\2\2\t\n\3\2\2\2\n\3\3\2\2"+
		"\2\13\t\3\2\2\2\f\r\7\3\2\2\rp\7!\2\2\16p\7\4\2\2\17\20\7\5\2\2\20\21"+
		"\7!\2\2\21\22\7$\2\2\22\23\7\6\2\2\23\24\7!\2\2\24p\7\7\2\2\25\26\7\b"+
		"\2\2\26\27\7!\2\2\27\30\7$\2\2\30\31\7\6\2\2\31\32\7!\2\2\32p\7\7\2\2"+
		"\33\34\7\t\2\2\34\35\7!\2\2\35p\7$\2\2\36\37\7\n\2\2\37 \7!\2\2 !\7!\2"+
		"\2!p\7!\2\2\"#\7\13\2\2#$\7!\2\2$%\7!\2\2%p\7!\2\2&\'\7\f\2\2\'(\7!\2"+
		"\2()\7!\2\2)p\7!\2\2*+\7\r\2\2+,\7!\2\2,-\7!\2\2-p\7!\2\2./\7\16\2\2/"+
		"\60\7!\2\2\60\61\7!\2\2\61p\7$\2\2\62\63\7\17\2\2\63\64\7!\2\2\64\65\7"+
		"!\2\2\65p\7$\2\2\66\67\7\20\2\2\678\7!\2\289\7!\2\29p\7$\2\2:;\7\21\2"+
		"\2;<\7!\2\2<=\7!\2\2=p\7$\2\2>?\7\22\2\2?@\7!\2\2@A\7!\2\2Ap\7!\2\2BC"+
		"\7\23\2\2CD\7!\2\2DE\7!\2\2Ep\7!\2\2FG\7\24\2\2GH\7!\2\2Hp\7!\2\2IJ\7"+
		"\25\2\2JK\7!\2\2KL\7!\2\2Lp\7%\2\2MN\7\26\2\2NO\7!\2\2OP\7!\2\2Pp\7%\2"+
		"\2QR\7\27\2\2RS\7!\2\2Sp\7%\2\2TU\7\30\2\2UV\7!\2\2Vp\7!\2\2WX\7\31\2"+
		"\2XY\7!\2\2YZ\7!\2\2Zp\7#\2\2[\\\7\32\2\2\\]\7!\2\2]^\7!\2\2^p\7#\2\2"+
		"_`\7\33\2\2`p\7#\2\2ab\7#\2\2bp\7\"\2\2cd\7\34\2\2dp\7#\2\2ef\7\35\2\2"+
		"fp\7!\2\2gh\7\36\2\2hi\7$\2\2ij\7\6\2\2jk\7!\2\2kp\7\7\2\2lm\7\37\2\2"+
		"mp\7!\2\2np\7 \2\2o\f\3\2\2\2o\16\3\2\2\2o\17\3\2\2\2o\25\3\2\2\2o\33"+
		"\3\2\2\2o\36\3\2\2\2o\"\3\2\2\2o&\3\2\2\2o*\3\2\2\2o.\3\2\2\2o\62\3\2"+
		"\2\2o\66\3\2\2\2o:\3\2\2\2o>\3\2\2\2oB\3\2\2\2oF\3\2\2\2oI\3\2\2\2oM\3"+
		"\2\2\2oQ\3\2\2\2oT\3\2\2\2oW\3\2\2\2o[\3\2\2\2o_\3\2\2\2oa\3\2\2\2oc\3"+
		"\2\2\2oe\3\2\2\2og\3\2\2\2ol\3\2\2\2on\3\2\2\2p\5\3\2\2\2\4\to";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}