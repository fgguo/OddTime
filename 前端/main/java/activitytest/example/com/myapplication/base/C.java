package activitytest.example.com.myapplication.base;

public final class C {
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// core settings (important)
	
	public static final class dir {
		public static final String base				= "/sdcard/demos";
		public static final String faces			= base + "/faces";
		public static final String images			= base + "/images";
	}
	
	public static final class api {
		// 建议换成你的内网IP的地址
		public static final String register1         ="/oddTime/time/register";
		public static final String base				= "http://192.168.43.226:8080";
		public static final String login1			= "/HelloSpringMVC/time/login";
		public static final String posttask1        = "/HelloSpringMVC/time/offerreward";
		public static final String getbasictask1     = "/HelloSpringMVC/time/getreward";
		public static final String getSearchResult  ="/demo4/users/getUsersBySkill";/* 上传技能名，返回用户信息接口*/
		public static final String getskillname     ="http://192.168.43.226:8080/demo4/users/matchSkills";/*上传关键字，在技能中搜索匹配项并返回技能名*/
		public static final String getuser          ="/demo4/users/getUserInfo";/*上传用户名，返回用户信息*/
		public static final String modifyuser       ="/demo4/users/modifyUserInfo";/*修改用户信息，返回成功与否*/
		public static final String login            ="/demo4/users/login";/*登录*/
		public static final String logout            ="/demo4/users/logOut";/*登出*/
		public static final String register         ="/demo4/users/register";/*注册*/
		public static final String getbasictask    ="/demo4/Tasks/getTasks";/*获取任务列表*/
		public static final String posttask       ="/demo4/Tasks/publishTasks";/*发布任务*/
		public static final String takeTask         ="/demo4/Tasks/takeTask";/*领取任务*/
		public static final String getNotifications ="/demo4/users/getNotifications";/*获取消息列表*/
		public static final String checkNotifications="http://192.168.43.226:8080/demo4/users/checkNotifications";/*检查消息*/
		public static final String getPublishTasks ="/demo4/Tasks/getPublishTasks";
		public static final String getRecieveTasks ="/demo4/Tasks/getReceiveTasks";


	}
	
	public static final class task {
		public static final int register            = 1000;
		public static final int index				= 1001;
		public static final int login				= 1002;
		public static final int searchResult        =1019;
		public static final int getSkillname        =1018;
		public static final int getuser             =1020;
		public static final int logout				= 1003;
		public static final int faceView			= 1004;
		public static final int faceList			= 1005;
		public static final int blogList			= 1006;
		public static final int blogView			= 1007;
		public static final int blogCreate			= 1008;
		public static final int commentList			= 1009;
		public static final int commentCreate		= 1010;
		public static final int customerView		= 1011;
		public static final int customerEdit		= 1012;
		public static final int fansAdd				= 1013;
		public static final int fansDel				= 1014;
		public static final int notice				= 1015;
		public static final int posttask            = 1016;
		public static final int getbasictask        = 1017;
	}
	
	public static final class err {
		public static final String network			= "网络错误";
		public static final String message			= "消息错误";
		public static final String jsonFormat		= "网络错误";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// intent & action settings
	
	public static final class intent {
		public static final class action {
			public static final String EDITTEXT		= "com.app.demos.EDITTEXT";
			public static final String EDITBLOG		= "com.app.demos.EDITBLOG";
		}
	}
	
	public static final class action {
		public static final class edittext {
			public static final int CONFIG			= 2001;
			public static final int COMMENT			= 2002;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// additional settings
	
	public static final class web {
		// 建议换成你的内网IP的地址
		public static final String base				= "http://192.168.20.21:8080";
//		public static final String base				= "http://10.0.2.2:8002";
		public static final String index			= base + "/index.php";
		public static final String gomap			= base + "/gomap.php";
	}
}