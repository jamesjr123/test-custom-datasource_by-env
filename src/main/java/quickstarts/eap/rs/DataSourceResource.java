package quickstarts.eap.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Path("datasource")
public class DataSourceResource {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceResource.class);

    @Resource(lookup = "java:jboss/datasources/shrDS")
    DataSource dataSource;

    @GET
    @Path("status")  
    public String status() {
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT 1 from dual")) {
            ps.executeQuery();
            return "ok";
        } catch (SQLException e) {
            LOG.error("DataSource validation failed: java:jboss/datasources/shrDS", e);
            return e.getMessage();
        }
    }
}