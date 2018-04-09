package cn.sut.oodtime.service;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.sut.oodtime.common.model.Relationship;
import cn.sut.oodtime.common.model.Task;
import cn.sut.oodtime.common.model.User;

public class TaskService {
	
	public static boolean publishTask(int userid,String name, String money, String time, String detail, String photo) {
	    try {
		    Record task = new Record().set("task_name", name).set("reward_amount", money).set("time_consuming", time)
				          .set("task_details", detail).set("photo_path", photo);
	        Db.save("task", task);
	        int a=task.getInt("id");
	        Record relationship=new Record().set("user_id", userid).set("task_id",a).set("relationship","发布");
	        Db.save("relationship", relationship);
	    }catch(Exception e) {
		    e.printStackTrace();
		    return false;
	    }
	    return true;
	}

	@Before(Tx.class)
	public static boolean takeTask(int taskid,int userid) {
		String sql=Db.getSql("tasksdao.countTask");
		String sql1=Db.getSql("tasksdao.checkTakeMan");
		Relationship a=new Relationship().findFirst(sql1,taskid);
		if(a==null) {
			return false;
		}
		List<Record> taskidcount = Db.find(sql, taskid);
		if(taskidcount.size()!=0) {
			return false;
		}
		else {
			Record relationship=new Record().set("user_id", userid).set("task_id", taskid).set("relationship", "领取");
			Db.save("relationship", relationship);
			Record user0=Db.findById("user", userid);
			int reputation=user0.get("reputation_value");
			reputation+=100;
		    user0.set("reputation_value",reputation);
		    Db.update("user", user0);
			String sql2=Db.getSql("tasksdao.findTaskName");
			Task task=new Task().findFirst(sql2,taskid);
			String taskname=task.getStr("task_name");
			Record message1=new Record().set("user_id", userid).set("notif_details", "成功领取任务"+taskname).set("sort", "1");
			Db.save("notification", message1);
			String sql3=Db.getSql("tasksdao.findUser");
			User user=new User().findFirst(sql3,userid);
			String username=user.getStr("username");
			String sql4=Db.getSql("tasksdao.findHost");
			Relationship b=new Relationship().findFirst(sql4,taskid);
		    int hostid=b.getInt("user_id");
			Record message2=new Record().set("user_id",hostid).set("notif_details", "您的任务被"+username+"领取").set("sort","2");
			Db.save("notification", message2);
		   return true;
		}
		
	}
	
	public static Set<HashMap<String,String>> getTasks(String sqlkey) {
		String sql=Db.getSql(sqlkey);
		List<Task> tasks=Task.dao.find(sql);
		Set<HashMap<String,String>> tasklist=new HashSet<HashMap<String,String>>();
		if(!tasks.isEmpty()) {
			for(Task t:tasks) {
				HashMap<String,String> task=new HashMap<String,String>();
				task.put("id", t.getId()==null?"":t.getId().toString());
				task.put("name", t.getTaskName()==null?"":t.getTaskName());
				task.put("description", t.getTaskDetails()==null?"":t.getTaskDetails());
				task.put("paying", t.getRewardAmount()==null?"":t.getRewardAmount());
				task.put("time", t.getTimeConsuming()==null?"":t.getTimeConsuming());
				task.put("picture", t.getPhotoPath()==null?"":t.getPhotoPath());
				tasklist.add(task);
			}
		}
		return tasklist;
	}

	public static Set<HashMap<String,String>> getPersonTasks(String sqlkey,int userid) {
		String sql=Db.getSql(sqlkey);
		List<Task> tasks=Task.dao.find(sql,userid);
		Set<HashMap<String,String>> tasklist=new HashSet<HashMap<String,String>>();
		if(!tasks.isEmpty()) {
			for(Task t:tasks) {
				HashMap<String,String> task=new HashMap<String,String>();
				task.put("id", t.getId()==null?"":t.getId().toString());
				task.put("name", t.getTaskName()==null?"":t.getTaskName());
				task.put("description", t.getTaskDetails()==null?"":t.getTaskDetails());
				task.put("paying", t.getRewardAmount()==null?"":t.getRewardAmount());
				task.put("time", t.getTimeConsuming()==null?"":t.getTimeConsuming());
				task.put("picture", t.getPhotoPath()==null?"":t.getPhotoPath());
				tasklist.add(task);
			}
		}
		return tasklist;
	}

	

}
