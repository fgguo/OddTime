package cn.sut.oodtime.config;


import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import cn.sut.oodtime.common.model._MappingKit;
import cn.sut.oodtime.controller.TaskController;
import cn.sut.oodtime.controller.UserController;
import cn.sut.oodtime.interceptor.ValidateLoginStatusInterceptor;
//后台Jfinal配置
public class SystemConfig extends JFinalConfig {
	public static DruidPlugin createDruidPlugin() {
		PropKit.use("a_little_config.properties");
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

	@Override
	public void configConstant(Constants constants) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
	    PropKit.use("a_little_config.properties");
	    constants.setDevMode(PropKit.getBoolean("devMode", false));
	    constants.setBaseUploadPath(PropKit.get("baseUploadPath"));
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configHandler(Handlers arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new ValidateLoginStatusInterceptor());

	}

	@Override
	public void configPlugin(Plugins plugins) {
		DruidPlugin druidPlugin = createDruidPlugin();
		druidPlugin.setDriverClass(JdbcConstants.MARIADB_DRIVER);// 数据库驱动类
		druidPlugin.addFilter(new StatFilter()); // 连接池开启状态监控
		druidPlugin.setInitialSize(10); // 初始连接数
		druidPlugin.setMaxActive(20);// 最大活动连接数
		druidPlugin.setMinIdle(10);// 最小空闲连接数
		druidPlugin.setValidationQuery("SELECT 1"); // 用于执行检测的SQL语句
		druidPlugin.setTestOnReturn(false);// 归还给连接池的时候不检测连接
		druidPlugin.setTestOnBorrow(true); // 使用连接之前检测连接
		druidPlugin.setMaxPoolPreparedStatementPerConnectionSize(50); // 每个连接可以缓存多少个PreparedStatement
//数据库防火墙（拦截恶意SQL）
		WallFilter wall = new WallFilter();
		wall.setDbType(JdbcConstants.MARIADB);
		WallConfig config = new WallConfig();
		config.setStrictSyntaxCheck(false); // 关闭druid的SQL解析功能，这个功能会导致全局主键的失效
		wall.setConfig(config);
		druidPlugin.addFilter(wall);

		plugins.add(druidPlugin); // 向JFinal注册数据库连接池插件

		/*
		 * 持久层插件（替代JDBC）
		 */
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setBaseSqlTemplatePath(PathKit.getRootClassPath() + "/cn/sut/oodtime/service"); // SQL文件目录
		// 注册SQL文件
		arp.addSqlTemplate("all.sql");
		// 所有映射在 MappingKit 中自动化搞定
	    _MappingKit.mapping(arp);


		plugins.add(arp); // 向JFinal注册持久层插件
		

	}

	@Override
	public void configRoute(Routes routes) {
		//routes.add("/", IndexController.class, "/index");
		routes.add("/users",UserController.class);
		routes.add("/Tasks",TaskController.class);
	}

}
