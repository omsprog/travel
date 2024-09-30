package com.omsprog.travel.helper;

import com.omsprog.travel.dto.external_models.DogFactDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class DogFactsHelper {
    private final WebClient dogClient;
    private static final String FACTS_DOG_PATH = "/api/v2/facts";
    private static final String FACTS_DOG_QUERY = "?limit={limit}";

    @Value(value = "${api.base.limit}")
    private String dogApiLimit;

    public DogFactDto getDogFacts() {

        log.info(FACTS_DOG_PATH + "  :  {}", dogApiLimit);

        return this.dogClient
                .get()
                .uri(uri ->
                    uri.path(FACTS_DOG_PATH)
                    .query(FACTS_DOG_QUERY)
                    .build(dogApiLimit)
                )
                .retrieve()
                .bodyToMono(DogFactDto.class)
                .block();
    }

    public DogFactsHelper(@Qualifier(value = "dog_facts") WebClient dogClient) {
        this.dogClient = dogClient;
    }


}