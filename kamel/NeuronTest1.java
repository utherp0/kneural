// camel-k: language=java
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.*;

public class NeuronTest1 extends org.apache.camel.builder.RouteBuilder
{
  @Override
  public void configure() throws Exception
  {

    from("knative:event/kneuronhigh")
          .log("Received: HIGH_EVENT ${body}");
  }
}
