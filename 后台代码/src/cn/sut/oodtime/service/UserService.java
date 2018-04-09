package cn.sut.oodtime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.sut.oodtime.common.model.Notification;
import cn.sut.oodtime.common.model.User;

public class UserService {
	
    public static User checkUser(String account) {
    	String sql=Db.getSql("usersdao.getUser");
    	User user=User.dao.findFirst(sql,account);
    	return user;
    }
    
    public static User findUserByTokenKey(String tokenKey) {
    	String sql=Db.getSql("usersdao.getUserByTokenkey");
    	User user=User.dao.findFirst(sql, tokenKey);
    	return user;
    }
    
    public static boolean saveUser(User user) {
    	return user.save();
    }
    
    public static boolean updateUser(User user) {
    	return user.update();
    }
    /*
	public boolean login(String account,String password) {
		boolean bool=dao.login(account, password);
		return bool;
	}
	public boolean register(String account,String password) {
		boolean bool=dao.register(account,password);
		return bool;
	}*/
    
    public static Map<String,Set<HashMap<String,String>>> packUserDetailInfo(User user){
    	Map<String,Set<HashMap<String,String>>> pack=new HashMap<String,Set<HashMap<String,String>>>();
    	HashMap<String,String> userInfo=new HashMap<String,String>();
    	userInfo.put("netname", user.getUsername()==null?"":user.getUsername());
		userInfo.put("photonumber", user.getPhotoPath()==null?"":user.getPhotoPath());
		userInfo.put("brief", user.getPersonalProfile()==null?"":user.getPersonalProfile());
		userInfo.put("cradit", user.getReputationValue()==null?"":user.getReputationValue().toString());
		userInfo.put("lingshi",user.getAvailableTime()==null?"":user.getAvailableTime());
		String arr=user.getSkills();
		ArrayList<String> skills;
		if(arr!=null) {
			skills=new ArrayList<String>(Arrays.asList(arr.split(";")));
		}else {
			skills=new ArrayList<String>();
		}
		for(int i=skills.size();i<4;++i) {
			skills.add(i, "");
		}
		userInfo.put("skill1", skills.get(0));
		userInfo.put("skill2", skills.get(1));
		userInfo.put("skill3", skills.get(2));
		userInfo.put("skill4", skills.get(3));
		userInfo.put("sex", user.getSex()==null?"":user.getSex());
		userInfo.put("location", user.getAddress()==null?"":user.getAddress());
		userInfo.put("work", user.getJob()==null?"":user.getJob());
		userInfo.put("birthday", user.getBirthday()==null?"":user.getBirthday());
		Set<HashMap<String,String>> result=new HashSet<HashMap<String,String>>();
		result.add(userInfo);
		pack.put("user", result);
		return pack;
    }
    
	private static Set<String> getAllSkill(){
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
    
	public static Set<String> getTargetSkills(String fragment){
		String regex=".*"+fragment+".*";
		Set<String> source=getAllSkill();
		Set<String> target=new HashSet<String>();
		for(String s:source) {
			if(Pattern.matches(regex, s)) {
				target.add(s);
			}
		}
		return target;
	}
	
	private static Set<Record> getUsersBySkill(String skill){
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
	
	public static Set<HashMap<String,String>> getMatchUsers(String skill){
		Set<Record> ini=getUsersBySkill(skill);
		Set<HashMap<String, String>> result=new HashSet<HashMap<String,String>>();
		HashMap<String,String> m;
		for(Record r:ini) {
			m=new HashMap<String,String>();
		    m.put("username", r.getStr("username")==null?"":r.getStr("username"));
		    m.put("iconIdget", r.getStr("photo_path")==null?"":r.getStr("photo_path"));
		    m.put("usertime", r.getStr("available_time")==null?"":r.getStr("available_time"));
		    m.put("userskill", skill);
			result.add(m);
		}
		return result;
	}
	
/*	public static boolean setProfile(String account,String profile) {
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
	
	public static boolean setSkills(String account,String skills) {
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
	
	public static boolean setAvailableTime(String account,String availableTime) {
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
	
	public static boolean setMoreInfo(String account,String sex,String address,String birthday,String job) {
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
*/	
	public static Set<HashMap<String,String>> getNotifications(int userid){
		String sql=Db.getSql("usersdao.getNotifications");
	    List<Notification> notifs=Notification.dao.find(sql,userid);
	    Set<HashMap<String,String>> notiflist=new HashSet<HashMap<String,String>>();
	    if(!notifs.isEmpty()) {
	    	for(Notification n:notifs) {
	    		HashMap<String,String> notif=new HashMap<String,String>();
	    		notif.put("specific", n.getNotifDetails()==null?"":n.getNotifDetails());
	    		notif.put("mType", n.getSort()==null?"":n.getSort());
	    		notiflist.add(notif);
	    		n.setNoSend(0);
	    	}
	    }
	    return notiflist;
	}
		
	public static int getNewCount(int userid) {
		String sql=Db.getSql("usersdao.getNewCount");
		Integer newCount=Db.queryInt(sql, userid);
		if(newCount!=null) {
			return newCount;
		}else {
			return 0;
		}	
	}

}
