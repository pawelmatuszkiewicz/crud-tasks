package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TrelloMapperTestSuite {
    private List<TrelloBoardDto> trelloBoardDtos;
    private List<TrelloBoard> trelloBoards;
    private List<TrelloListDto> trelloListDtos;
    private List<TrelloList> trelloLists;

    @Before
    public void init() {
        trelloBoardDtos = new ArrayList<>();
        trelloBoards = new ArrayList<>();
        trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "To Do Dto List", false));
        trelloListDtos.add(new TrelloListDto("2", "In Progress Dto List", false));
        trelloListDtos.add(new TrelloListDto("3", "Done Dto List", false));
        trelloBoardDtos.add(new TrelloBoardDto("My Dto Board", "1", trelloListDtos));
        trelloBoardDtos.add(new TrelloBoardDto("Second Dto Board", "2", trelloListDtos));

        trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "To Do List", false));
        trelloLists.add(new TrelloList("2", "In Progress List", false));
        trelloLists.add(new TrelloList("3", "Done List", false));
        trelloBoards.add(new TrelloBoard("1", "My Board", trelloLists));
        trelloBoards.add(new TrelloBoard("2", "My Second Board", trelloLists));
    }

    @Test
    public void testMapToBoards() {
        // Given
        TrelloMapper trelloMapper = new TrelloMapper();
        // When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtos);
        TrelloBoard trelloBoard = trelloBoardList.get(0);
        // Then
        assertEquals(2, trelloBoardList.size());
        assertEquals("My Dto Board", trelloBoard.getName());
        assertEquals("1", trelloBoard.getLists().get(0).getId());
        assertEquals("To Do Dto List", trelloBoard.getLists().get(0).getName());
        assertFalse(trelloBoard.getLists().get(0).isClosed());
    }

    @Test
    public void testMapToBoardsDto() {
        // Given
        TrelloMapper trelloMapper = new TrelloMapper();
        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        TrelloBoardDto trelloBoardDto = trelloBoardDtos.get(0);
        // Then
        assertEquals(2, trelloBoardDtos.size());
        assertEquals("My Board", trelloBoardDto.getName());
        assertEquals("3", trelloBoardDto.getLists().get(2).getId());
        assertEquals("Done List", trelloBoardDto.getLists().get(2).getName());
        assertFalse(trelloBoardDto.getLists().get(2).isClosed());
    }

    @Test
    public void testMapToList() {
        // Given
        TrelloMapper trelloMapper = new TrelloMapper();
        // When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        // Then
        assertEquals(3, trelloLists.size());
        assertEquals("1", trelloLists.get(0).getId());
        assertEquals("To Do Dto List", trelloLists.get(0).getName());
        assertFalse(trelloLists.get(0).isClosed());
    }

    @Test
    public void testMapToListDto() {
        // Given
        TrelloMapper trelloMapper = new TrelloMapper();
        // When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        // Then
        assertEquals(3, trelloListDtos.size());
        assertEquals("1", trelloListDtos.get(0).getId());
        assertEquals("To Do List", trelloListDtos.get(0).getName());
        assertFalse(trelloListDtos.get(0).isClosed());
    }

    @Test
    public void testMapToCard() {
        // Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test Card", "Test trello card mapping", "top", "test_id");
        // When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        // Then
        assertEquals("Test Card", trelloCard.getName());
        assertEquals("Test trello card mapping", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("test_id", trelloCard.getListId());
    }

    @Test
    public void testMapToCardDto() {
        // Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloCard trelloCard = new TrelloCard(
                "Test Card", "Test trello card mapping", "top", "test_id");
        // When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        // Then
        assertEquals("Test Card", trelloCardDto.getName());
        assertEquals("Test trello card mapping", trelloCardDto.getDescription());
        assertEquals("top", trelloCardDto.getPos());
        assertEquals("test_id", trelloCardDto.getListId());
    }
}
