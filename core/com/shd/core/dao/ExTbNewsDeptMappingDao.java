package com.shd.core.dao;

import org.springframework.stereotype.Repository;

import com.shd.core.pojo.ExTbNewsDeptMappingM;
import com.shd.core.utils.BasePojoDAO;

@Repository
public class ExTbNewsDeptMappingDao extends BasePojoDAO<ExTbNewsDeptMappingM, Long> {
	
	@Override
	protected Class<ExTbNewsDeptMappingM> getPojoClass() {
		return ExTbNewsDeptMappingM.class;
	}
}
