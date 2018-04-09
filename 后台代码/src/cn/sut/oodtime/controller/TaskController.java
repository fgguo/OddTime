package cn.sut.oodtime.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;

import cn.sut.oodtime.common.model.User;
import cn.sut.oodtime.interceptor.ValidateLoginStatusInterceptor;
import cn.sut.oodtime.service.TaskService;
import cn.sut.oodtime.service.UserService;
import cn.sut.oodtime.util.DataResponse;
@Clear(ValidateLoginStatusInterceptor.class)
public class TaskController extends Controller {

	public void publishTasks() {
		UploadFile imageFile = getFile();
		String photo = PropKit.get("imagePath")+PropKit.get("baseUploadPath")+"/";
		if(imageFile==null) {
			renderJson(new DataResponse("发布失败,图片未上传成功"));
			return;
		}else {
			// 重命名文件名
			try {
				String filePath = imageFile.getUploadPath() + "/" + imageFile.getFileName();
				String imageNewName = FilenameUtils.getBaseName(filePath) + System.currentTimeMillis() + "." + FilenameUtils.getExtension(filePath);
				imageFile.getFile().renameTo(new File(imageFile.getUploadPath() + "/" + imageNewName));
				photo+=imageNewName;
			} catch (Exception e) {
				e.printStackTrace();
				renderJson(new DataResponse("发布失败,图片未上传成功"));
				throw e;
			}
		}
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		int userid=user.getId();
		String name = getPara("name");
		String time = getPara("time");
		String money = getPara("money");
		String detail = getPara("detail");
		boolean bool=false;
		if(name!=null&&time!=null&&money!=null&&detail!=null&&photo!=null) {
			bool = TaskService.publishTask(userid, name, money, time, detail, photo);
		}
		if (bool) {
			renderJson(new DataResponse("发布成功",""));
			return;
		} else {
			renderJson(new DataResponse("发布失败"));
			return;
		}
	}

	@SuppressWarnings("static-access")
	public void takeTask() {
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		int userid=user.getId();
		Integer taskid = getParaToInt("taskid");
		boolean bool=false;
		if(taskid!=null) {
			TaskService service=enhance(TaskService.class);
			bool = service.takeTask(taskid, userid);
		}
		if (bool) {
			renderJson(new DataResponse("领取成功",""));
			return;
		} else {
			renderJson(new DataResponse("领取失败"));
			return;
		}
	}

	public void getTasks() {
		Set<HashMap<String,String>> tasklist= TaskService.getTasks("tasksdao.getTasks");
		if (tasklist.isEmpty()) {
			renderJson(new DataResponse("暂无任务"));
			return;
		}else {
			HashMap<String, Set<HashMap<String,String>>> result = new HashMap<String, Set<HashMap<String,String>>>();
			result.put("offer", tasklist);
			renderJson(new DataResponse("获取成功",result));
			return;
		}
	}

	
	public void getPublishTasks() {
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		int userid=user.getId();
		Set<HashMap<String,String>> tasklist= TaskService.getPersonTasks("tasksdao.getPublishTasks",userid);
		if (tasklist.isEmpty()) {
			renderJson(new DataResponse("200","获取成功",""));
			return;
		}else {
			HashMap<String, Set<HashMap<String,String>>> result = new HashMap<String, Set<HashMap<String,String>>>();
			result.put("offer", tasklist);
			renderJson(new DataResponse("获取成功",result));
			return;
		}
	}
	
	public void getReceiveTasks() {
		String tokenKey=getPara("tokenkey");
		User user=UserService.findUserByTokenKey(tokenKey);
		int userid=user.getId();
		Set<HashMap<String,String>> tasklist= TaskService.getPersonTasks("tasksdao.getReceiveTasks",userid);
		if (tasklist.isEmpty()) {
			renderJson(new DataResponse("200","获取成功",""));
			return;
		}else {
			HashMap<String, Set<HashMap<String,String>>> result = new HashMap<String, Set<HashMap<String,String>>>();
			result.put("offer", tasklist);
			renderJson(new DataResponse("获取成功",result));
			return;
		}
	}
	

/*	
	// @Clear(ValidateLoginStatusInterceptor.class)
	public void uploadImage() {
		UploadFile imageFile = getFile();
		if(imageFile == null){
			setAttr("code", 500);
			setAttr("result", "");
			setAttr("message", "error");
			renderJson();
		}else {
		// 重命名文件名
		try {
			String filePath = imageFile.getUploadPath() + "/" + imageFile.getFileName();
			String imageNewName = FilenameUtils.getBaseName(filePath) + System.currentTimeMillis() + "." + FilenameUtils.getExtension(filePath);
			imageFile.getFile().renameTo(new File(imageFile.getUploadPath() + "/" + imageNewName));
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("imageNewName", imageNewName);
			resultMap.put("imagePath", filePath);
			setAttr("code", 200);
			setAttr("result", resultMap);
			setAttr("message", "succeed");
			renderJson();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			this.renderJson();
			throw e;
		}
	}
    }
	/* @Clear(ValidateLoginStatusInterceptor.class)
		public void uploadImage() {
	 String fileName = "";  
     String extName = "";  
     boolean flag = false;  
     String msg = "";
     File f=null;
     try {  
         String path = getSession().getServletContext().getRealPath("/")  
                 + "img/";  
         List<UploadFile> uf = getFiles(path);  
         File file = uf.get(0).getFile();
         f=file;
         fileName = file.getName();  
         extName = fileName.substring(fileName.lastIndexOf(".") + 1);  
         SimpleDateFormat dateFormat = new SimpleDateFormat(  
                 "yyyyMMddHHmmss");  
         int temp = (int) (Math.random() * 9000 + 1000);  
         fileName = getRequest().getRemoteAddr().replaceAll(":", "")  
                 + dateFormat.format(new Date())  
                 + new Integer(temp).toString() + "." + extName;//对文件进行重命名，防治文件重名。命名的规则是使用上传客户端的ip+上传时间+四位随机数的方法。  
         file.renameTo(new File(path + fileName));  
         flag = true;  
         msg = "上传成功";  
     }  
     catch (Exception e) {  
         msg = "网络超时，请重试。";  
     }
     renderJson(new DataResponse(msg,f));
	return;
	 }
	/* @Clear(ValidateLoginStatusInterceptor.class)
	public void uploadImage() { 
		  final int MAXSize = 50 * 1024 * 1024; // 50M
		  String filedir=PathKit.getWebRootPath() + "\\upload\\uservideo\\";//指定用户训练视频文件上传路径
		 
		  // TODO Auto-generated method stub
        try {
//          UploadFile upFile = getFile();//单个上传文件一句搞定  默认路径是 upload
//          UploadFile upFile = getFile("FILE", filedir, maxSize, "utf-8");//只用于表单提交方式， 单个上传文件
            List<UploadFile> upFiles = getFiles("d:/fileUpload/", MAXSize, "utf-8");//批量上传文件	            
            for (UploadFile fileItem : upFiles) {
                 
                String fPath=filedir+fileItem.getOriginalFileName();
                System.out.println("上传fPath"+fPath);
                String newPath=filedir+fileItem.getOriginalFileName().replace(".", "1.");//例如：交叉接触动作1.3gp
                File oldFile=new File(fPath);
                File newFile=new File(newPath);
                if(newFile.exists()){
                    System.out.println("-------------删除"+fileItem.getOriginalFileName());
                    //删除旧的
                    oldFile.delete();
                    //新的重命名
                    boolean updateName=newFile.renameTo(oldFile);
                    System.out.println("-------------updateName:"+updateName);
                     
                }
                 
            }
             
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            renderJson("status", "0");//失败
        }
        renderJson("status", "1");//成功
 
	}  */

}
