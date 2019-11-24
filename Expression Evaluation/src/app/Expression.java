package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";

	/**
	 * Populates the vars list with simple variables, and arrays lists with arrays
	 * in the expression. For every variable (simple or array), a SINGLE instance is
	 * created and stored, even if it appears more than once in the expression. At
	 * this time, values for all variables and all array items are set to zero -
	 * they will be loaded from a file in the loadVariableValues method.
	 * 
	 * @param expr   The expression
	 * @param vars   The variables array list - already created by the caller
	 * @param arrays The arrays array list - already created by the caller
	 */
	public static void makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		/** COMPLETE THIS METHOD **/
		/**
		 * DO NOT create new vars and arrays - they are already created before being
		 * sent in to this method - you just need to fill them in.
		 **/
		StringTokenizer exp = new StringTokenizer(expr, delims, true);
		StringTokenizer expCheck = new StringTokenizer(expr, delims, true);
		String tok = "";
		String tokCheck = expCheck.nextToken();
		while (expCheck.hasMoreTokens()) {
			tok = exp.nextToken();
			tokCheck = expCheck.nextToken();
			if (tok.matches("[a-zA-Z]+")) {
				if (tokCheck.equals("[")) {
					Array arr = new Array(tok);
					int check = arrays.indexOf(arr);
					if(check == -1) {
						arrays.add(arr);
						System.out.println(arr);
					}
				} else {
					Variable var = new Variable(tok);
					int check = vars.indexOf(var);
					if (check == -1) {
						vars.add(var);
						System.out.println(var);
					}
				}
			}
		}
		tok = exp.nextToken();
		if (tok.matches("[a-zA-Z]+")) {
			Variable var = new Variable(tok);
			int check = vars.indexOf(var);
			if(check == -1) {
				vars.add(var);
				System.out.println(var);
			}
		}
		
		
	}

	/**
	 * Loads values for variables and arrays in the expression
	 * 
	 * @param sc Scanner for values input
	 * @throws IOException If there is a problem with the input
	 * @param vars   The variables array list, previously populated by
	 *               makeVariableLists
	 * @param arrays The arrays array list - previously populated by
	 *               makeVariableLists
	 */
	public static void loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays)
			throws IOException {

		while (sc.hasNextLine()) {
			StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
			int numTokens = st.countTokens();
			String tok = st.nextToken();
			Variable var = new Variable(tok);
			Array arr = new Array(tok);
			int vari = vars.indexOf(var);
			int arri = arrays.indexOf(arr);
			if (vari == -1 && arri == -1) {
				continue;
			}
			int num = Integer.parseInt(st.nextToken());
			if (numTokens == 2) { // scalar symbol
				vars.get(vari).value = num;
			} else { // array symbol
				arr = arrays.get(arri);
				arr.values = new int[num];
				// following are (index,val) pairs
				while (st.hasMoreTokens()) {
					tok = st.nextToken();
					StringTokenizer stt = new StringTokenizer(tok, " (,)");
					int index = Integer.parseInt(stt.nextToken());
					int val = Integer.parseInt(stt.nextToken());
					arr.values[index] = val;
				}
			}
		}
	}

	/**
	 * Evaluates the expression.
	 * 
	 * @param vars   The variables array list, with values for all variables in the
	 *               expression
	 * @param arrays The arrays array list, with values for all array items
	 * @return Result of evaluation
	 */
	public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		return recEval(expr, 0, expr.length(), vars, arrays);
	}

	private static float recEval(String expr, int index1, int index2, ArrayList<Variable> vars,
			ArrayList<Array> arrays) {
		/** COMPLETE THIS METHOD **/
		// following line just a placeholder for compilation
		expr = expr.substring(index1, index2);
		int varCount = 0;
		int arrCount = 0;
		int start = 0;
		int end = 0;
		int count = 0;
		float first, second, third, total;
		String operator, operator2;
		Stack<Float> tempNums = new Stack<Float>();
		Stack<String> tempOp = new Stack<String>();
		Stack<Float> nums = new Stack<Float>();
		Stack<String> op = new Stack<String>();
		StringTokenizer exp = new StringTokenizer(expr, delims, true);
		String tok = "";
		/*
		 * while (expCheck.hasMoreTokens()) { tok = exp.nextToken(); tokCheck =
		 * expCheck.nextToken(); System.out.println(tokCheck); expr =
		 * expr.substring(expr.indexOf(tok) + tok.length()); //System.out.println(expr);
		 * if (tok.isBlank()) continue; if (tokCheck.equals("[")) { Array arr = new
		 * Array(tok); arrCount = arrays.indexOf(arr); //System.out.println(arrCount);
		 * int next = (int) evaluate(expr, vars, arrays); nums.push((float)
		 * arrays.get(arrCount).values[next]); System.out.println("Recursive call arr:"
		 * + nums.peek()); while(expCheck.hasMoreTokens()) { tok = exp.nextToken();
		 * tokCheck = expCheck.nextToken(); expr = expr.substring(expr.indexOf(tok) +
		 * tok.length()); System.out.println("new expr: " + expr); if(tok.equals("]")) {
		 * break; } } if(expCheck.hasMoreTokens() == false) { tok = exp.nextToken();
		 * expr = expr.substring(expr.indexOf(tok) + tok.length());4 } } else if
		 * (tok.equals("(")) { nums.push(evaluate(expr, vars, arrays));
		 * System.out.println("Recursive call paren: " + nums.peek());
		 * System.out.println(expr); while(expCheck.hasMoreTokens()) { tok =
		 * exp.nextToken(); tokCheck = expCheck.nextToken(); if(tok.equals(")")) { expr
		 * = expr.substring(expr.indexOf(tok) + tok.length());
		 * System.out.println("new expr: " + expr); break; } }
		 * if(expCheck.hasMoreTokens() == false) { tok = exp.nextToken(); expr =
		 * expr.substring(expr.indexOf(tok) + tok.length()); } continue;
		 * //System.out.println("dasfa"); } else if (tok.equals(")") ||
		 * (tok.equals("]"))) { //System.out.println("break");
		 * System.out.println("break expr: " + expr); break; } else if (tok.equals("+")
		 * || tok.equals("-") || tok.equals("*") || tok.equals("/")) { op.push(tok); }
		 * else if (tok.matches("[a-zA-Z]+")) { Variable var = new Variable(tok);
		 * varCount = vars.indexOf(var); //System.out.println((float)
		 * vars.get(varCount).value); nums.push((float) vars.get(varCount).value);
		 * System.out.println("not last var = " + nums.peek()); } else if
		 * (Character.isDigit(tok.charAt(0))) { nums.push(Float.parseFloat(tok)); } }
		 * 
		 * 
		 * 
		 * if (expCheck.hasMoreTokens() == false) { tok = exp.nextToken();
		 * System.out.println("last"); if (tok.matches("[a-zA-Z]+")) { Variable var =
		 * new Variable(tok); varCount = vars.indexOf(var); nums.push((float)
		 * vars.get(varCount).value); System.out.println("vars = " + nums.peek()); }
		 * else if (Character.isDigit(tok.charAt(0))) {
		 * nums.push(Float.parseFloat(tok)); } else if(tok.equals(")") ||
		 * tok.equals("]")){ expr = ""; } }
		 * 
		 */

		while (exp.hasMoreTokens()) {
			start = 0;
			count = 0;
			end = 0;
			tok = exp.nextToken();
			expr = expr.substring((expr.indexOf(tok) + tok.length()));
			if (tok.equals("+") || tok.equals("-") || tok.equals("*") || tok.equals("/")) {
				op.push(tok);
			} else if (Character.isDigit(tok.charAt(0))) {
				nums.push(Float.parseFloat(tok));
			} else if (tok.matches("[a-zA-Z]+")) {
				Array arr = new Array(tok);
				arrCount = arrays.indexOf(arr);
				if (arrCount == -1) {
					Variable var = new Variable(tok);
					varCount = vars.indexOf(var);
					nums.push((float) vars.get(varCount).value);
				} else {
					tok = exp.nextToken();
					expr = expr.substring((expr.indexOf(tok) + tok.length()));
					for (int i = 0; i < expr.length(); i++) {
						if (expr.charAt(i) == ']' && count == 0) {
							end = i;
							break;
						} else if (expr.charAt(i) == '[') {
							count++;
						} else if (expr.charAt(i) == ']') {
							count--;
						}
					}
					nums.push((float) arrays.get(arrCount).values[(int) recEval(expr, start, end, vars, arrays)]);
					expr = expr.substring(end + 1);
					System.out.println(expr);
					exp = new StringTokenizer(expr, delims, true);
					System.out.println(exp.hasMoreTokens());
					continue;
				}
			} else if (tok.equals("(")) {
				for (int i = 0; i < expr.length(); i++) {
					if (expr.charAt(i) == ')' && count == 0) {
						end = i;
						break;
					} else if (expr.charAt(i) == '(') {
						count++;
					} else if (expr.charAt(i) == ')') {
						count--;
					}
				}
				nums.push(recEval(expr, start, end, vars, arrays));
				expr = expr.substring(end + 1);
				System.out.println(expr);
				exp = new StringTokenizer(expr, delims, true);
				System.out.println(exp.hasMoreTokens());
				continue;
			} 
			/*else if (tok.equals("]") || tok.equals(")")) {
				 break;
			}*/
		}

		while (nums.isEmpty() == false || op.isEmpty() == false) {
			if (nums.isEmpty())
				tempOp.push(op.pop());
			else if (op.isEmpty())
				tempNums.push(nums.pop());
			else {
				tempNums.push(nums.pop());
				tempOp.push(op.pop());
			}
		}

		while (tempOp.isEmpty() == false) {
			first = tempNums.pop();
			second = tempNums.pop();
			operator = tempOp.pop();
			System.out.println("first: " + first + ", second: " + second);
			if (tempOp.isEmpty()) {
				if (operator.equals("+")) {
					total = first + second;
					tempNums.push(total);
				} else if (operator.equals("-")) {
					total = first - second;
					tempNums.push(total);
				} else if (operator.equals("*")) {
					total = first * second;
					tempNums.push(total);
				} else if (operator.equals("/")) {
					total = first / second;
					tempNums.push(total);
				}
				break;
			}
			if (operator.equals("*")) {
				total = first * second;
				tempNums.push(total);
			} else if (operator.equals("/")) {
				total = first / second;
				tempNums.push(total);
			} else if (tempOp.peek().equals("*") || tempOp.peek().equals("/")) {
				operator2 = tempOp.pop();
				third = tempNums.pop();
				if (operator2.equals("*")) {
					total = third * second;
					tempNums.push(total);
					tempNums.push(first);
					tempOp.push(operator);
				} else if (operator2.equals("/")) {
					total = second / third;
					tempNums.push(total);
					tempNums.push(first);
					tempOp.push(operator);
				}
			} else {
				if (operator.equals("+")) {
					total = first + second;
					tempNums.push(total);
				} else {
					total = first - second;
					tempNums.push(total);
				}
			}
			System.out.println("Peek Nums: " + tempNums.peek());

		}
		System.out.println("final peek: " + tempNums.peek());
		return tempNums.pop();
	}
}
