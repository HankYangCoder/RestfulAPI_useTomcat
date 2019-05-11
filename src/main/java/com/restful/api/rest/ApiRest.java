package com.restful.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

/**
 * 使用於 api 呼叫的 rest 父類別
 * 不驗session，認證檢核必需另外實作
 * @author Hank
 *
 */
public class ApiRest 
{
	Gson gson;	

	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;
	
	public ApiRest()
	{
		gson = new Gson();
	}
	
	
	public UriInfo getUriInfo() {
		return uriInfo;
	}


	public void setUriInfo(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}


	public Request getRequest() {
		return request;
	}


	public void setRequest(Request request) {
		this.request = request;
	}

/* samples	
 *  collection resource: /apps
 *  instance resource: /app/123
 *  擴充功能(QueryParam)：expend, fields, pagination(offset, limit), 
 *                     method rewrite(_delete, _put, _get, _post)
 *  pagination result tag:offset, limit, first, previous, next, last, items
 *  meta: 存放 href 參考url及 media type 
 * 
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTodo(JAXBElement<Todo> todo) {
	    Todo c = todo.getValue();
	    return putAndGetResponse(c);
	}
	  
	@DELETE
	public void deleteTodo() {
	    Todo c = TodoDao.instance.getModel().remove(id);
	    if(c==null)
	      throw new RuntimeException("Delete: Todo with " + id +  " not found");
	}
	
	@GET
	@Path("sample/{organization_id}/{customer_id}/{period}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response sample(@PathParam("organization_id") String organization_id,
									@PathParam("customer_id") String customer_id,
									@PathParam("period") String period,
									@DefaultValue("true") @QueryParam("min-m") boolean hasMin								
			                       )
	{
		String ret=null;
		
		
		return Response.ok(ret).build();
	}

	
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newTodo(@FormParam("id") String id,
        @FormParam("summary") String summary,
        @FormParam("description") String description,
        @Context HttpServletResponse servletResponse) throws IOException {
        Todo todo = new Todo(id, summary);
        if (description != null) {
          todo.setDescription(description);
        }
        TodoDao.instance.getModel().put(id, todo);
        servletResponse.sendRedirect("../create_todo.html");
    }
  	
*/	
}
