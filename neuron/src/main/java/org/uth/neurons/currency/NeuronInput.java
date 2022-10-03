package org.uth.neurons.currency;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class NeuronInput
{
  public String targetEvent;
  public String countState;
  public String additionalData;

  public NeuronInput( String targetEventInput, String countStateInput, String additionalDataInput )
  {
    targetEvent = targetEventInput;
    countState = countStateInput;
    additionalData = additionalDataInput;
  }

  @Override
  public String toString()
  {
    return "DATA{targetEvent=" + targetEvent + ", countState=" + countState + ", additionalData=" + additionalData + "}";
  }
}
