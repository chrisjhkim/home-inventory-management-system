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
public class Placement {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	private Integer quantity;

	// == 비즈니스 로직 == //
	public void changeQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
