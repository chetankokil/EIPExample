package com.test.eip.eipexample.routes;

import com.test.eip.eipexample.dto.PersonDTO;
import com.test.eip.eipexample.dto.Test;
import com.test.eip.eipexample.strategy.PersonAggregationStrategy;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RouterTest extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        from("direct:test")
                .log(LoggingLevel.INFO, "to Test body = ${body}")
                .multicast(new PersonAggregationStrategy())
                    .parallelProcessing()
                    .log(LoggingLevel.INFO, "https://randomuser.me/api/?gender=${body.gender}")
                    .enrich("https://randomuser.me/api/?gender=${body.gender}")
                .end();




        from("direct:apicall")
                .setBody(simple("${null}"))
                .to("https://randomuser.me/api/?results=5")
                .unmarshal().json(JsonLibrary.Gson,  Test.class)
                .to("direct:split-calls");

        from("direct:split-calls")
                .log(LoggingLevel.INFO, "data=${body}")
                .process((exchange -> {
                    Test test = exchange.getIn().getBody(Test.class);
                    List<PersonDTO> personDTOList = test.getResults().stream().collect(Collectors.toList());
                    exchange.getIn().setBody(personDTOList);
                }))
                .split(body())
                .to("direct:test")
                .log("${body}");
    }
}
