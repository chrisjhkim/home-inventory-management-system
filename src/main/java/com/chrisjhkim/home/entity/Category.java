package com.chrisjhkim.home.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Builder
@NoArgsConstructor(access = PROTECTED)
@Entity
@Getter
public class Category extends BaseTimeEntity{
	// == Columns == //
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Category> children = new ArrayList<>();

	// == 생성자 == //
	@SuppressWarnings("unused") // Lombok 에서 쓰임
	Category(Long id, String name, Category parent, List<Category> children) {
		this.id = id;
		this.name = name;
		this.children = new ArrayList<>();

		if ( parent != null ) {
			this.changeParentCategory(parent);
		}

		if ( children != null ) {
			children.forEach(child -> child.changeParentCategory(this));
		}
	}

	// == static 생성자 == //
	// == 연관관계 매핑 == //
	// == Getter Setter == //


	// == Overrides == //
	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", name='" + name + '\'' +
				(parent != null ? ", parent.name="+parent.name : "") +
				", children=" + children +
				", createdAt=" + getCreatedAt() +
				", updatedAt=" + getUpdatedAt() +
				'}';
	}



	// == 비즈니스 로직 == //
	public void changeParentCategory(Category parentCategory){
		if ( this.parent != null ) {
			this.parent.getChildren().remove(this);
		}

		this.parent = parentCategory;
		if ( parentCategory != null ) {
			parentCategory.getChildren().add(this);
		}
	}
}
