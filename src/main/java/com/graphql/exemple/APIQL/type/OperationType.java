package com.graphql.exemple.APIQL.type;

public enum OperationType{
	eq("="), neq("<>"), contains("LIKE"), ncontains("LIKE"), gt(">"), lt("<"), in("in");
	
	private final String name;
	
	OperationType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}