package demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.sut.oodtime.common.model.User;

public class UsersDao {
	
	public boolean login(String account,String password) {
		String sql=Db.getSql("usersdao.login");
		long count=Db.queryLong(sql,account,password);
		boolean bool=count==1?true:false;
		return bool;
	}
	
	public boolean register(String account,String password) {
		List<Record> users=Db.find("select * from users where account=?",account);
		boolean bool=false;
		if(users==null) {
			System.out.println("NULL");
		}else if(users.size()==0){
			//SqlPara sp=Db.getSqlPara("usersdao.encrypt",password);
			//Record encryption=Db.findFirst(sp);
			//Record encryption=Db.findFirst("SELECT HEX(AES_ENCRYPT(?,\"oodtime\")) AS ENCRYPTION",password);
			String sql=Db.getSql("usersdao.encrypt");
			String encryption=Db.queryStr(sql, password);
			Record user=new Record().set("account",account).set("password",encryption);
			Db.save("users", user);
			bool=true;
		}
		return bool;
	}
	
	
	public Set<String> getSkills(){
		String sql=Db.getSql("usersdao.getSkills");
		List<Record> records=Db.find(sql);
		Set<String> ini=new HashSet<String>();
		Set<String> skills=new HashSet<String>();
		for(Record r:records) {
			String s=r.getStr("skills");
			if(s!=null) {
				ini.add(s.trim());
			}
		}
		for(String s:ini) {
		    skills.addAll(Arrays.asList(s.split(";")));
		}
		return skills;
	}
	
	public Set<Record> getMatchUsers(String skill){
		String regex=";"+skill+";";
		Pattern p=Pattern.compile(regex);
		Matcher m;
		String sql=Db.getSql("usersdao.getUsersBySkill");
		List<Record> records=Db.find(sql);
		Set<Record> match=new HashSet<Record>();
		for(Record r:records) {
			String s=r.getStr("skills");
			if(s!=null) {
				m=p.matcher(s);
				if(m.find()) {
					match.add(r);
				}
			}
		}
		return match;
	}
	
	public boolean setProfile(String account,String profile) {
		String sql=Db.getSql("usersdao.getUser");
		User user=new User().findFirst(sql, account);
		boolean bool=false;
		if(user!=null) {
			user.setPersonalProfile(profile).update();
			String p=user.getPersonalProfile();
			if(p!=null) {
				bool=p.equals(profile);
			}
		}
		return bool;
	}
	
	public boolean setSkills(String account,String skills) {
		String sql=Db.getSql("usersdao.getUser");
		User user=new User().findFirst(sql, account);
		boolean bool=false;
		if(user!=null) {
			user.setSkills(skills).update();
			String s=user.getSkills();
			if(s!=null) {
				bool=s.equals(skills);
			}
		}
		return bool;
	}
	
	public boolean setAvailableTime(String account,String availableTime) {
		String sql=Db.getSql("usersdao.getUser");
		User user=new User().findFirst(sql, account);
		boolean bool=false;
		if(user!=null) {
			user.setAvailableTime(availableTime).update();
			String a=user.getAvailableTime();
			if(a!=null) {
				bool=a.equals(availableTime);
			}
		}
		return bool;
	}
	
	public boolean setMoreInfo(String account,String sex,String address,String birthday,String job) {
		String sql=Db.getSql("usersdao.getUser");
		User user=new User().findFirst(sql, account);
		boolean bool=false;
		if(user!=null) {
			user.setSex(sex);
			user.setAddress(address);
			user.setBirthday(birthday);
			user.setJob(job);
			user.update();
			String s=user.getSex();
			String a=user.getAddress();
			String b=user.getBirthday();
			String j=user.getJob();
			if(s!=null&&a!=null&&b!=null&&j!=null) {
				bool=s.equals(sex)&&a.equals(address)&&b.equals(birthday)&&j.equals(job);
			}
		}
		return bool;
	}

}
