package com.shd.core.utils;

import org.hibernate.dialect.SQLServer2008Dialect;

public class ImSQLServerDialect extends SQLServer2008Dialect {

	public ImSQLServerDialect() {

		super();
		registerHibernateType(-9, "string");
	}

	@Override
	public boolean supportsLimit() {

		return false;
	}
}
