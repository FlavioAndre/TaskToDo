package com.tasklist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="tasks")
public class TaskListVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<TaskVO> tasks = new ArrayList<TaskVO>();
	 
    public List<TaskVO> getTasks() {
        return tasks;
    }
 
    public void setTaks(List<TaskVO> tasks) {
        this.tasks = tasks;
    }

}
