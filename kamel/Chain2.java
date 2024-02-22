// camel-k: language=java
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.*;

public class Chain2 extends org.apache.camel.builder.RouteBuilder
{
  @Override
  public void configure() throws Exception
  {

    from("knative:event/chain2")
          .log("Received: ${body}")
          .to("knative:event/chain3")
          .setBody(simple("Chain2 Event generated ${body}"));
  }
}
