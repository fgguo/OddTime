package cn.sut.oodtime.interceptor;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import cn.sut.oodtime.common.model.User;
import cn.sut.oodtime.service.UserService;
import cn.sut.oodtime.util.DataResponse;

/**
 * 验证登录状态 全局拦截器
 * @className: ValidateLoginStatusInterceptor
 * @author sangyue
 * @date Jun 15, 2017 12:58:16 AM
 * @version V1.0
 */
public class ValidateLoginStatusInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller con = inv.getController();
		// 验证是否登录
		
		//Map<String,String[]> tt = con.getRequest().getParameterMap();
		//String tokenKey=tt.get("tokenKey")[0];
		//System.out.println("requestdate:"+tt.toString());//获取post请求的参数之前调用
		//HttpKit.readData(con.getRequest());
		String tokenKey = con.getPara("tokenkey");
		Date tokenTime = null;
		if(StringUtils.isEmpty(tokenKey)){
			con.renderJson(new DataResponse("ValidateInterceptor验证不通过，请传入tokenKey"));
			return;
		}else{
			// 根据该tokenKey查询相应userAuth信息
			User user = UserService.findUserByTokenKey(tokenKey);
			if(user == null){
				con.renderJson(new DataResponse("ValidateInterceptor验证不通过，tokenKey已失效或不正确，请重新登录"));
				return;
			}else{
				tokenTime = user.getTokenTime();
				if(tokenTime.before(new Date())){// token失效时间小于当前时间，代表已经失效
					con.renderJson(new DataResponse("ValidateInterceptor验证不通过，tokenKey已失效，请重新登录"));
					return;
				}
			}
		}
	
		inv.invoke();
	}
}
