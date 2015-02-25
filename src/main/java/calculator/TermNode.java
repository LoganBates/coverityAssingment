package calculator;

import calculator.ArithNode.Operator;

public class TermNode extends Node {
	private String m_value;
	
	public TermNode(String val) {
		 super();
		 this.m_value = val;
	}

	@Override
	public int getValue() {
		return Integer.parseInt(m_value);
	}

}
