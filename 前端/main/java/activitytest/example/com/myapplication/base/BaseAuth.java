package activitytest.example.com.myapplication.base;

import activitytest.example.com.myapplication.entity.User;

public class BaseAuth {
	
	static public boolean isLogin () {
		User user = User.getInstance();
		if (user.getLogin() == true) {
			return true;
		}
		return false;
	}
	static public boolean isRegister () {
		User user = User.getInstance();
		if (user.getRegister() == true) {
			return true;
		}
		return false;
	}
	static public void setLogin (Boolean status) {
		User user = User.getInstance();
		user.setLogin(status);
	}
	static public void setRegister (Boolean status) {
		User user = User.getInstance();
		user.setRegister(status);
	}
	static public void setUaer (User mc) {
		User user = User.getInstance();
		user.setName(mc.getName());
	}
	
	static public User getUser () {
		return User.getInstance();
	}
}