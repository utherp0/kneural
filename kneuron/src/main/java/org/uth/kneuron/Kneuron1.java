package org.uth.kneuron;

import io.quarkus.funqy.Context;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.knative.events.CloudEvent;
import io.quarkus.funqy.knative.events.CloudEventBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.uth.kneuron.currency.*;

/**
 * Initial memory-consuming Kneuron implementation
 * Environment Variables:
MEMORY_ID - UUID for getting memory state from grid (via gridconnect)
GRIDCONNECT_EP - endpoint for the local gridconnect service (for building URL)
LOW_THRESHOLD - value to emit low threshold event at
HIGH_THRESHOLD - value to emit high threshold event at
LOW_EVENT - cloudevent ID to emit at low threshold
HIGH_EVENT - cloudevent to emit at high threshold
INITIAL_STATE - initial value at startup
WATCHMAN_EP - endpoint for log state engine

Payload:
For testing - {"operator":"reset|addition|subtraction|multiplication|division","value":"delta for operator"}

Simple Pseudocode:

Pull memory state(valueCurrent) for MEMORY_ID
Process payload - get operator

if(RESET) valueCurrent = INITIAL_STATE
  emit no event
else
  if(ADDITION) valueCurrent += value
  if(SUBTRACTION) valueCurrent -= value
  if(MULTIPLICATION) valueCurrent *= value
  if(DIVISION) valueCurrent /= value

  watchman(UUID, valueCurrent)

  if(valueCurrent > HIGH_THRESHOLD)
    (logic for follow on event payload)
    emit HIGH_EVENT
  else if(valueCurrent < LOW_THRESHOLD)
    (logic for follow on event payload)
    emit LOW_EVENT
  endif
endif
 * @author uther
 */
public class Kneuron1
{
  @ConfigProperty(name="CACHE_NAME")
  String _cacheName;
  
  @ConfigProperty(name="MEMORY_ID")
  String _memoryID;

  @ConfigProperty(name="GRIDCONNECT_EP")
  String _gridconnectEP;

  @ConfigProperty(name="LOW_THRESHOLD")
  int _lowThreshold;

  @ConfigProperty(name="HIGH_THRESHOLD")
  int _highThreshold;

  @ConfigProperty(name="LOW_EVENT")
  String _lowEvent;

  @ConfigProperty(name="HIGH_EVENT")
  String _highEvent;

  @ConfigProperty(name="INITIAL_STATE")
  String _initialState;  
  
  @ConfigProperty(name="WATCHMAN_EP")
  String _watchmanEP;
  
  @Funq
  public CloudEvent<KneuronOutput> processor( KneuronInput input, @Context CloudEvent<KneuronInput> cloudEvent )
  {
    long start = System.currentTimeMillis();
    System.out.println( "RECV: " + cloudEvent.type() + " " + cloudEvent.toString() );

    KneuronOutput output = new KneuronOutput();

    // Step 1: get memory state from target cache
    
    // Step 2: perform logic check to determine new state and output event (if any)
    
    // Step 3: rewrite memory state
    
    // Step 4: emit event if needed
    
    // DEBUG
    output.setPayload( "RECV: " + start );
    String targetEvent = _highEvent;
    
    return CloudEventBuilder.create()
             .source(this.getClass().getName()) 
             .type(targetEvent)
             .id(_memoryID)
             .build(output);
  }
}
