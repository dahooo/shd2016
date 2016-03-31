package com.shd.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import com.shd.core.entity.ExTbBcModelMEntity;
import com.shd.core.utils.BaseDAO;
import com.shd.core.utils.Pagination;

@Repository
public class ExTbBcModelMDao extends BaseDAO{

	
	public Pagination<ExTbBcModelMEntity> getExTbBcModelMByStatusPagination(String edit_status, int currentPage, int pageSize){		
		SQLQuery query = getNamedSQLQuery("bc.query.getReleaseExTbBcModelMPagination");
		query.setParameter("EDIT_STATUS", edit_status);
		return setBcObject(currentPage, pageSize, query);
	}
	
	public Pagination<ExTbBcModelMEntity> getStatus4ExTbBcModelMPagination(
			ExTbBcModelMEntity ett, int currentPage, int pageSize,String empNo) {
		SQLQuery query = getNamedSQLQuery("bc.query.getStatus4ExTbBcModelMPagination");
		query.setParameter("empNo", empNo);
		return setBcObject(currentPage, pageSize, query);
	}
	
	
	public Pagination<ExTbBcModelMEntity> getStatus5ExTbBcModelMPagination(
			ExTbBcModelMEntity ett, int currentPage, int pageSize) {
		
		if("A".equals(ett.getRoleLevel())){
			SQLQuery query = getNamedSQLQuery("bc.query.getStatus5ExTbBcModelMPaginationRoleA");
			return setBcObject(currentPage, pageSize, query);
		}else{
			SQLQuery query = getNamedSQLQuery("bc.query.getStatus5ExTbBcModelMPagination");
			query.setParameter("deptCode", ett.getDeptCode()+"%");
			return setBcObject(currentPage, pageSize, query);
		}
	}
	
	
	public Pagination<ExTbBcModelMEntity> getUnReleaseExTbBcModelMPagination(ExTbBcModelMEntity ett, int currentPage, int pageSize){		
			if("A".equals(ett.getRoleLevel())){
				SQLQuery query = getNamedSQLQuery("bc.query.getExTbBcModelMAPagination");
				query.setParameter("empNo", ett.getEmpNo());
				return setBcObject(currentPage, pageSize, query);
			}else if("B".equals(ett.getRoleLevel())){
				SQLQuery query = getNamedSQLQuery("bc.query.getExTbBcModelMBPagination");
				query.setParameter("empNo", ett.getEmpNo());
				query.setParameter("deptCode", ett.getDeptCode()+"%");
				return setBcObject(currentPage, pageSize, query);
			}else if("C".equals(ett.getRoleLevel())){
				SQLQuery query = getNamedSQLQuery("bc.query.getExTbBcModelMCPagination");
				query.setParameter("empNo", ett.getEmpNo());
				query.setParameter("deptCode", ett.getDeptCode()+"%");
				return setBcObject(currentPage, pageSize, query);
			}else if("D".equals(ett.getRoleLevel())){
				SQLQuery query = getNamedSQLQuery("bc.query.getExTbBcModelMDPagination");
				query.setParameter("empNo", ett.getEmpNo());
				return setBcObject(currentPage, pageSize, query);
			}else{
				Pagination pagination = new Pagination(new ArrayList(), currentPage, pageSize, 0);
				return pagination;
			}
	}


	private Pagination<ExTbBcModelMEntity> setBcObject(int currentPage, int pageSize,
			SQLQuery query) {
		query.addScalar("BC_M_ID", new StringType());		
		query.addScalar("NAME", new StringType());
		query.addScalar("SENIORITY", new StringType());
		query.addScalar("BC_GROUP", new StringType());
		query.addScalar("STORE_NAME", new StringType());
		query.addScalar("CREATED_DATE", new TimestampType());
		query.addScalar("CREATED_ID", new StringType());
		query.addScalar("UPDATED_DATE", new TimestampType());
		query.addScalar("UPDATED_ID", new StringType());
		query.addScalar("TOPIC", new StringType());
		query.addScalar("PIC_NAME", new StringType());
		query.addScalar("EDIT_STATUS", new StringType());
		query.addScalar("INTRODUCTION", new StringType());
		
		query.addScalar("empCname", new StringType());
		query.setResultTransformer(Transformers.aliasToBean(ExTbBcModelMEntity.class));
		return this.processPagination(currentPage, pageSize, query);
	}
	
	
	public String saveExTbBcModelM(ExTbBcModelMEntity exTbBcModelM) {
		String key = exTbBcModelM.getNEW_UPLOAD_PIC_NAME().split("\\.")[0];
		SQLQuery query = getNamedSQLQuery("bc.save.saveExTbBcModelM");
		query.setParameter("BC_M_ID",key);
		query.setParameter("NAME", exTbBcModelM.getNAME());
		query.setParameter("SENIORITY", exTbBcModelM.getSENIORITY());
		query.setParameter("BC_GROUP", exTbBcModelM.getBC_GROUP());
		query.setParameter("STORE_NAME", exTbBcModelM.getSTORE_NAME());
		query.setParameter("CREATED_DATE", exTbBcModelM.systime());
		query.setParameter("CREATED_ID", exTbBcModelM.getEmpNo());
		query.setParameter("UPDATED_DATE", exTbBcModelM.systime());
		query.setParameter("UPDATED_ID", exTbBcModelM.getEmpNo());
		query.setParameter("TOPIC", exTbBcModelM.getTOPIC());
		query.setParameter("PIC_NAME", exTbBcModelM.getNEW_UPLOAD_PIC_NAME());
		query.setParameter("EDIT_STATUS", "1");
		query.setParameter("INTRODUCTION", exTbBcModelM.getINTRODUCTION());
		query.executeUpdate();
		return (key);
	}
	
	
	public int updateExTbBcModelMStatus(ExTbBcModelMEntity exTbBcModelM) {
		SQLQuery query = getNamedSQLQuery("bc.update.updateExTbBcModelMStatus");
		query.setParameter("BC_M_ID", exTbBcModelM.getBC_M_ID());
		query.setParameter("UPDATED_DATE", exTbBcModelM.systime());
		query.setParameter("UPDATED_ID", exTbBcModelM.getEmpNo());
		query.setParameter("EDIT_STATUS", exTbBcModelM.getEDIT_STATUS());
		if("6".equals(exTbBcModelM.getEDIT_STATUS())){
			query.setParameter("AWARD_DATE", exTbBcModelM.systime());
		}else{
			query.setParameter("AWARD_DATE", null);
		}
		return (query.executeUpdate());
	}
	
	
	public int updateExTbBcModelM(ExTbBcModelMEntity exTbBcModelM) {
		SQLQuery query = getNamedSQLQuery("bc.update.updateExTbBcModelM");
		query.setParameter("BC_M_ID", exTbBcModelM.getBC_M_ID());
		query.setParameter("NAME", exTbBcModelM.getNAME());
		query.setParameter("SENIORITY", exTbBcModelM.getSENIORITY());
		query.setParameter("BC_GROUP", exTbBcModelM.getBC_GROUP());
		query.setParameter("STORE_NAME", exTbBcModelM.getSTORE_NAME());
		query.setParameter("UPDATED_DATE", exTbBcModelM.systime());
		query.setParameter("UPDATED_ID", exTbBcModelM.getEmpNo());
		query.setParameter("TOPIC", exTbBcModelM.getTOPIC());
		query.setParameter("EDIT_STATUS", "7");
		query.setParameter("INTRODUCTION", exTbBcModelM.getINTRODUCTION());
		return (query.executeUpdate());
	}



	public ExTbBcModelMEntity getExTbBcModelMById(String bC_M_ID) {
		SQLQuery query = getNamedSQLQuery("bc.query.getExTbBcModelMById");
		query.setParameter("BC_M_ID", bC_M_ID);
		
		query.addScalar("BC_M_ID", new StringType());		
		query.addScalar("NAME", new StringType());
		query.addScalar("SENIORITY", new StringType());
		query.addScalar("BC_GROUP", new StringType());
		query.addScalar("STORE_NAME", new StringType());
		query.addScalar("CREATED_DATE", new TimestampType());
		query.addScalar("CREATED_ID", new StringType());
		query.addScalar("UPDATED_DATE", new TimestampType());
		query.addScalar("UPDATED_ID", new StringType());
		query.addScalar("TOPIC", new StringType());
		query.addScalar("PIC_NAME", new StringType());
		query.addScalar("EDIT_STATUS", new StringType());
		query.addScalar("INTRODUCTION", new StringType());
		
		query.setResultTransformer(Transformers.aliasToBean(ExTbBcModelMEntity.class));
		
		List list = query.list();
		
		if(list.size() > 0){
			ExTbBcModelMEntity ett = (ExTbBcModelMEntity)list.get(0);
			return ett;
		}else{
			return null;
		}
	}

	
	public int deleteExTbBcModelM(ExTbBcModelMEntity exTbBcModelM) {
		SQLQuery query = getNamedSQLQuery("bc.delete.deleteExTbBcModelM");
		query.setParameter("BC_M_ID", exTbBcModelM.getBC_M_ID());
		return (query.executeUpdate());
	}


	/**	 
	 * D:BC: 821, 822
	 * B:美容主任: 702,特戰主任: 703,美容擔當: 802
	 * C:美容課長: 512
	 * A:塗老師 /顧客G陳老師
	 * @param empNo
	 * @return
	 */
	public ExTbBcModelMEntity getBcLevelByEmpNo(String empNo) {
		String bcLevelA = (String) imConfiguration.get("bcLevelA");//固定名單
		ExTbBcModelMEntity ett = new ExTbBcModelMEntity();
		SQLQuery query = getNamedSQLQuery("bc.query.getBcPositionCodeByEmpNo");
		query.setParameter("empNo", empNo);
		query.addScalar("positionCode", new StringType());
		query.addScalar("empNo", new StringType());
		query.addScalar("deptCode", new StringType());
		query.addScalar("empCname", new StringType());
		query.setResultTransformer(Transformers.aliasToBean(ExTbBcModelMEntity.class));
		
		List list = query.list();
		if(list.size() > 0){
			ett = (ExTbBcModelMEntity)list.get(0);
			if(StringUtils.contains(bcLevelA, empNo)){
				ett.setRoleLevel("A");
				return ett;
			}else{
				String positionCode = ett.getPositionCode();
				if("821".equals(positionCode) || "822".equals(positionCode)){
					ett.setRoleLevel("D");
				}else if("702".equals(positionCode) || "703".equals(positionCode) || "802".equals(positionCode)){
					ett.setRoleLevel("C");
				}else if("512".equals(positionCode)){
					ett.setRoleLevel("B");
				}else{
					ett.setRoleLevel("E");
				}
				return ett;
			}
		}else{
			ett.setRoleLevel("E");
			return ett;
		}
	}



	

}
