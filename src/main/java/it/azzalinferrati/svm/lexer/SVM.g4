grammar SVM;

@lexer::header {
import java.util.List;
import java.util.ArrayList;
}
@lexer::members {
private List<String> errors = new ArrayList<>();

public int errorCount() {
	return errors.size();
}
}

assembly: instruction*;

instruction:
	'push' REGISTER													# push
	| 'pop'															# pop
	| 'lw' output = REGISTER NUMBER '(' input = REGISTER ')'		# loadWord
	| 'sw' output = REGISTER NUMBER '(' input = REGISTER ')'		# storeWord
	| 'li' REGISTER NUMBER											# loadInteger
	| 'add' output = REGISTER input1 = REGISTER input2 = REGISTER	# add
	| 'sub' output = REGISTER input1 = REGISTER input2 = REGISTER	# sub
	| 'mult' output = REGISTER input1 = REGISTER input2 = REGISTER	# mult
	| 'div' output = REGISTER input1 = REGISTER input2 = REGISTER	# div
	| 'addi' output = REGISTER input = REGISTER NUMBER				# addInt
	| 'subi' output = REGISTER input = REGISTER NUMBER				# subInt
	| 'multi' output = REGISTER input = REGISTER NUMBER				# multInt
	| 'divi' output = REGISTER input = REGISTER NUMBER				# divInt
	| 'and' output = REGISTER input1 = REGISTER input2 = REGISTER	# and
	| 'or' output = REGISTER input1 = REGISTER input2 = REGISTER	# or
	| 'not' output = REGISTER input = REGISTER						# not
	| 'andb' output = REGISTER input = REGISTER BOOL				# andBool
	| 'orb' output = REGISTER input = REGISTER BOOL					# orBool
	| 'notb' output = REGISTER BOOL									# notBool
	| 'mv' output = REGISTER input = REGISTER						# move
	| 'beq' input1 = REGISTER input2 = REGISTER LABEL				# branchIfEqual
	| 'bleq' input1 = REGISTER input2 = REGISTER LABEL				# branchIfLessEqual
	| 'b' LABEL														# branch
	| LABEL COL														# label
	| 'jal' LABEL													# jumpToFunction
	| 'jr' REGISTER													# jumpToRegister
	| 'del' NUMBER '(' REGISTER ')'									# delete
	| 'print' REGISTER												# print
	| 'halt'														# halt;

// Available registers
REGISTER:
	'$a0'
	| // Accumulator is used to store the computed value of expressions
	'$t1'
	| // General purpose register is used to store temporary values
	'$sp'
	| // Stack Pointer points to the top of the stack
	'$fp'
	| // Frame Pointer points to the current Access Link relative to the active frame
	'$al'
	| // Access Link is used to go through the static chain (i.e. scopes)
	'$ra'
	| // Return address stores the return address
	'$hp'; // Heap pointer points to the top of the heap

// LABELS
COL: ':';
LABEL: ('a' ..'z' | 'A' ..'Z') (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
	)*;

// NUMBERS AND BOOLEANS
NUMBER: '0' | ('-')? (('1' ..'9') ('0' ..'9')*);
BOOL: '0' | '1';

// ESCAPE SEQUENCES
WS: ( '\t' | ' ' | '\r' | '\n')+ -> channel(HIDDEN);

ERR: . { errors.add("Invalid character: "+ getText()); } -> channel(HIDDEN);

