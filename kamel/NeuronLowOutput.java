// camel-k: language=java
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.*;

public class NeuronLowOutput extends org.apache.camel.builder.RouteBuilder
{
  @Override
  public void configure() throws Exception
  {

    from("knative:event/kneuronlow")
          .log("Received: LOW_EVENT ${body}");
  }
}
