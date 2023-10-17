Neuron 0.1 Design Pseudocode

(Driven by CloudEvent)

Environment Variables:
MEMORY_ID - UUID for getting memory state from grid (via gridconnect)
GRIDCONNECT_EP - endpoint for the local gridconnect service (for building URL)
LOW_THRESHOLD - value to emit low threshold event at
HIGH_THRESHOLD - value to emit high threshold event at
LOW_EVENT - cloudevent ID to emit at low threshold
HIGH_EVENT - cloudevent to emit at high threshold
INITIAL_STATE - initial value at startup

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
