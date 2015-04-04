package com.issamux.ast;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MainTest {

	public static final String TEST_FILE = "test.java";

	public static void main(String[] args) {

		// creates an input stream for the file to be parsed
		FileInputStream in;
		try {
			in = new FileInputStream(TEST_FILE);
			try {
				// parse the file
				CompilationUnit cu = JavaParser.parse(in);
				new MyMethodVisitor().visit(cu, null);
			} catch (ParseException e) {
				e.printStackTrace();
			} finally {
				in.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class MyMethodVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(MethodDeclaration method, Object arg) {
			//
			System.out.println("method body : " + method.toString());
			System.out.println("*******************************");
			//
			addForStmt(method.getBody());
			//
			System.out.println("method body : " + method.toString());
			//
		}

		private void addForStmt(BlockStmt body) {

			int beginLine = body.getBeginLine();
			int beginColumn = body.getBeginColumn();
			int endLine = body.getEndLine();
			int endColumn = body.getEndColumn();
			//
			List<Expression> init = new ArrayList<Expression>();
			Expression compare = null;
			List<Expression> update = null;
			BlockStmt methodBody = new BlockStmt();
			ForStmt forStmt = new ForStmt(beginLine, beginColumn, endLine, endColumn, init, compare, update, methodBody);
			//
			ASTHelper.addStmt(body, forStmt);
		}

	}

}
