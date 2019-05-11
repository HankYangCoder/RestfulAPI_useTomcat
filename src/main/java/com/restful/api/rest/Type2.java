package com.restful.api.rest;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("GW2")
public class Type2 extends BaseApiRest
{
	
	public Type2()
	{
	}
	
	@GET
	@Path("test/{func}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response test(@PathParam("func") String func)
	{
		System.out.println(" GW2 / {func} "+ func);
		return super.doAction(func);
	}
	
	@POST
	@Path("/{trandatetime}/{status}/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response gwInterface(@PathParam("trandatetime") String trandatetime,
						@PathParam("status") String status,
						@PathParam("id") String id
						)
	{
		System.out.println("h!");
		return super.doAction(trandatetime, status, id);
	}

	


	@Override
	protected String getUserOP() {
		return "TEST";
	}

	@Override
	protected void invoke_QueryError(Map<String, Object> ret) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void invoke_InBoundINITError(Map<String, Object> ret) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void invoke_ParamCheckError(Map<String, Object> ret) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void invoke_ProcessError(Map<String, Object> ret) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void invoke_DefaultError(Map<String, Object> ret) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void invoke_CheckSECError(Map<String, Object> ret) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Map<String, Object> invoke_processData(com.restful.api.po.InBoundBean inBound, String userOP) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void invoke_NecessaryWork(com.restful.api.po.InBoundBean inBound, Map<String, Object> retMap) {
		// TODO Auto-generated method stub
		
	}


}
