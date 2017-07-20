package com.tasklist.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasklist.model.Task;

@Path("/task")
public class TaskResource {
	private Map<Long, Task> lista = new HashMap<Long, Task>();
	@GET
	@Produces("application/json")
	public Response getInfractions() {
		ObjectMapper mapper = new ObjectMapper();
		Task task = new Task();
		task.setDescricao("tarefa1");
		lista.put(new Long(1), task);
		task.setDescricao("tarefa2");
		lista.put(new Long(2), task);
		String jsonInString;
		try {
			jsonInString = mapper.writeValueAsString(lista.values());
		} catch (JsonProcessingException e) {
			return Response
					.ok("Error: Ao buscar lista:  => " + e.getMessage())
					.build();
		}
		ResponseBuilder builder = Response.ok(jsonInString);
		return builder.build();
	}

}
