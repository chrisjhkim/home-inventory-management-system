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
public class Tag extends BaseTimeEntity{
	// == Columns == //
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Tag parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Tag> children = new ArrayList<>();

	@OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
	private List<ItemTag> itemTags = new ArrayList<>();

	// == 생성자 == //
	@SuppressWarnings("unused") // Lombok 에서 쓰임
	@Builder
	Tag(Long id, String name, Tag parent, List<Tag> children, List<Item> items) {
		this.id = id;
		this.name = name;
		this.children = new ArrayList<>();
		this.itemTags = new ArrayList<>();

		if ( parent != null ) {
			this.changeParentTag(parent);
		}

		if ( children != null ) {
			children.forEach(child -> child.changeParentTag(this));
		}

		if ( items != null ) {
//			items.forEach(item -> item.addTag(this));
			// TODO ?
		}


	}

	// == static 생성자 == //
	// == 연관관계 매핑 == //
	// == Getter Setter == //


	// == Overrides == //
	@Override
	public String toString() {
		return "Tag{" +
				"id=" + id +
				", name='" + name + '\'' +
				(parent != null ? ", parent.name="+parent.name : "") +
				", children=" + children +
				", createdAt=" + getCreatedAt() +
				", updatedAt=" + getUpdatedAt() +
				'}';
	}



	// == 비즈니스 로직 == //
	public void changeParentTag(Tag parentTag){
		if ( this.parent != null ) {
			this.parent.getChildren().remove(this);
		}

		this.parent = parentTag;
		if ( parentTag != null ) {
			parentTag.getChildren().add(this);
		}
	}
}
