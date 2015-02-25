package calculator;

class ArithNode extends Node {
	private Node m_left;
	private Node m_right; 
	private Operator m_typ;
	
	public enum Operator {
		ADD,
		SUBT,
		DIV,
		MULT;
	}
	
	public ArithNode(Node left, Node right, Operator type) {
		super();
		this.m_left = left;
		this.m_right = right;
		this.m_typ = type;
	}
	
	@Override
	public int getValue() {
		switch (this.m_typ) {
			case MULT: return m_left.getValue() * m_right.getValue();
			case DIV: return m_left.getValue() / m_right.getValue();
			case ADD: return m_left.getValue() + m_right.getValue();
			case SUBT: return m_left.getValue() - m_right.getValue();
		}
		return 0;
	}

}
