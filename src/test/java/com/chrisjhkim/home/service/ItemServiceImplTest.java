package com.chrisjhkim.home.service;

import com.chrisjhkim.home.entity.Item;
import com.chrisjhkim.home.entity.Tag;
import com.chrisjhkim.home.repository.ItemJpaRepository;
import com.chrisjhkim.home.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceImplTest {


	private final ItemJpaRepository itemJpaRepository = mock(ItemJpaRepository.class);
	private final TagRepository tagRepository = mock(TagRepository.class);
	private final ItemService itemService = ItemServiceImpl.builder()
			.itemJpaRepository(itemJpaRepository)
			.tagRepository(tagRepository)
			.build();

	@Test
	void testCreateItemWithTag() {
		// Test # Given
		ItemDto.CreateRequest createRequest = ItemDto.CreateRequest.builder()
				.name("Item1")
				.description("Description1")
				.tagId(1L)
				.build();

		Tag tag = Tag.builder().id(1L).name("Tag1").build();
		Item savedItem = Item.builder().id(1L).name("Item1").description("Description1").build();

		when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
		when(itemJpaRepository.save(any(Item.class))).thenReturn(savedItem);

		// ArgumentCaptor 추가
		ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);


		// Test # When
		ItemDto.Response response = itemService.createItem(createRequest);

		// Test # Then
		assertNotNull(response);
		assertEquals(1L, response.getId());
		assertEquals("Item1", response.getName());

		// 저장된 Item 객체 검증
		verify(itemJpaRepository).save(itemCaptor.capture());
		Item capturedItem = itemCaptor.getValue();
		assertNotNull(capturedItem.getTag());
		assertEquals(1L, capturedItem.getTag().getId());
		assertEquals("Tag1", capturedItem.getTag().getName());

	}

	@Test
	void testCreateItemWithoutTag() {
		// Test # Given
		ItemDto.CreateRequest createRequest = ItemDto.CreateRequest.builder()
				.name("Item2")
				.description("Description2")
				.build();

		Item savedItem = Item.builder().id(2L).name("Item2").description("Description2").build();

		when(itemJpaRepository.save(any(Item.class))).thenReturn(savedItem);

		// Test # When
		ItemDto.Response response = itemService.createItem(createRequest);

		// Test # Then
		assertNotNull(response);
		assertEquals(2L, response.getId());
		assertEquals("Item2", response.getName());
	}

	@Test
	void testCreateItemTagNotFound() {
		// Test # Given
		ItemDto.CreateRequest createRequest = ItemDto.CreateRequest.builder()
				.name("Item3")
				.description("Description3")
				.tagId(99L)
				.build();

		when(tagRepository.findById(99L)).thenReturn(Optional.empty());


		// Test # When
		// Test # Then
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> itemService.createItem(createRequest));
		assertEquals("Tag not found", exception.getMessage());
	}

	@Test
	void testCreateItemWithNullTagId() {
		// Test # Given
		ItemDto.CreateRequest createRequest = ItemDto.CreateRequest.builder()
				.name("Item4")
				.description("Description4")
				.tagId(null)
				.build();

		Item savedItem = Item.builder()
				.id(4L)
				.name("Item4")
				.description("Description4")
				.build();

		when(itemJpaRepository.save(any(Item.class))).thenReturn(savedItem);

		// Test # When
		ItemDto.Response response = itemService.createItem(createRequest);

		// Test # Then
		assertNotNull(response);
		assertEquals(4L, response.getId());
		assertEquals("Item4", response.getName());
	}

	@Test
	void testCreateItemWithMultipleTags() {
		// Test # Given
		List<Tag> tags = List.of(
				Tag.builder().id(1L).name("Tag1").build(),
				Tag.builder().id(2L).name("Tag2").build()
		);

		ItemDto.CreateRequest createRequest = ItemDto.CreateRequest.builder()
				.name("Item5")
				.description("Description5")
				.tagId(1L) // Simulate the logic for selecting multiple tags in the future
				.build();

		Item savedItem = Item.builder()
				.id(5L)
				.name("Item5")
				.description("Description5")
				.tags(tags)
				.build();

		when(tagRepository.findById(1L)).thenReturn(Optional.of(Tag.builder().id(1L).name("Tag1").build()));
		when(itemJpaRepository.save(any(Item.class))).thenReturn(savedItem);

		// Test # When
		ItemDto.Response response = itemService.createItem(createRequest);

		// Test # Then
		assertNotNull(response);
		assertEquals(5L, response.getId());
		assertEquals("Item5", response.getName());
		assertEquals(2, savedItem.getTags().size());
	}
}