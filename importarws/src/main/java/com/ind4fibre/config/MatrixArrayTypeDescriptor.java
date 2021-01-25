package com.ind4fibre.config;

import com.vladmihalcea.hibernate.type.array.internal.AbstractArrayTypeDescriptor;

public class MatrixArrayTypeDescriptor extends AbstractArrayTypeDescriptor<int[][]> {

	public MatrixArrayTypeDescriptor() {
		super(int[][].class);
	}

	@Override
	protected String getSqlArrayType() {
		return "integer";
	}
}
