package calculator;

public class LetNode extends Node {
	private Node m_left;
	private Node m_right;
	private String m_expr;
	public LetNode(String inExpr) {
		super();
		this.m_expr = inExpr;
	}

	@Override
	public int getValue() {
		
		return 0;
	}
	
	public void resolveString() {
		
	}

}
