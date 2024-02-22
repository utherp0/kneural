// camel-k: language=java
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.*;

public class Chain3 extends org.apache.camel.builder.RouteBuilder
{
  @Override
  public void configure() throws Exception
  {

    from("knative:event/chain3")
          .log("Received: ${body}")
          .to("knative:event/chain4")
          .setBody(simple("Chain3 Event generated ${body}"));
  }
}
