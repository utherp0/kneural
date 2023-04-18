package org.uth.neurons;

import io.quarkus.funqy.Context;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.knative.events.CloudEvent;
import io.quarkus.funqy.knative.events.CloudEventMapping;
import io.quarkus.funqy.knative.events.CloudEventBuilder;
//import io.smallrye.mutiny.Uni;
//import io.smallrye.mutiny.subscription.UniEmitter;
//import io.vertx.core.Vertx;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.json.simple.*;
import org.json.simple.parser.*;

import javax.inject.Inject;
import java.net.*;
import java.util.*;
import java.io.*;

import org.uth.neurons.currency.*;

public class PersistTest1
{
  private long _start = System.currentTimeMillis();

  // Use @ConfigProperty to inject ENVs here
  @ConfigProperty(name="TARGETFILE")
  String _targetFile;

  @Funq
  @CloudEventMapping(trigger = "persistRequest", responseSource = "PersistTest1", responseType = "persistResponse")
  public CloudEvent<NeuronOutput> function( NeuronInput input, @Context CloudEvent<NeuronInput> cloudEvent )
  {
    //NeuronInput input = cloudEvent.data();

    System.out.println( "RECV: " + cloudEvent.type() + " " + cloudEvent.toString() );

    // Check the existence of the file
    try
    {
      FileOutputStream outputStream = new FileOutputStream(_targetFile, true);

      String dataToAppend = "/r/n(" + _start + ") " + cloudEvent.type() + " " + cloudEvent.toString();
      byte[] dataInBytes = dataToAppend.getBytes();
      outputStream.write(dataInBytes);
      outputStream.close();
    }
    catch( Exception exc )
    {
      System.out.println( "Unable to write to file due to " + exc.toString() );
    }

    NeuronOutput output = new NeuronOutput();
    output.setPayload( "RECV: " + _start );

    return CloudEventBuilder.create()
             .source("PersistTest1")
             .type("persistResponse")
             .id(UUID.randomUUID().toString())
             .build(output);
  }
}
