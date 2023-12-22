grammar PL0;

program: programHeader subProgram;

programHeader: 'PROGRAM' identifier;

subProgram: (constDeclaration)? (varDeclaration)? statement;

constDeclaration: 'CONST' constDefinition (',' constDefinition)* ';';

constDefinition: identifier ':=' unsignedInteger;

varDeclaration: 'VAR' identifier (',' identifier)* ';';

unsignedInteger: DIGIT+;

identifier: LETTER (LETTER | DIGIT)*;

compoundStatement: 'BEGIN' statement (';' statement)* 'END';

statement: assignmentStatement
         | conditionStatement
         | loopStatement
         | compoundStatement
         | emptyStatement;

assignmentStatement: identifier ':=' expression;

expression: additionOperator? term
            | expression additionOperator term;

term:   factor
        | term multiplicationOperator factor;

factor: identifier
      | unsignedInteger
      | '(' expression ')';

conditionStatement: 'IF' condition 'THEN' statement;

loopStatement: 'WHILE' condition 'DO' statement;

condition: expression relationalOperator expression;

relationalOperator: '=' | '<>' | '<' | '<=' | '>' | '>=';

additionOperator: '+' | '-';

multiplicationOperator: '*' | '/';

emptyStatement: ;



LETTER: [a-zA-Z];

DIGIT: [0-9];

WS: [ \t\r\n]+ -> skip;
