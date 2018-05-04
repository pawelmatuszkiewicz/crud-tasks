package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.username}")
    private String username;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        Optional<TrelloBoardDto[]> boardsResponse = Optional.ofNullable(
                restTemplate.getForObject(makeGetTrelloBoardsUrl(), TrelloBoardDto[].class));

        return Arrays.asList(boardsResponse.orElseGet(() -> new TrelloBoardDto[]{}));
    }

    private URI makeGetTrelloBoardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + username + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id").build().encode().toUri();
    }
}
