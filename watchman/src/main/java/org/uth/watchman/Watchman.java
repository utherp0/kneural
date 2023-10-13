package org.uth.watchman;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.FileOutputStream;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/watchman")
public class Watchman 
{
  @ConfigProperty(name = "LOGFILE", defaultValue="watchman.txt")
  String _logFile;  
  
  @GET
  @Path("/heartbeat")
  @Produces(MediaType.TEXT_PLAIN)
  public String heartbeat() 
  {
    return "::" + System.currentTimeMillis();
  }

  @GET
  @Path("/log")
  @Produces(MediaType.APPLICATION_JSON)
  public String log( @QueryParam("UUID") String uuid, @QueryParam("VALUE") String value )
  {
    // Check the existence of the file
    try
    {
      FileOutputStream outputStream = new FileOutputStream(_logFile, true);

      String dataToAppend = "\r\n(" + System.currentTimeMillis() + " - UUID:" + uuid + " VALUE: " + value;
      byte[] dataInBytes = dataToAppend.getBytes();
      outputStream.write(dataInBytes);
      outputStream.close();
      
      return("{\"logged\":\"true\"}");
    }
    catch( Exception exc )
    {
      System.out.println( "Unable to write to file due to " + exc.toString() );
      
      return("{\"logged\":\"false\"}");
    }    
  }
}
