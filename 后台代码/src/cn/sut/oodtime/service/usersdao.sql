#sql("login")
SELECT
  COUNT(*)
FROM user
WHERE
  account=? AND
  password=HEX(AES_ENCRYPT(?,"oodtime"));
#end

#sql("encrypt")
SELECT HEX(AES_ENCRYPT(?,"oodtime")) AS ENCRYPTION;
#end

#sql("getUser")
SELECT *
FROM user
WHERE account=?;
#end

#sql("getSkills")
SELECT skills
FROM user;
#end

#sql("getUsersBySkill")
SELECT username,photo_path,skills,available_time
FROM user;
#end

#sql("getUserByTokenkey")
SELECT *
FROM user
WHERE tokenKey=?;
#end

#sql("getNewCount")
SELECT COUNT(*)
FROM notification
WHERE user_id=? AND no_send!=0;
#end

#sql("getNotifications")
select *from notification where user_id=?;
#end

#sql("isAppointed")
SELECT * 
FROM notification
WHERE user_id=? AND sort=3;
#end

