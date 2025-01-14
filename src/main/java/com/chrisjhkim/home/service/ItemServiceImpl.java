package com.chrisjhkim.home.service;

import com.chrisjhkim.home.entity.Item;
import com.chrisjhkim.home.entity.Tag;
import com.chrisjhkim.home.repository.ItemJpaRepository;
import com.chrisjhkim.home.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class ItemServiceImpl implements ItemService {
	private final ItemJpaRepository itemJpaRepository;
	private final TagRepository tagRepository;
	@Transactional
	@Override
	public ItemDto.Response createItem(ItemDto.CreateRequest createRequest) {

		List<Tag> tags = getTags(createRequest.getTagId());

		Item item = Item.builder()
				.name(createRequest.getName())
				.description(createRequest.getDescription())
				.tags(tags)
				.build();

		Item savedItem = itemJpaRepository.save(item);

		return ItemDto.Response.builder()
				.id(savedItem.getId())
				.name(savedItem.getName())
				.description(savedItem.getDescription())
				.createdAt(savedItem.getCreatedAt())
				.updatedAt(savedItem.getUpdatedAt())
				.build();
	}

	private List<Tag> getTags(Long tagId) {
		if ( tagId == null ) {
			return List.of();
		}

		return tagRepository.findById(tagId)
				.map(List::of)
				.orElseThrow(()-> new IllegalArgumentException("Tag not found"));
	}

}
