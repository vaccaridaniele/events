package org.e2d.smartfarm.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.e2d.smartfarm.mypojo.Mymessage;
import org.e2d.smartfarm.pojo.EventScrape;
import org.e2d.smartfarm.util.DbUtil;
import org.event.rest.Test;

@Path("/eventscrapes")
@Produces("application/json")
@Consumes("application/json")
public class EventScrapeEndpoint {
	static Logger logger = Logger.getLogger(Test.class);

	@POST
	public Response create(final EventScrape eventscrape) {
		// TODO: process the given eventscrape
		// you may want to use the following return statement, assuming that
		// EventScrape#getId() or a similar method
		// would provide the identifier to retrieve the created EventScrape
		// resource:
		// return
		// Response.created(UriBuilder.fromResource(EventScrapeEndpoint.class).path(String.valueOf(eventscrape.getId())).build()).build();

		DbUtil cu = new DbUtil();
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = cu.getDataSourceConnection();
			p = connection.prepareStatement("INSERT INTO event(titolo,description) VALUES (?,?);");
			p.setString(1, eventscrape.getTitle());
			p.setString(2, eventscrape.getDescription());
			boolean result = p.execute();
			return Response.created(null).build();
		} catch (Exception e) {
			logger.fatal(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			try {
				p.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * http://localhost:8080/events/rest/eventscrapes/1
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		// TODO: retrieve the eventscrape
		EventScrape eventscrape = null;
		if (eventscrape == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(eventscrape).build();
	}

	@GET
	public List<EventScrape> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		// TODO: retrieve the eventscrapes
		final List<EventScrape> eventscrapes = null;
		return eventscrapes;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final EventScrape eventscrape) {
		// TODO: process the given eventscrape
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		// TODO: process the eventscrape matching by the given id
		return Response.noContent().build();
	}

}
