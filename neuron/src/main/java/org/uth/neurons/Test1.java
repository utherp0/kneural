package org.uth.neurons;

//import io.quarkus.funqy.Context;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.knative.events.CloudEvent;
//import io.quarkus.funqy.knative.events.CloudEventMapping;
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

import org.uth.neurons.currency.*;

public class Test1
{
  private long _start = System.currentTimeMillis();

  // Use @ConfigProperty to inject ENVs here
  @ConfigProperty(name="NEURONID")
  String _neuronID;

  @Funq
  public CloudEvent<NeuronOutput> function( CloudEvent<NeuronInput> cloudEvent )
  {
    NeuronInput input = cloudEvent.data();

    System.out.println( "RECV: " + cloudEvent.type() + " " + cloudEvent.toString() );

    NeuronOutput output = new NeuronOutput();
    output.setPayload( "RECV: " + _start );

    return CloudEventBuilder.create()
             .source(_neuronID )
             .type(input.targetEvent)
             .id(UUID.randomUUID().toString())
             .build(output);
  }
}
