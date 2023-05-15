// camel-k: language=java
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.*;

public class NeuronForwarder extends org.apache.camel.builder.RouteBuilder
{
  @Override
  public void configure() throws Exception
  {

    from("knative:event/neuronevent")
          .log("Received: ${body}")
          .to("knative:event/neuronresponse")
          .setBody(simple("NeuronForwarder Event generated ${body}"));
  }
}
