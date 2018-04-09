package demo;

import java.util.List;
import com.jfinal.plugin.activerecord.Record;

import cn.sut.oodtime.common.model.Notification;
import cn.sut.oodtime.common.model.Relationship;
import cn.sut.oodtime.common.model.Task;
import cn.sut.oodtime.common.model.User;

import com.jfinal.plugin.activerecord.Db;
public class TasksDao {
	public boolean publishTask(int userid,String name, String money, String time, String detail, String photo) {
	    try {
		    Record task = new Record().set("task_name", name).set("reward_amount", money).set("time_consuming", time)
				          .set("task_details", detail).set("photo_path", photo);
            Db.save("task", task);
            int a=task.getInt("id");
            System.out.println("a="+a);
            Record relationship=new Record().set("user_id", userid).set("task_id",a).set("relationship","发布");
            Db.save("relationship", relationship);
        }catch(Exception e) {
		    e.printStackTrace();
		    return false;
	    }
	    return true;
	}

	public boolean takeTask(int taskid,int userid) {
		String sql=Db.getSql("tasksdao.countTask");
		String sql1=Db.getSql("tasksdao.checkTakeMan");
		Relationship a=new Relationship().findFirst(sql1,userid);
		if(a.getInt("task_id")==taskid) {
			return false;
		}
		List<Record> taskidcount = Db.find(sql, taskid);
		if(taskidcount.size()!=0) {
			return false;
		}
		else {
			Record relationship=new Record().set("user_id", userid).set("task_id", taskid).set("relationship", "领取");
			Db.save("Relationships", relationship);
			String sql2=Db.getSql("tasksdao.findTaskName");
			Task task=new Task().findFirst(sql2,taskid);
			String taskname=task.getStr("task_name");
			Record message1=new Record().set("user_id", userid).set("notif_details", "成功领取任务"+taskname).set("sort", "1");
			Db.save("Notifications", message1);
			String sql3=Db.getSql("tasksdao.findUser");
			User user=new User().findFirst(sql3,userid);
			String username=user.getStr("username");
			String sql4=Db.getSql("tasksdao.findHost");
			Relationship b=new Relationship().findFirst(sql4,taskid);
		    int hostid=b.getInt("user_id");
			Record message2=new Record().set("user_id",hostid).set("notif_details", "您的任务被"+username+"领取").set("sort","2");
			Db.save("Notifications", message2);
		   return true;
		}
		
	}

   public List<Notification> getNotifications(int userid){
	   String sql=Db.getSql("tasksdao.getNotifications");
		List<Notification> notifications=Notification.dao.find(sql,userid);
		return notifications;
   }
  
	public List<Task> getTasks() {
		String sql=Db.getSql("tasksdao.getTasks");
		List<Task> tasks=Task.dao.find(sql);
		return tasks;
	}

}
