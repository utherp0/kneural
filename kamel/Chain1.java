// camel-k: language=java
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.*;

public class Chain1 extends org.apache.camel.builder.RouteBuilder
{
  @Override
  public void configure() throws Exception
  {

    from("knative:event/chainstart")
          .log("Received: ${body}")
          .to("knative:event/chain2")
          .setBody(simple("ChainStart Event generated ${body}"));
  }
}
