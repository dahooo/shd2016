package com.shd.core.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.shd.core.dao.BcUserTableDao;
import com.shd.core.dao.ExTbNewsFilesModelDao;
import com.shd.core.dao.ExTbNewsModelDao;
import com.shd.core.entity.EiTbHrEmpEntity;
import com.shd.core.entity.ExTbNewsModelMEntity;
import com.shd.core.pojo.BcUserTable;
import com.shd.core.pojo.ExTbNewsFilesM;
import com.shd.core.pojo.ExTbNewsModelM;
import com.shd.utils.ImDateStringUtils;
import com.shd.utils.ImDateUtils;
import com.shd.utils.ImStringUtils;
import com.shd.utils.StreamCopier;
import com.shd.utils.UUIDHexGenerator;

/**
 * 
 * 電子公布欄/新聞
 * @author Roy
 *
 */
@Service
public class ShdCmdService extends BaseService{
	
	@Autowired
	private BcUserTableDao bcUserTableDao;
	
	@Autowired
	private ExTbNewsModelDao exTbNewsModelDao;

	@Autowired
	private ExTbNewsFilesModelDao exTbNewsFilesModelDao;
	
	@Autowired
	@Qualifier("imConfiguration")
	protected Properties imConfiguration;

	//AD
	private String adLogPath = "D:/TDI/batch/logs/";
	private String tdLdapURL = "ldap://TPEBIMobileDB.shiseido.com.tw:389";
	private String tdsadminId = "cn=tdsadmin,cn=users,o=shiseido";
	private String tdsadminPass = "p@ssw0rd";
	
	//新聞
	private String newsUploadLogPath = "D:/IBM/batch/logs/SHD_AUTO_Upload_News_";

	
	@Transactional(propagation = Propagation.SUPPORTS)
	public Set<EiTbHrEmpEntity> getEmpUserIdList(){
		return exTbNewsModelDao.getEmpUserIdList();
	}

	/**
	 * 比對mobile db 並產出quit.text and new.text
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void outputUpdateAccount() throws Exception {
		String currentTime = ImDateStringUtils.fetchCurrentDateString("yyyyMMdd");
		String currentTimess = ImDateStringUtils.fetchCurrentDateString("yyyyMMddHHmmss");
		
		String sysTemAccounts = (String) imConfiguration.get("sysTemAccounts");
		
		
		//新增
		PrintStream pst = new PrintStream(new FileOutputStream(adLogPath+"new_"+currentTimess+".txt", false));
		System.setOut(pst);
		
		PrintStream quit = new PrintStream(new FileOutputStream(adLogPath+"quit_"+currentTimess+".txt", false));
		System.setErr(quit);
		
		List<BcUserTable> list = bcUserTableDao.findAll();
		Set<String> mobileSet = new HashSet<String>();
		for(BcUserTable bc:list){
			mobileSet.add(bc.getUserID());
		}

		//新進BC
		Set<String> notInADSet = new HashSet<String>();
		try{
			InputStream fis = new FileInputStream(adLogPath+"Not_In_AD_Accounts_"+currentTime+".log");
			InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				String userId = String.valueOf(line.split(",")[0]);
				String password = String.valueOf(line.split(",")[1]);
				notInADSet.add(userId);
				if(!mobileSet.contains(userId) && !sysTemAccounts.contains(userId)){
					System.out.println( "===========================");
					bcUserTableDao.saveNewBcTable(userId, "Y", "N");
					this.addADAccount(userId, password);
					System.out.println( "[成功]新增 DB User Id:" + userId + ".");
				}
			}
		}catch (Exception e) {
			System.out.println( "[錯誤]" + e.getMessage() + ".");
		}

		//離職BC
		for(BcUserTable bc:list){
			String userId = bc.getUserID();
			String status = bc.getStatus();
			if(!notInADSet.contains(userId) && "N".equals(status)){
				System.err.println( "===========================");
				try {
					BcUserTable quitBc = bcUserTableDao.find(userId);
					quitBc.setStatus("Y");
					quitBc.setEditedTime(ImDateUtils.getSysDateTime());
					System.err.println( "[成功]已變更離職 User Id:" + userId + ".");
					this.changeADPassword(userId,"1234567891011121314151617181920");
				} catch (Exception e) {
					System.err.println( "[錯誤]" + e.getMessage() + ".");
				}
			}
		}
	}
	
	/**
	 * 變更AD密碼
	 * @param userId
	 */
	private void changeADPassword(String userId,String pass){
		Hashtable env = new Hashtable();
        String userName = "CN="+userId;
        String searchBase = ",cn=users,o=shiseido";
      
        env.put(Context.PROVIDER_URL,tdLdapURL);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PRINCIPAL,tdsadminId);
        env.put(Context.SECURITY_CREDENTIALS,tdsadminPass);
  
        env.put(Context.SECURITY_AUTHENTICATION,"Simple");
         
        try {
            DirContext ctx = new InitialDirContext(env);
            ModificationItem[] mods = new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userpassword", pass));

            ctx.modifyAttributes(userName + searchBase, mods);
            System.err.println( "[成功]更改密碼 AD User Id:" + userId + ".");
            ctx.close();
        }catch (NamingException e) {
        	System.err.println( "[失敗]更改密碼 AD User Id:" + userId + ". "+e.getMessage());
        }
		
	}
	
	
	
	
	/**
	 * 新增AD帳號
	 * @param userId
	 * @param pass
	 */
	private void addADAccount(String userId,String pass){
		Hashtable env = new Hashtable();
		String userName = "cn="+userId;
        String uid = userId;
        String newPassword = pass;
        String searchBase = ",cn=users,o=shiseido";

        env.put(Context.PROVIDER_URL,tdLdapURL);
        
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PRINCIPAL,tdsadminId);
        env.put(Context.SECURITY_CREDENTIALS,tdsadminPass);
  
        env.put(Context.SECURITY_AUTHENTICATION,"Simple"); //No other SALS worked with me  
                 
        Attributes matchAttrs = null;
        Attribute objectClass = new BasicAttribute("objectClass");
        
        try {
            DirContext ctx = new InitialDirContext(env);

            matchAttrs = new BasicAttributes(true);
            objectClass.add("inetOrgPerson");
            objectClass.add("organizationalPerson");
            objectClass.add("person");
            objectClass.add("top");
            matchAttrs.put(objectClass);
            matchAttrs.put(new BasicAttribute("uid", uid));
            matchAttrs.put(new BasicAttribute("cn", uid));
            matchAttrs.put(new BasicAttribute("sn", uid));
            matchAttrs.put(new BasicAttribute("givenName", uid));
            matchAttrs.put(new BasicAttribute("userPassword", newPassword)); 

            // Add the entry
            ctx.createSubcontext(userName + searchBase, matchAttrs);
            System.out.println( "[成功]新增AD User Id:" + userId + ".");

            ctx.close();
        }catch (NamingException e) {
        	System.out.println( "[*失敗*]新增AD User Id:" + userId + ". " +e.getMessage());
        }
	}
	
	
	/*
	private void renameFile(File backupNewFile, File backupNewFile2) {
		if(backupNewFile.renameTo(backupNewFile2)){
            System.out.println("File renamed");
        }else{
            System.out.println("Sorry! the file can't be renamed");
        }
	}*/




	@Transactional(propagation = Propagation.SUPPORTS)
	public void composeADAccount() throws IOException{		
		String currentTime = ImDateStringUtils.fetchCurrentDateString("yyyyMMdd");
		
		PrintStream pst = new PrintStream(new FileOutputStream(adLogPath+"AD_HR_search_"+currentTime+".log", false));
		System.setOut(pst);
		
		PrintStream notInPst = new PrintStream(new FileOutputStream(adLogPath+"SHD_AUTO_Upload_News_"+currentTime+".log", false));
		System.setErr(notInPst);
		
		
		//from AD
		LdapContext lc = getLdapContext();
		Set<String> adSet = new HashSet<String>();
		try {
			adSet = listSubContext(lc, "DC=shiseido,DC=com,DC=tw");
		} catch (NamingException e) {
			System.out.println("=====讀取AD失敗!=====");
			e.printStackTrace();
		}
		System.out.println("==From AD:");	
		for(String id:adSet){
			System.out.println(id);	
		}
		
		//form DB
		Set<EiTbHrEmpEntity> empSet = exTbNewsModelDao.getEmpUserIdList();
		System.out.println("==From DB:");	
		Set<String> dbIdSet = new HashSet<String>();
		Map<String,String> empMap = new HashMap<String,String>();
		for(EiTbHrEmpEntity emp:empSet){
			String defalutPassword = "12345678";
			if(ImStringUtils.isNotBlank(emp.getBirthday())){
				defalutPassword = emp.getBirthday();
			}
			empMap.put(emp.getEmpNo(), defalutPassword);
			System.out.println(emp.getEmpNo()+","+defalutPassword);	
			dbIdSet.add(emp.getEmpNo());
		}

		//diff
		Set<String> notInAdSet = Sets.difference(dbIdSet, adSet);
		System.out.println("==not in AD accounts:===================");	
		for(String id:notInAdSet){
			System.out.println(id+","+empMap.get(id));
			System.err.println(id+","+empMap.get(id));	
		}
		System.out.println("==From AD DB:"+adSet.size());
		System.out.println("==From HR DB:"+dbIdSet.size());
		System.out.println("==not in AD DB:"+notInAdSet.size());
	}
	
	
	private LdapContext getLdapContext(){
        LdapContext ctx = null;
        try{
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
            env.put(Context.SECURITY_PRINCIPAL, "CN=ExpenseTestUser,OU=SAP,DC=shiseido,DC=com,DC=tw");
            env.put(Context.SECURITY_CREDENTIALS, "123456");
            env.put(Context.PROVIDER_URL, "ldap://tpedc.shiseido.com.tw:389");
            ctx = new InitialLdapContext(env, null);
            System.out.println("=====與AD連線成功!=====");
            System.out.println("==AD Connection Successfully");
        }catch(NamingException nex){
        	System.out.println("=====與AD連線失敗!=====");
        	System.out.println("==AD Connection Fail");
            nex.printStackTrace();
        }
        return ctx;
    }
	
	
	private Set<String> listSubContext(DirContext ctx, String rootContext) throws NamingException {
		Set<String> adSet = new HashSet<String>();
		
		String[] returnedAtts = {"sn","mail","givenname","sAMAccountName"};
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(returnedAtts);
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String searchFilter = "(&(objectClass=user)(mail=*))";
        
		NamingEnumeration answer = ctx.search(rootContext, searchFilter, searchCtls);
		while (answer.hasMoreElements()) {
			SearchResult sr = (SearchResult)answer.next();
			Attributes attrs = sr.getAttributes();
			
			if(attrs.get("sAMAccountName") != null){
				try{
					String id = attrs.get("sAMAccountName").get()+"";
					adSet.add(id);
				}catch(Exception nex){
		        }
			}
		}
		return adSet;
    }
	
	
	
	private String getMsgWithTime(String msg){
		return  ImDateStringUtils.fetchCurrentDateString("yyyy-MM-dd HH:mm:ss")+ ":" + msg ;
	}
	
	
	/**
	 * 企業新聞自動新增
	 * @throws IOException 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateNewsByBatch() throws IOException{
		String currentTime = ImDateStringUtils.fetchCurrentDateString("yyyyMMdd");
		
		PrintStream pst = new PrintStream(new FileOutputStream(newsUploadLogPath+currentTime+".log", true));
		//PrintStream pst = new PrintStream(new FileOutputStream("C:/IBM/SHD_Portlet/batch/SHD_AUTO_Upload_News.log", true));
		System.setOut(pst);
		
		String newsTempRoot = (String) imConfiguration.get("newsRoot")+"/上傳區";
		try{
			File dir = new File(newsTempRoot);
		    File[] firstLevelFiles = dir.listFiles();
		    if (firstLevelFiles != null && firstLevelFiles.length > 0) {
		    	System.out.println(getMsgWithTime("=====開始執行企業新聞自動上傳====="));
				System.out.println(getMsgWithTime("==路徑:"+newsTempRoot));
		    	int i = 1;
		        for (File aFile : firstLevelFiles) {
		        	if (aFile.isFile()) {
		        		System.out.println(getMsgWithTime("*******檔案"+i+":"+aFile.getName()+"*******"));
		            	String fileName = getFileName(aFile);
		            	String fileType = getExtension(aFile);
		            	if("pdf".equals(fileType)){//need type pdf
		            		ExTbNewsModelMEntity em = new ExTbNewsModelMEntity();
		            		em.setTOPIC(fileName);
			            	em.setFILE_TYPE(fileType);
			            	try {
								autoSaveExTbNewsModelM(em,aFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
		            	}else{
		            		System.out.println(getMsgWithTime(aFile.getName()+"須為.pdf檔案"));
		            	}
		        	}
		        	i++;
		        }
		        System.out.println(getMsgWithTime("=====結束執行企業新聞自動上傳====="));
		    }else{
		    	System.out.println(getMsgWithTime("*****上傳區無檔案*****"));
		    }  
	    
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(getMsgWithTime(e.getMessage()));
		}
	}
	
	/**
	 * 自動儲存新聞與附檔
	 * @param param
	 * @param aFile 
	 * @throws Exception
	 */
	private void autoSaveExTbNewsModelM(ExTbNewsModelMEntity param, File aFile) throws Exception {
		
		if(ImStringUtils.isNotBlank(param.getEND_DATE_STR())){
			param.setEND_DATE(ImDateStringUtils.transDateStrToTimestamp(param.getEND_DATE_STR(), "yyyyMMdd"));
		}
		String uuid = new UUIDHexGenerator().generate();
		//移動資料按照月份至網路磁碟備份並刪除
		String year = ImDateStringUtils.fetchCurrentDateString(new Date(), "yyyy");
		Calendar today = Calendar.getInstance();
		String month = (today.get(Calendar.MONTH)+1)+""; 
		String newsBackUpRoot = (String) imConfiguration.get("newsRoot")+"/"+year+"每日剪報摘要/日報/"+month+"月";
		File backUpDir = new File(newsBackUpRoot);
		if(!backUpDir.exists()){
			System.out.println(getMsgWithTime("資料夾不存在,建立資料夾:"+newsBackUpRoot));
			if (backUpDir.mkdirs()) {
				try{
					moveFile(newsBackUpRoot, aFile,uuid,param);
					saveNewsToDB(uuid,param);
				} catch (Exception e) {throw e;}
			}else{
				System.out.println(getMsgWithTime("資料夾:"+newsBackUpRoot+",建立失敗."));
			}
		}else{
			try{
				moveFile(newsBackUpRoot, aFile,uuid,param);
				saveNewsToDB(uuid,param);
			} catch (Exception e) {throw e;}
		}
	}
	

	private void saveNewsToDB(String uuid,ExTbNewsModelMEntity param) throws Exception{
		Date end_date = ImDateUtils.add(new Date(), Calendar.DATE, 3);
		ExTbNewsModelM etm = this.transferEntity2Pojo(param, ExTbNewsModelM.class);
		etm.setCREATED_DATE(ExTbNewsModelMEntity.systime());
		etm.setCREATED_ID("118245");
		etm.setUPDATED_DATE(ExTbNewsModelMEntity.systime());
		etm.setUPDATED_ID("118245");
		etm.setDELETE_FLAG("0");
		etm.setEND_DATE(new Timestamp(end_date.getTime()));
		etm.setNEWS_TYPE(new Long(2));//產業新聞
		Long newsId = exTbNewsModelDao.save(etm);
		
		ExTbNewsFilesM etf = new ExTbNewsFilesM();
		etf.setFILE_ID(uuid);
		etf.setFILE_NAME(param.getTOPIC());
		etf.setFILE_TYPE(param.getFILE_TYPE());
		etf.setNEWS_ID(newsId);
		exTbNewsFilesModelDao.save(etf);
		System.out.println(getMsgWithTime("*資料儲存至資料庫*uuid:"+uuid));
	}
	
	
	private String getFileName(File file){
        int endIndex = file.getName().lastIndexOf(46);
        return  file.getName().substring(0, endIndex);
    }
	
	private String getExtension(File file){
        int startIndex = file.getName().lastIndexOf(46) + 1;
        int endIndex = file.getName().length();
        return  file.getName().substring(startIndex, endIndex);
    }
	
	
	
	
	
	public void moveFile(String dstPath, File sourceFile,String uuid,ExTbNewsModelMEntity param) throws Exception{		
		File dst = new File(dstPath, sourceFile.getName());	
		if(!dst.exists()){
			boolean	success = sourceFile.renameTo(dst);		
			if (!success){
				System.out.println(getMsgWithTime(sourceFile.getName()+"移動檔案失敗"));
				throw new Exception(sourceFile.getName()+"移動檔案失敗");
			}else{
				//移動資料從原來至web資料夾
				String filesRoot = (String) imConfiguration.get("filesRoot");
				copyFile(filesRoot+"/"+uuid+"."+param.getFILE_TYPE(), dst);
				System.out.println(getMsgWithTime(sourceFile.getName()+"檔案已複製至資料夾:"+dstPath));
			}
		}else{
			System.out.println(getMsgWithTime(sourceFile.getName()+"檔案重複"));
			throw new Exception(sourceFile.getName()+"檔案重複");
		}
	}


	
	public void copyFile(String targetPath, File fileObj) throws Exception{
		File targetFile = new File(targetPath);
		if (!targetFile.getCanonicalPath().equalsIgnoreCase(fileObj.getCanonicalPath())) {
			FileInputStream fin = null;
			FileOutputStream fout = null;
			try {
				fin = new FileInputStream(fileObj);
				fout = new FileOutputStream(targetFile);
				StreamCopier.copy(fin, fout);
				fin.close();
				fout.close();
			} catch (Exception e) {
				throw e;
			} finally {
				try {
					if (fin != null)
						fin.close();
				} catch (IOException e) {
					throw e;
				}
				try {
					if (fout != null)
						fout.close();
				} catch (IOException e) {
					throw e;
				}
			}

		}
	}



	

	
	

	
}
