package org.uth.kneural;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Path("/grid")
public class GridConnector
{
  @ConfigProperty(name = "CACHESERVICE", defaultValue="infinispan")
  String _cacheService;

  @ConfigProperty(name = "CACHENAME", defaultValue="neuralcache")
  String _cacheName;

  @Path("ping")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String ping()
  {
    return "CALL RECV";
  }

  @Path("add")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String addEntryToCache(@QueryParam("cache") String cacheName, @QueryParam("key") String key, @QueryParam("value") String value )
  {
    // Build the composite target for the addition POST
    // Example = http://kneuralgrid:11222/rest/v2/caches/neuralnet
    // (data goes down the line as a header)
    String target = "http://" + _cacheService + ":11222/rest/v2/caches/" + cacheName + "/" + key;

    try
    {
      System.out.println( "Posting data to " + target );
      System.out.println( "Data: " + value );

      URL url = new URL( target );
      HttpURLConnection postConnection = (HttpURLConnection)url.openConnection();

      postConnection.setRequestMethod( "POST" );
      postConnection.setRequestProperty( "Content-Type", "text/plain" );

      postConnection.setDoOutput(true);

      OutputStreamWriter outputWriter = new OutputStreamWriter( postConnection.getOutputStream(), "UTF-8");
      outputWriter.write(value);
      outputWriter.flush();
      outputWriter.close();

      int responseCode = postConnection.getResponseCode();

      System.out.println( responseCode + " from " + target );

      return "Response " + responseCode;
    }
    catch( Exception exc )
    {
      System.out.println( "POST failure in cache insert : " + exc.toString());

      return exc.toString();
    }
  }

  @Path("create")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String performCacheCreate(@QueryParam("cache") String cacheNameParam ) 
  {
    String cacheTarget = ( cacheNameParam == null ? _cacheName : cacheNameParam );

    // Build the composite target for the creation POST
    // Example - http://kneuralgrid:11222/rest/v2/caches/neuralnet
    String target = "http://" + _cacheService + ":11222/rest/v2/caches/" + cacheTarget;

    try
    {
      System.out.println( "Posting to " + target );

      URL url = new URL(target);
      HttpURLConnection postConnection = (HttpURLConnection)url.openConnection();

      postConnection.setRequestMethod( "POST" );
      postConnection.setRequestProperty( "Content-Type", "application/json" );

      postConnection.setDoOutput(true);

      int responseCode = postConnection.getResponseCode();

      System.out.println( responseCode + " from " + target );

      return "Response " + responseCode;
    }
    catch( Exception exc )
    {
      System.out.println( "POST failure in cache create : " + exc.toString());

      return exc.toString();
    }
  }

  @Path("caches")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String fetchCaches(@QueryParam("cache") String cacheNameParam )
  {
    String cacheTarget = ( cacheNameParam == null ? _cacheName : cacheNameParam );

    // Build the composite target for the creation POST
    // Example - http://kneuralgrid:11222/rest/v2/caches/neuralnet
    String target = "http://" + _cacheService + ":11222/rest/v2/caches/";

    try
    {
      System.out.println( "GETting " + target );

      URL url = new URL(target);
      HttpURLConnection getConnection = (HttpURLConnection)url.openConnection();

      getConnection.setRequestMethod( "GET" );
      getConnection.setRequestProperty( "Content-Type", "application/json" );

      getConnection.setDoOutput(true);

      int responseCode = getConnection.getResponseCode();

      System.out.println( responseCode + " from " + target );

      // If the response is 200 grab the data
      if( responseCode == 200 )
      {
        BufferedReader in = new BufferedReader( new InputStreamReader(getConnection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }

        in.close();

        System.out.println( "Recv: " + content.toString());

        // Return response code and data
        return "Response " + responseCode + " data: " + content.toString();
      }

      return "Response " + responseCode;
    }
    catch( Exception exc )
    {
      System.out.println( "GET failure in caches fetch : " + exc.toString());

      return exc.toString();
    }
  }
}
