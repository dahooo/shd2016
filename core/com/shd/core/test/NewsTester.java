package com.shd.core.test;
import java.util.HashSet;
import java.util.Hashtable;
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core.xml")
@TransactionConfiguration(defaultRollback = true)
public class NewsTester {

	@Test
	public void testAddUser(){
		Hashtable env = new Hashtable();
		String userName = "cn=99997799";
        String uid = "99997799";
        String newPassword = "99997799";
        String searchBase = ",cn=users,o=shiseido";
         
  
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
  
        //set security credentials, note using simple cleartext authentication
 
        env.put(Context.SECURITY_PRINCIPAL,"cn=tdsadmin,cn=users,o=shiseido");
        env.put(Context.SECURITY_CREDENTIALS,"p@ssw0rd");
  
        env.put(Context.SECURITY_AUTHENTICATION,"Simple"); //No other SALS worked with me  
                 
        //connect to my domain controller
        String ldapURL = "ldap://TPEBIMobileDB.shiseido.com.tw:389";
        env.put(Context.PROVIDER_URL,ldapURL);
         
        Attributes matchAttrs = null;
        Attribute objectClass = new BasicAttribute("objectClass");
        
        try {
  
            // Create the initial directory context
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
            System.out.println( "AddUser: added entry " + searchBase + ".");

            ctx.close();
 
        } 
        catch (NamingException e) {
            System.out.println("Problem resetting password: " + e);
        }
		
		
	}
	
	
	@Test
	public void testChangePass(){
		
		Hashtable env = new Hashtable();
        String userName = "CN=99997744";
        String newPassword = "123";
        String searchBase = ",cn=users,o=shiseido";
         
  
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
  
        //set security credentials, note using simple cleartext authentication
 
        env.put(Context.SECURITY_PRINCIPAL,"cn=tdsadmin,cn=users,o=shiseido");
        env.put(Context.SECURITY_CREDENTIALS,"p@ssw0rd");
  
        env.put(Context.SECURITY_AUTHENTICATION,"Simple"); //No other SALS worked with me  
                 
        //connect to my domain controller
        String ldapURL = "ldap://TPEBIMobileDB.shiseido.com.tw:389";
        env.put(Context.PROVIDER_URL,ldapURL);
         
        try {
  
            // Create the initial directory context
            DirContext ctx = new InitialDirContext(env);
         
            //set password is a ldap modfy operation
            ModificationItem[] mods = new ModificationItem[1];

            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userpassword", newPassword));
  
            // Perform the update
            ctx.modifyAttributes(userName + searchBase, mods);
            System.out.println(userName + " " + mods);
         
            System.out.println("Reset Password for: " + userName);  
            ctx.close();
 
        } 
        catch (NamingException e) {
            System.out.println("Problem resetting password: " + e);
        }
		
	}
	
	
	@Test
	public void testGetEmphMListPagination() throws Exception {
		LdapContext lc = getLdapContext();
		listSubContext(lc, "o=shiseido");
		
		
	}
	
	private LdapContext getLdapContext(){
        LdapContext ctx = null;
        try{
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
            env.put(Context.SECURITY_PRINCIPAL, "cn=tdsadmin,cn=users,o=shiseido");
            env.put(Context.SECURITY_CREDENTIALS, "p@ssw0rd");
            env.put(Context.PROVIDER_URL, "ldap://TPEBIMobileDB.shiseido.com.tw:389");
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
		
		String[] returnedAtts = {"dn","cn","sn","mail","givenname","userpassword"};
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(returnedAtts);
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String searchFilter2 = "(&(objectClass=person))"; 
		
		NamingEnumeration results = ctx.search(rootContext, searchFilter2, searchCtls);
        
        while (results.hasMore()) {
           SearchResult searchResult = (SearchResult) results.next();            
           Attributes attributes = searchResult.getAttributes();  
           
          // System.out.println("dn----------> "+searchResult.getName());
           System.out.println("cn----------> "+attributes.get("cn").get()); 
          // System.out.println("userpassword----------> "+attributes.get("userpassword").get()); 
           /*
           if (attributes.get("givenName")!=null)
                System.out.println("First Name--> "+attributes.get("givenName").get());
              
           System.out.println("Last Name---> "+attributes.get("sn").get());*/
           //System.out.println("Mail--------> "+attributes.get("mail").get()+"\n\n");
                   
        }
		
		/*
		NamingEnumeration answer = ctx.search(rootContext, searchFilter, searchCtls);
		while (answer.hasMoreElements()) {
			SearchResult sr = (SearchResult)answer.next();
			Attributes attrs = sr.getAttributes();
			
			if(attrs.get("sAMAccountName") != null){
				try{
					String id = attrs.get("sAMAccountName").get()+"";
					System.out.println(id+"=====");
					//adSet.add(id);
				}catch(Exception nex){
		        }
			}
		}*/
		return adSet;
    }
	
	
}
