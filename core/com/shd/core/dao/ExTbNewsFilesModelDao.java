package com.shd.core.dao;

import org.springframework.stereotype.Repository;

import com.shd.core.pojo.ExTbNewsFilesM;
import com.shd.core.utils.BasePojoDAO;

@Repository
public class ExTbNewsFilesModelDao extends BasePojoDAO<ExTbNewsFilesM, Long> {
	
	@Override
	protected Class<ExTbNewsFilesM> getPojoClass() {
		return ExTbNewsFilesM.class;
	}
}
