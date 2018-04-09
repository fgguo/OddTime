#sql("getTasks")
select * from task where id in(
SELECT task_id
FROM relationship
group by task_id
having COUNT(*) =1);
#end

#sql("countTask")
select * from task where id in(
SELECT task_id
FROM relationship
where task_id=?
group by task_id
having COUNT(task_id) =2);
#end

#sql("checkTakeMan")
select * from relationship where task_id=? and relationship="发布";
#end

#sql("findTaskName")
select * from task where id=?;
#end

#sql("findUser")
select *from user where id=?;
#end
	
	#sql("getPublishTasks")
SELECT * 
FROM task
WHERE id IN(
SELECT task_id
FROM relationship
WHERE user_id=? AND relationship="发布");
#end


#sql("getReceiveTasks")
SELECT * 
FROM task
WHERE id IN(
SELECT task_id
FROM relationship
WHERE user_id=? AND relationship="领取");
#end


#sql("getTaskHost")
select *from notification where user_id=? and relationship="li";
#end

#sql("findHost")
select * from relationship where task_id=? and relationship="发布";
#end



