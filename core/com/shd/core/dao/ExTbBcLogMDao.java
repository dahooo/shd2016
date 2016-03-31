package com.shd.core.dao;

import org.springframework.stereotype.Repository;

import com.shd.core.pojo.ExTbBcLogM;
import com.shd.core.utils.BasePojoDAO;

@Repository
public class ExTbBcLogMDao extends BasePojoDAO<ExTbBcLogM, Long> {
	
	@Override
	protected Class<ExTbBcLogM> getPojoClass() {
		return ExTbBcLogM.class;
	}
}
