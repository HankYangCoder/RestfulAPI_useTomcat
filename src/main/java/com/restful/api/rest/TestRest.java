package com.restful.api.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("test")
public class TestRest extends ApiRest{
	
//	Gson gson ;
	
	public TestRest() {
//		gson = new Gson();
	}

	@POST
	@Path("retCall")
	@Produces({MediaType.APPLICATION_JSON})
	public Response retCall(@QueryParam("input") String input)
	{
		System.out.println("rrrrrrrrrrrrrrr");
		System.out.println("input="+input);
		String ret = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("return", input);
		ret = gson.toJson(retMap);
		return Response.ok(ret).build();
		
	}
}
