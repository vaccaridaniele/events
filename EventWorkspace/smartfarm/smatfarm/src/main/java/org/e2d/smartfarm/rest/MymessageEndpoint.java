package org.e2d.smartfarm.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.e2d.smartfarm.mypojo.Mymessage;

@Path("/mymessages")
@Produces("application/json")
@Consumes("application/json")
public class MymessageEndpoint {

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Mymessage mymessage = null;
		if (mymessage == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(mymessage).build();
	}

}
