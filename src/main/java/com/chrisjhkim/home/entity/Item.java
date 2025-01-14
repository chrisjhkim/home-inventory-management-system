package com.chrisjhkim.home.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;


@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
public class Item extends BaseTimeEntity{

	// == Columns == //

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String description;

	// == 연관관계 매핑 == //
	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<Placement> placements;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private List<ItemTag> itemTags;

	// == 생성자 == //
	@Builder
	public Item(Long id, String name, String description, List<Tag> tags) {
		this.id = id;
		this.name = name;
		this.description = description;

		this.placements = new ArrayList<>();

		this.itemTags = new ArrayList<>();
		if ( tags != null ) {
			tags.forEach(this::addTag);
		}


	}
	// == static 생성자 == //

	// == Getter Setter == //
	// == Overrides == //
	// == 비즈니스 로직 == //
	public void addTag(Tag tag) {
		ItemTag itemTag = ItemTag.builder()
				.item(this)
				.tag(tag)
				.build();
		this.itemTags.add(itemTag);
		tag.getItemTags().add(itemTag);
	}

	public void removeTag(Tag tag) {

		ItemTag itemTagToRemove = this.itemTags.stream()
				.filter(itemTag -> itemTag.getTag().equals(tag))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("해당 태그가 존재하지 않습니다"));

		this.itemTags.remove(itemTagToRemove);
		tag.getItemTags().remove(itemTagToRemove);  // 양방향 관계 해제



		tag.getItemTags().removeIf(itemTag -> itemTag.getItem().equals(this));
		this.itemTags.removeIf(itemTag -> itemTag.getTag().equals(tag));
	}


}

