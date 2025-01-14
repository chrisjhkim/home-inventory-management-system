package com.chrisjhkim.home.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

@Builder
@AllArgsConstructor(access = PACKAGE)
@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
public class ItemTag extends BaseTimeEntity {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;

	public ItemTag(Item item, Tag tag) {
		this.item = item;
		this.tag = tag;
	}
}