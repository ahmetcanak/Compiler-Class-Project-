package edu.aydin.sda.frontent;

import java.util.HashMap;
import java.util.Map;

public class Parser {
	private Scanner scanner;
	private Token curToken;
	Dal myDal;
	Dal mainDal;
	private int curId = 0;
	private int mainId = 0;
	Integer inBody = -1;
	Integer inIf = -1;
	Integer inWhile = -1;
	Integer oldBody = -1;
	Integer oldIfBody = -1;
	Integer oldWhileBody = -1;
	boolean then = false;
	boolean begin = false;
	HashMap<Integer, Dal> list = new HashMap<Integer, Dal>();
	HashMap<Integer, Dal> mainList = new HashMap<Integer, Dal>();
	HashMap<String, String> variables = new HashMap<String, String>();
	Dal ifDal = null;
	Dal whileDal = null;

	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}

	void printToken() {
		System.out.printf("Type: %s, text: %s\n", curToken.getTokenType(), curToken.getText());
		if (!curToken.getTokenType().equals(TokenType.END_OF_FILE) && !curToken.getTokenType().equals(TokenType.ENDIF)
				&& !curToken.getTokenType().equals(TokenType.ENDWHILE)) {
			if (inBody == -1) {
				list = new HashMap<Integer, Dal>();
				Dal dal = new Dal();
				dal.setValue("Body");
				if (oldBody != -1) {
					dal.setUst(oldBody);
					mainList.get(oldBody).setSag(curId);
				}
				inBody = curId;
				list.put(curId++, dal);
			}
		}

		if (curToken.getTokenType().equals(TokenType.WHILE)) {
			whileDal = new Dal("while");
			whileDal.setUst(inBody);
			list.get(inBody).setSol(curId);
			inWhile = curId;
			list.put(curId++, whileDal);
			oldWhileBody = inBody;
			oldBody = -1;
		} else if (curToken.getTokenType().equals(TokenType.BEGIN)) {
			begin = true;
			mainId = curId - 1;
			while (list.get(mainId).getUst() != null) {
				mainId = list.get(mainId).getUst();
			}
			list.get(mainId).setUst(inWhile);
			list.get(inWhile).setSol(mainId);
			for (Map.Entry<Integer, Dal> me : list.entrySet()) {
				mainList.put(me.getKey(), me.getValue());
			}
			inBody = -1;
		} else if (curToken.getTokenType().equals(TokenType.ENDWHILE)) {
			begin = false;
			mainId = curId - 1;
			while (mainList.get(mainId).getUst() != null) {
				mainId = mainList.get(mainId).getUst();
			}
			mainList.get(mainId).setUst(inWhile);
			mainList.get(inWhile).setSag(mainId);
			inWhile = -1;
			inBody = -1;
			oldBody = oldWhileBody;
		} else if (curToken.getTokenType().equals(TokenType.IF)) {
			ifDal = new Dal("if");
			ifDal.setUst(inBody);
			list.get(inBody).setSol(curId);
			inIf = curId;
			list.put(curId++, ifDal);
			oldIfBody = inBody;
			oldBody = -1;
		} else if (curToken.getTokenType().equals(TokenType.THEN)) {
			then = true;
			mainId = curId - 1;
			while (list.get(mainId).getUst() != null) {
				mainId = list.get(mainId).getUst();
			}
			list.get(mainId).setUst(inIf);
			list.get(inIf).setSol(mainId);
			for (Map.Entry<Integer, Dal> me : list.entrySet()) {
				mainList.put(me.getKey(), me.getValue());
			}
			inBody = -1;
		} else if (curToken.getTokenType().equals(TokenType.ENDIF)) {
			then = false;
			mainId = curId - 1;
			while (mainList.get(mainId).getUst() != null) {
				mainId = mainList.get(mainId).getUst();
			}
			mainList.get(mainId).setUst(inIf);
			mainList.get(inIf).setSag(mainId);
			inIf = -1;
			inBody = -1;
			oldBody = oldIfBody;
		} else if (curToken.getTokenType().equals(TokenType.IDENTIFIER)
				|| curToken.getTokenType().equals(TokenType.INTEGER)) {

			Dal yeniDal = new Dal();
			yeniDal.setValue(curToken.getText());
			if (list.get(curId - 1).getValue() != "Body" && list.get(curId - 1).getValue() != "if" && list.get(curId - 1).getValue() != "while") {
				yeniDal.setUst(curId - 1);
				list.get(curId - 1).setSag(curId);
			}

			list.put(curId++, yeniDal);
		} else if (curToken.getTokenType().equals(TokenType.MINUS) || curToken.getTokenType().equals(TokenType.PLUS)
				|| curToken.getTokenType().equals(TokenType.LESS_THAN)
				|| curToken.getTokenType().equals(TokenType.EQUAL)
				|| curToken.getTokenType().equals(TokenType.MORE_THAN)
				|| curToken.getTokenType().equals(TokenType.MULTIPLE)
				|| curToken.getTokenType().equals(TokenType.DIVIDE)) {
			Dal yeniDal = new Dal();
			yeniDal.setValue(curToken.getText());
			yeniDal.setSol(curId - 1);
			Integer temp = list.get(curId - 1).getUst();
			if (temp != null) {
				list.get(temp).setSag(curId);
				yeniDal.setUst(temp);
			}
			list.get(curId - 1).setUst(curId);
			list.put(curId++, yeniDal);

		} else if (curToken.getTokenType().equals(TokenType.SEMI_COLON)) {
			mainId = curId - 1;
			while (list.get(mainId).getUst() != null) {
				mainId = list.get(mainId).getUst();
			}
			list.get(mainId).setUst(inBody);
			list.get(inBody).setSol(mainId);
			oldBody = inBody;
			inBody = -1;
			for (Map.Entry<Integer, Dal> me : list.entrySet()) {
				mainList.put(me.getKey(), me.getValue());
			}

		}

	}
	
	void evaluator() {
		for (Map.Entry<Integer, Dal> me : mainList.entrySet()) {
			if(me.getValue().getValue().equals("=")) {
				if(mainList.get(me.getValue().getSag()).getValue().equals("+") 
						||mainList.get(me.getValue().getSag()).getValue().equals("-")
						||mainList.get(me.getValue().getSag()).getValue().equals("*")
						||mainList.get(me.getValue().getSag()).getValue().equals("/")
						) {
					variables.put(mainList.get(me.getValue().getSol()).getValue(), 
							mainList.get(mainList.get(me.getValue().getSag()).getSag()).getValue() 
							+" "
							+mainList.get(me.getValue().getSag()).getValue()
							+" "
							+mainList.get(mainList.get(me.getValue().getSag()).getSol()).getValue());
				}
				else
				variables.put(mainList.get(me.getValue().getSol()).getValue(), mainList.get(me.getValue().getSag()).getValue());
			}
		}
	}

	void parse() {
		while (!(curToken instanceof EOFToken)) {
			curToken = scanner.nextToken();
			printToken();
			Stmts();
		}
		System.out.println("\nProgam's traversal diagram");
		Dal.printTree(0, mainList);
		evaluator();
		System.out.println("\nOutput:");
		for (Map.Entry<String, String> me : variables.entrySet()) {
			System.out.println(me);
		}

	}

	void Stmts() {
		Stmt();
		// Stmt_tail();
		if (curToken.getTokenType().equals(TokenType.END_OF_FILE)) {
			// ...
		}
	}

	void Stmt() {
		if (curToken.getTokenType().equals(TokenType.IF)) {
			curToken = scanner.nextToken();
			printToken();
			if (curToken.getTokenType().equals(TokenType.LEFT_PAR)) {
				curToken = scanner.nextToken();
				printToken();
				if (curToken.getTokenType().equals(TokenType.RIGHT_PAR)) {
					curToken = scanner.nextToken();
					printToken();
					if (curToken.getTokenType().equals(TokenType.THEN)) {

						Stmts();
						curToken = scanner.nextToken();
						printToken();
						if (curToken.getTokenType().equals(TokenType.ENDIF)) {
							// ...
						} else if (curToken.getTokenType().equals(TokenType.ELSE)) {
							Stmts();
							curToken = scanner.nextToken();
							printToken();
							if (curToken.getTokenType().equals(TokenType.ENDIF)) {
								// ...
							}

						}
					}
				}
			}
		} else if (curToken.getTokenType().equals(TokenType.WHILE)) {
			curToken = scanner.nextToken();
			printToken();
			if (curToken.getTokenType().equals(TokenType.LEFT_PAR)) {
				Boolean();
				curToken = scanner.nextToken();
				printToken();
				if (curToken.getTokenType().equals(TokenType.RIGHT_PAR)) {
					curToken = scanner.nextToken();
					printToken();
					if (curToken.getTokenType().equals(TokenType.BEGIN)) {
						Stmts();
						curToken = scanner.nextToken();
						printToken();
						if (curToken.getTokenType().equals(TokenType.ENDWHILE)) {
							// ...
						}
					}
				}
			}
		} else if (curToken.getTokenType().equals(TokenType.IDENTIFIER)) {
			curToken = scanner.nextToken();
			printToken();
			if (curToken.getTokenType().equals(TokenType.EQUAL)) {

				Expr();
				curToken = scanner.nextToken();
				printToken();
				if (curToken.getTokenType().equals(TokenType.SEMI_COLON)) {
					// ...
				}
			}
		} else if (curToken.getTokenType().equals(TokenType.IN)) {
			curToken = scanner.nextToken();
			printToken();
			Expr();
			if (curToken.getTokenType().equals(TokenType.SEMI_COLON)) {
				// ...
			}
		} else if (curToken.getTokenType().equals(TokenType.OUT)) {
			curToken = scanner.nextToken();
			printToken();
			Expr();
			if (curToken.getTokenType().equals(TokenType.SEMI_COLON)) {
				// ...
			}
		}
	}

	void Expr() {
		Term();
		Expr_tail();
	}

	void Expr_tail() { // artý - eksi
		if (curToken.getTokenType().equals(TokenType.PLUS)) {

			Term();
			Expr_tail();
		} else if (curToken.getTokenType().equals(TokenType.MINUS)) {

			Term();
			Expr_tail();
		} else if (curToken.getTokenType().equals(TokenType.END_OF_FILE)) {
			curToken = scanner.nextToken();
			printToken();
			return;
		}
	}

	void Term() { // rakam veya identifiar - çarpým - bölüm
		Factor();
		Term_tail();
	}

	void Term_tail() {
		if (curToken.getTokenType().equals(TokenType.MULTIPLE)) {

			Factor();
			Term_tail();
		} else if (curToken.getTokenType().equals(TokenType.DIVIDE)) {

			Factor();
			Term_tail();
		} else if (curToken.getTokenType().equals(TokenType.END_OF_FILE)) {
			curToken = scanner.nextToken();
			printToken();
			return;
		}
	}

	void Factor() {
		curToken = scanner.nextToken();
		if (curToken.getTokenType().equals(TokenType.INTEGER)) {
			printToken();
		} else if (curToken.getTokenType().equals(TokenType.LEFT_PAR)) {
			Expr();
			curToken = scanner.nextToken();
			if (curToken.getTokenType().equals(TokenType.RIGHT_PAR)) {
				// ...
			}
		} else if (curToken.getTokenType().equals(TokenType.IDENTIFIER)) {
			printToken();
		}

	}

	void Boolean() {
		Expr();
		curToken = scanner.nextToken();
		if (curToken.getTokenType().equals(TokenType.SPECIAL_SYMBOLS.get(curToken.text))) {

			printToken();
		}
		Expr();
	}

}
