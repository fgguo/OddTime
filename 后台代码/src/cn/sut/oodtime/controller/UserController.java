package cn.sut.oodtime.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.sut.oodtime.service.TaskService;
import cn.sut.oodtime.service.UserService;
import cn.sut.oodtime.util.DataResponse;
import cn.sut.oodtime.util.DateUtil;
import cn.sut.oodtime.util.PasswordUtil;
import cn.sut.oodtime.common.model.Notification;
import cn.sut.oodtime.common.model.User;
import cn.sut.oodtime.interceptor.ValidateLoginStatusInterceptor;

@Clear(ValidateLoginStatusInterceptor.class)
public class UserController extends Controller{
	@ActionKey("/")
	public void index() {
		renderText("Hello!");
	}
	/*public void login1(){
		//获得url请求中的数据
		String account=getPara("account");
		String password=getPara("password");
		//执行系统登录
		boolean bool=usersService.login(account, password);
		setAttr("result",bool);
		if(bool) {
			setSessionAttr("account",account);
			setAttr("code",200);
			setAttr("message","succeed");
		}
		else {
			setAttr("code",200);
			setAttr("message","error");
		}
		//返回登录结果
		renderJson();
	}
	public void register1() {
		String account=getPara("account");
		String password=getPara("password");
		boolean bool=usersService.register(account, password);
		setAttr("result",bool);
		if(bool) {
			setAttr("code",200);
			setAttr("message","succeed");
		}
		else {
			setAttr("code",200);
			setAttr("message","error");
		}
		//返回注册结果
		renderJson();
	}*/
	@Before(Tx.class)
	public void register() {
		String account=getPara("account");
		String password=getPara("password");
		String username=getPara("netname");
		if (StringUtils.isEmpty(account)) {
			renderJson(new DataResponse("账号不可为空"));
			return;
		}
		if (StringUtils.isEmpty(password)) {
			renderJson(new DataResponse("密码不可为空"));
			return;
		}
		User user=UserService.checkUser(account);
		if(user!=null) {
			renderJson(new DataResponse("该账号已经存在"));
			return;
		}
		String salt= PasswordUtil.getSalt().toString();
		password = PasswordUtil.md5(password + salt);
		user=new User();
		// 生成tokenKey
	    String tokenKey = PasswordUtil.generalTokenKey();
	    //保存用户验证信息
	    user.setUsername(username);
	    user.setAccount(account);
	    user.setPassword(password);
	    user.setSalt(salt);
	    user.setTokenKey(tokenKey);
	    user.setTokenTime(DateUtil.addMonth(new Date(), 1));// token保存时间为1个月
	    user.setLoginTime(new Date());
        UserService.saveUser(user);
        renderJson(new DataResponse(tokenKey,""));
        return;
	}
	

	
	public void login() {
		final String account=getPara("account");
		String password=getPara("password");
		if (StringUtils.isEmpty(account)) {
			renderJson(new DataResponse("账号不可为空"));
			return;
		}
		if (StringUtils.isEmpty(password)) {
			renderJson(new DataResponse("密码不可为空"));
			return;
		}
		User user=UserService.checkUser(account);
		if(user==null) {
			renderJson(new DataResponse("该账号不存在"));
			return;
		}
		String salt = user.getSalt();// 获取用户盐
		password = PasswordUtil.md5(password + salt);
		if(!password.equals(user.getPassword())){
			renderJson(new DataResponse("密码不正确"));
			return;
		}
		// 更新tokenkey和token过期时间,用户登录时间
		String tokenKeyNew = PasswordUtil.generalTokenKey();
	    user.setTokenKey(tokenKeyNew);
		user.setTokenTime(DateUtil.addMonth(new Date(), 1));// token保存时间为1个月
	    user.setLoginTime(new Date());
		UserService.updateUser(user);
					
		renderJson(new DataResponse(tokenKeyNew,""));
	    return;
	}
	
	@Before(ValidateLoginStatusInterceptor.class)
	public void logOut(){
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		String account=user.getAccount();
		if (StringUtils.isEmpty(account)) {
			renderJson(new DataResponse("账号不可为空"));
			return;
		}
		
		User checkUser = UserService.checkUser(account);
		if(checkUser == null){
			renderJson(new DataResponse("不存在该用户"));
			return;
		}
		checkUser.setTokenKey("");
		checkUser.setTokenTime(null);
		UserService.updateUser(checkUser);
		
		renderJson(new DataResponse("退出登录成功",""));
		return;
	}
	
	@Before(ValidateLoginStatusInterceptor.class)
	public void getUserInfo() {
		String tokenKey=getPara("tokenkey");
		final User user=UserService.findUserByTokenKey(tokenKey);
		Map<String,Set<HashMap<String,String>>> result=null;
		/*if(user==null) {
			renderJson(new DataResponse("不存在该用户"));
			return;
		}else {
			result=UserService.packUserDetailInfo(user);
			renderJson(new DataResponse("查询成功",result));
			return;
		}*/
		result=UserService.packUserDetailInfo(user);
		if(result==null) {
			renderJson(new DataResponse("查询失败"));
			return;
		}
		renderJson(new DataResponse("查询成功",result));
		return;
	}
	
	@Before(ValidateLoginStatusInterceptor.class)
	public void matchSkills() {
		String fragment=getPara("fragment");
		Set<String> target=UserService.getTargetSkills(fragment);
		if(target.size()==0) {
			renderJson(new DataResponse("无相匹配的技能"));
			return;
		}else {
			renderJson(new DataResponse("查询成功",target));
			return;
		}
	}
	
	@Before(ValidateLoginStatusInterceptor.class)
	public void getUsersBySkill() {
		String skill=getPara("skill");
		Set<HashMap<String,String>> list=UserService.getMatchUsers(skill);
		if(list.size()==0) {
			renderJson(new DataResponse("无相匹配用户"));
			return;
		}else {
			Map<String,Object> result=new HashMap<String,Object>();
			result.put("bean", list);
			renderJson(new DataResponse("查询成功",result));
			return;
		}
	}
	
	@Before(ValidateLoginStatusInterceptor.class)
	public void modifyUserInfo() {
		boolean bool=false;
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		String account=user.getAccount();
		String profile=getPara("profile");
		String skills=getPara("skills");
		String availableTime=getPara("availableTime");
		String sex=getPara("sex");
		String address=getPara("address");
		String birthday=getPara("birthday");
		String job=getPara("job");
		String photo=getPara("iconId");
		if(account!=null) {
			if(profile!=null) {
				bool=user.setPersonalProfile(profile).update();
			}else if(skills!=null) {
				bool=user.setSkills(skills).update();
			}else if(availableTime!=null) {
				bool=user.setAvailableTime(availableTime).update();
			}else if(sex!=null&&address!=null&&birthday!=null&&job!=null) {
				bool=user.setSex(sex).setAddress(address).setBirthday(birthday).setJob(job).update();
			}else if(photo!=null) {
				bool=user.setPhotoPath(photo).update();
			}
		}
		if(bool==false) {
			renderJson(new DataResponse("修改失败"));
			return;
		}else {
			renderJson(new DataResponse("修改成功",""));
			return;
		}
	}
	
	@Before(ValidateLoginStatusInterceptor.class)
	public void getNotifications() {
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		int userid=user.getId();
		Set<HashMap<String,String>> notiflist= UserService.getNotifications(userid);
		if(notiflist.isEmpty()) {
			renderJson(new DataResponse("暂无消息"));
			return;
		}else {
			HashMap<String, Set<HashMap<String,String>>> result = new HashMap<String, Set<HashMap<String,String>>>();
			result.put("message", notiflist);
			renderJson(new DataResponse("获取成功",result));
			return;
		}
	}
	
	@Before(ValidateLoginStatusInterceptor.class)
	public void checkNotifications() {
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		int userid=user.getId();
		int newCount=UserService.getNewCount(userid);
		if(newCount>0) {
			renderText("1");
			return;
		}else {
			renderText("0");
			return;
		}
	}
	@Before(ValidateLoginStatusInterceptor.class)
	public void appointUserTime() {
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		String account0=user.getAccount();
		String account=getPara("account");
		User beAppointed=UserService.checkUser(account);
		if(beAppointed==null) {
			renderJson(new DataResponse("要预约的用户不存在"));
			return;
		}else {
			int appointedId=beAppointed.getId();
			String sql=Db.getSql("usersdao.isAppointed");
			if(!Notification.dao.find(sql, appointedId).isEmpty()) {
				renderJson(new DataResponse("要预约的用户已经被预约过"));
				return;
			}
			new Notification().set("user_id", appointedId).set("notif_details", "您的零时已经被用户"+account0+"成功预约").set("sort", "3").save();
			renderJson(new DataResponse("成功预约","ok"));
			return;
		}
		
	}
	

}
