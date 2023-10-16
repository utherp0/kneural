package org.uth.kneuron.currency;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class KneuronInput
{
  public String _operator;
  public int _value;

  public KneuronInput( String operator, int value )
  {
    _operator = operator;
    _value = value;
  }

  @Override
  public String toString()
  {
    return "{\"operator\":\"" + _operator + "\",\"value\":\"" + _value + "\"}";
  }
}
