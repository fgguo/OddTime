package cn.sut.oodtime.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRelationship<M extends BaseRelationship<M>> extends Model<M> implements IBean {

	public M setRelationId(java.lang.Integer relationId) {
		set("relation_id", relationId);
		return (M)this;
	}

	public java.lang.Integer getRelationId() {
		return get("relation_id");
	}

	public M setUserId(java.lang.Integer userId) {
		set("user_id", userId);
		return (M)this;
	}

	public java.lang.Integer getUserId() {
		return get("user_id");
	}

	public M setTaskId(java.lang.Integer taskId) {
		set("task_id", taskId);
		return (M)this;
	}

	public java.lang.Integer getTaskId() {
		return get("task_id");
	}

	public M setRelationship(java.lang.String relationship) {
		set("relationship", relationship);
		return (M)this;
	}

	public java.lang.String getRelationship() {
		return get("relationship");
	}

}
