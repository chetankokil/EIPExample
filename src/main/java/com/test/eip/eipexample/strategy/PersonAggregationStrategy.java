package com.test.eip.eipexample.strategy;

import com.test.eip.eipexample.dto.PersonDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.ExchangeHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Slf4j
@Component
public class PersonAggregationStrategy implements org.apache.camel.AggregationStrategy {


    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        PersonDTO personDTO = newExchange.getIn().getBody(PersonDTO.class);
        ArrayList<PersonDTO> list = null;
        if (oldExchange == null) {
            list = new ArrayList<PersonDTO>();
            list.add(personDTO);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(personDTO);
            return oldExchange;
        }
    }
}
