package org.uth.neurons;

import io.quarkus.funqy.Context;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.knative.events.CloudEvent;
import io.quarkus.funqy.knative.events.CloudEventMapping;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.UniEmitter;
import io.vertx.core.Vertx;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.json.simple.*;
import org.json.simple.parser.*;

import javax.inject.Inject;
import java.net.*;
import java.util.*;

import org.uth.neurons.currency.*;

public class Test1
{
  private long _start = System.currentTimeMillis();

  @Inject
  Vertx vertx;

  // Use @ConfigProperty to inject ENVs here

  @Funq
  @CloudEventMapping(responseType = "neuronevent")
  public Uni<NeuronOutput> function( Object input, @Context CloudEvent cloudEvent )
  {
    return Uni.createFrom().emitter( emitter ->
    {
      buildResponse( input, cloudEvent, emitter );
    });
  }

  public void buildResponse( Object input, CloudEvent cloudEvent, UniEmitter<? super NeuronOutput> emitter )
  {
    System.out.println("Received: " + input );

    // Perform Neuron processing
    NeuronOutput output = new NeuronOutput();

    output.setPayload( "{'payload','some stuff'}" );

    System.out.println( "Generating: " + output.getPayload() );

    emitter.complete(output);
  }
}
