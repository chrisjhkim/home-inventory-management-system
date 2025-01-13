package com.chrisjhkim.home.repository;

import com.chrisjhkim.home.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CategoryRepositoryTest {
	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	@DisplayName("연관관계 매핑 테스트 ")
	void categoryRelationshipTest() {
		// Given
		Category coke = Category.builder()
				.name("coke")
				.build();

		Category beverage = Category.builder()
				.name("beverage")
				.children(List.of(coke))
				.build();


		// When
		categoryRepository.save(coke);
		categoryRepository.save(beverage);

		// Then
		// 1. 부모 카테고리 검증
		Category savedParent = categoryRepository.findById(beverage.getId())
				.orElseThrow(() -> new AssertionError("Parent category not found"));

		assertAll(
				() -> assertThat(savedParent.getName()).isEqualTo(beverage.getName()),
				() -> assertThat(savedParent.getChildren()).contains(coke)
		);

		// 2. 자식 카테고리 검증
		Category savedChild = categoryRepository.findById(coke.getId())
				.orElseThrow(() -> new AssertionError("Child category not found"));

		assertThat(savedChild.getParent()).isEqualTo(beverage);

	}


}