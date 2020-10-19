package com.test.eip.eipexample;

import com.test.eip.eipexample.dto.PersonDTO;
import com.test.eip.eipexample.routes.RouterTest;
import lombok.SneakyThrows;
import org.apache.camel.*;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class EipExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(EipExampleApplication.class, args);
    }

}

@RestController
class EIPController {

    @Autowired
    CamelContext camelContext;

    @EndpointInject("direct:apicall")
    private ProducerTemplate producer;


    @GetMapping("/call")
    public ResponseEntity aggregateApiCall() {
        Exchange str  = producer.request("direct:apicall", exchange -> exchange.getIn().setBody(exchange.getIn().getBody(String.class)));
        return ResponseEntity.ok(str.getIn().getBody());
    }
}