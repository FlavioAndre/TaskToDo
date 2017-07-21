package com.tasklist.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tasklist.model.TaskListVO;
import com.tasklist.model.TaskVO;

@Controller
public class TaskRestController {
	
	private static TaskListVO tasks = new TaskListVO();
	
	private TaskRestController() {
		TaskVO task1 = new TaskVO(1L, "tarefa 1", "N", "Executar tarefa1", new Date(), new Date(), new Date(), new Date());
		TaskVO task2 = new TaskVO(2L, "tarefa 2", "N", "Executar tarefa2", new Date(), new Date(), new Date(), new Date());
		TaskVO task3 = new TaskVO(3L, "tarefa 3", "N", "Executar tarefa3", new Date(), new Date(), new Date(), new Date());
		TaskVO task4 = new TaskVO(4L, "tarefa 4", "N", "Executar tarefa4", new Date(), new Date(), new Date(), new Date());
		TaskVO task5 = new TaskVO(5L, "tarefa 5", "N", "Executar tarefa5", new Date(), new Date(), new Date(), new Date());

		tasks.getTasks().add(task1);
		tasks.getTasks().add(task2);
		tasks.getTasks().add(task3);
		tasks.getTasks().add(task4);
		tasks.getTasks().add(task5);

	}

	private TaskVO _getTaskById(int id){
        for(TaskVO e : tasks.getTasks()){
            if(e.id == id){
                return e;
            }
        }
        return null;
    }
	
	 /**
     * HTTP GET - Get all tasks
     * */
	@CrossOrigin
	@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE,   method = RequestMethod.GET)
	public ResponseEntity<TaskListVO> getAllTasksJSON() 
    {
        return new ResponseEntity<TaskListVO>(tasks, HttpStatus.OK);
    }

	/**
     * HTTP GET - Get task by id
     * */
	@CrossOrigin
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public ResponseEntity<TaskVO> getTask(@PathVariable("id") int id) 
    {
        TaskVO task = _getTaskById(id);
        if(task != null){
            return new ResponseEntity<TaskVO>(task, HttpStatus.OK);
        }
        return new ResponseEntity<TaskVO>(HttpStatus.NOT_FOUND);
    }
	/**
     * HTTP POST - Create new Tasks
     * */
	@CrossOrigin
    @RequestMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> createEmployee(@RequestBody TaskVO task) 
    {
    	task.id = (tasks.getTasks().size() + 1l);
        tasks.getTasks().add(task);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
    
    /**
     * HTTP PUT - Update task
     * */
	@CrossOrigin
    @RequestMapping(value = "/tasks/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<TaskVO> updateTask(@PathVariable("id") int id, @RequestBody TaskVO task) 
    {
        TaskVO taskTemp = _getTaskById(id);
        if(taskTemp != null){
            taskTemp.id = task.id;
            taskTemp.descricao = task.descricao;
            taskTemp.titulo = task.titulo;
            taskTemp.status = task.status;
            taskTemp.setEdicao(task.getEdicao());
            if(task.status.equalsIgnoreCase("S")){
            	taskTemp.setConclusao(task.getConclusao());
            }
            return new ResponseEntity<TaskVO>(taskTemp, HttpStatus.OK);
        }
        return new ResponseEntity<TaskVO>(HttpStatus.NOT_FOUND);
    }
	
    /**
     * HTTP DELETE - Delete task
     * */
	@CrossOrigin
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTask(@PathVariable("id") int id) 
    {
        TaskVO task = _getTaskById(id);
        if(task != null){
            tasks.getTasks().remove(task);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
}