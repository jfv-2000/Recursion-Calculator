import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Calculator2 {
	//	static double answer;
	/**
	 * method to check if string is a number
	 * @param expression
	 * @return
	 */
	public static boolean isNumber(String expression)
	{
		try
		{
			Double.parseDouble(expression);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
	/**
	 * method to calculate a factorial number
	 * @param n
	 * @return
	 */
	public static double factorial(double n)
	{
		if (n == 1 || n == 0)
			return 1;
		else
			return n*factorial(n-1);
	}
	/**
	 * recursive method to calculate the expression
	 * @param expression
	 * @return
	 */
	public static double EvalExp(String expression)
	{
		String operand1Str;
		String operand2Str;
		double operand1;
		double operand2;
		int opIndex = 0;
		double answer = 0;
		Pattern patternFactorial = Pattern.compile("!(?!=)");
		Matcher matcherFactorial = patternFactorial.matcher(expression);
		Pattern patternNotEqual = Pattern.compile("!(?==)");
		Matcher matcherNotEqual = patternNotEqual.matcher(expression);
		Pattern patternEqual = Pattern.compile("=(?==)");
		Matcher matcherEqual = patternEqual.matcher(expression);
		Pattern patternGreaterEqual = Pattern.compile(">(?==)");
		Matcher matcherGreaterEqual = patternGreaterEqual.matcher(expression);
		Pattern patternLessEqual = Pattern.compile("<(?==)");
		Matcher matcherLessEqual = patternLessEqual.matcher(expression);
		boolean multipleCharOp = false;
		if (isNumber(expression))
			return Double.parseDouble(expression);
		//method to calculate value in parentheses (priority)
		while(expression.contains("("))
		{
			for(int i = 0; i < expression.length(); i++)
			{
				if(expression.charAt(i) == ')')
				{
					for(int n = i; n >= 0; n--)
					{
						if(expression.charAt(n) == '(')
						{
							expression = expression.substring(0, n) + EvalExp(expression.substring(n + 1, i)) + expression.substring(i + 1);
							break;
						}
					}
					break;
				}
			}
		}
		if(isNumber(expression))
			answer += Double.parseDouble(expression);
		else
		{
			//finding the index of the operator in the expression
			for(opIndex = expression.length() - 1; opIndex >= 0; opIndex--)
			{
				if(matcherGreaterEqual.find())
				{
					multipleCharOp = true;
					opIndex = expression.indexOf(">=");
					break;
				}
				else if(matcherLessEqual.find())
				{
					System.out.println("opIndex: "+opIndex);
					opIndex = expression.indexOf("<=");
					multipleCharOp = true;
					break;
				}
				else if(expression.charAt(opIndex) == '>')
					break;
				else if(expression.charAt(opIndex) == '<')
					break;
				else if(matcherEqual.find())
				{
					opIndex = expression.indexOf("==");
					multipleCharOp = true;
					break;
				}
				else if(matcherNotEqual.find())
				{
					System.out.println("HElooooo");
					multipleCharOp = true;
					opIndex = expression.indexOf("!=");
					System.out.println("opIndex != " + opIndex);
					break;
				}
			}
			if(opIndex < 0)
			{
				for(opIndex = expression.length() - 1; opIndex >= 0; opIndex--)
				{
					if(expression.charAt(opIndex) == '+' || expression.charAt(opIndex) == '-')
					{
						break;
					}
				}
			}
			if(opIndex < 0)
			{
				for(opIndex = expression.length() - 1; opIndex >= 0; opIndex--)
				{
					if(expression.charAt(opIndex) == '*' || expression.charAt(opIndex) == '/')
					{
						break;
					}
				}
			}
			if(opIndex < 0)
			{
				for(opIndex = expression.length() - 1; opIndex >= 0; opIndex--)
				{
					if(expression.charAt(opIndex) == '^')
					{
						break;
					}
				}
			}
			if(opIndex < 0)
			{
				for(opIndex = expression.length() - 1; opIndex >= 0; opIndex--)
				{ 
					if(matcherFactorial.find())
					{
						System.out.println("found factorial");
						break;
					}
				}
			}
			operand1Str = expression.substring(0, opIndex);
			operand2Str = expression.substring(opIndex + 1, expression.length());
			double powerAnswer = 1;
			//if the operator is of one character only
			if(multipleCharOp == false)
			{
				switch(expression.charAt(opIndex))
				{
				case '+':
					answer += EvalExp(operand1Str) + EvalExp(operand2Str);
					break;
				case '-':
					answer += EvalExp(operand1Str) - EvalExp(operand2Str);
					break;
				case '*':
					answer += EvalExp(operand1Str) * EvalExp(operand2Str);
					break;
				case '/':
					operand2 = EvalExp(operand2Str);
					if(operand2 == 0)
					{
						System.out.println("Cannot divide by zero.");
						System.exit(1);
					}
					else
						answer += EvalExp(operand1Str) / operand2;
					break;	
				case '^':
					operand2 = EvalExp(operand2Str);
					if (Double.parseDouble(operand2Str) == 0)
						answer += powerAnswer;
					else
					{
						for(int j = 0; j < operand2; j++)
						{
							powerAnswer = powerAnswer*EvalExp(operand1Str);
						}
						answer += powerAnswer;
						System.out.println("answer after pwoer: " + answer);
					}
					break;
				case '!':
					answer += factorial(Double.parseDouble(operand1Str));
					break;

				case '>':
					if(EvalExp(operand1Str) > EvalExp(operand2Str))
						answer = 1;
					else
						answer = 0;
					break;
				case '<':
					if(EvalExp(operand1Str) < EvalExp(operand2Str))
						answer = 1;
					else
						answer = 0;
					break;

				}
			}
			//if the operator is of more than 1 character
			else
			{
				operand1Str = expression.substring(0, opIndex);
				operand2Str = expression.substring(opIndex + 2, expression.length());
				switch(expression.substring(opIndex, opIndex+2))
				{
				case ">=":
					multipleCharOp = false;
					if(EvalExp(operand1Str) >= EvalExp(operand2Str))
						answer = 1;
					else
						answer = 0;
					break;
				case "<=":
					multipleCharOp = false;
					if(EvalExp(operand1Str) <= EvalExp(operand2Str))
						answer = 1;
					else
						answer = 0;
					break;
				case "==":
					multipleCharOp = false;
					if(EvalExp(operand1Str) == EvalExp(operand2Str))
						answer = 1;
					else
						answer = 0;
					break;
				case "!=":
					multipleCharOp = false;
					if(EvalExp(operand1Str) != EvalExp(operand2Str))
						answer = 1;
					else
						answer = 0;
					break;
				}
			}
		}
		return answer;
	}

	public static void main(String[] args) {
		PrintWriter pw;
		Scanner sc;
		try
		{
			String expression;
			pw = new PrintWriter(new FileOutputStream("results2.txt"));
			sc = new Scanner(new FileInputStream("expressions.txt"));
			while(sc.hasNextLine())
			{
				expression = sc.nextLine();
				pw.println("\n" + expression);
				pw.println("Answer: " + EvalExp(expression));
			}
			pw.close();
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("FileNotFoundException");
		}
	}
}
