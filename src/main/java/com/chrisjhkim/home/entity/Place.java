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
public class Place extends BaseTimeEntity {

	// == Columns == //
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Place parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private List<Place> children = new ArrayList<>();



	// == 생성자 == //
	@Builder
	public Place(Long id, String name, String description, Place parent) {
		this.id = id;
		this.name = name;
		this.description = description;
		if ( parent != null ) {
			this.changeParentPlace(parent);
		}
		this.children = new ArrayList<>();
	}


	// == static 생성자 == //
	// == 연관관계 매핑 == //
	// == Getter Setter == //
	// == Overrides == //
	// == 비즈니스 로직 == //
	public void changeParentPlace(Place parentPlace) {
		if ( this.parent != null ) {
			this.parent.getChildren().remove(this);
		}
		this.parent = parentPlace;
		if ( parentPlace != null ) {
			parentPlace.getChildren().add(this);
		}
	}


}
