package com.shd.core.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.shd.core.pojo.BcUserTable;
import com.shd.core.utils.BasePojoDAO;
import com.shd.utils.ImDateUtils;

@Repository
public class BcUserTableDao extends BasePojoDAO<BcUserTable, String> {
	
	@Override
	protected Class<BcUserTable> getPojoClass() {
		return BcUserTable.class;
	}
	
	
	public void saveNewBcTable(String UserID,String IsNewEmployee,String Status) {
		SQLQuery query = getNamedSQLQuery("user.save.saveNewBcTable");
		query.setParameter("UserID",UserID);
		query.setParameter("IsNewEmployee", IsNewEmployee);
		query.setParameter("Status",Status);
		query.setParameter("CreatedTime",ImDateUtils.getSysDateTime());
		query.setParameter("EditedTime",ImDateUtils.getSysDateTime());
		query.executeUpdate();
	}
}
