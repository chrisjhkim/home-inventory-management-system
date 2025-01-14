package com.chrisjhkim.home.repository;

import com.chrisjhkim.home.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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


	@Nested
	@DisplayName("Entity 테스트 용")
	class EntityTest{

		@Test
		@DisplayName("저장 시점에 생성되야 할 값들 테스트")
		void test_save(){
			// Test # Given

			Category category = Category.builder()
					.name("test-name")
					.build();
			System.out.println("category = " + category);
			assertAll(
					()-> assertThat(category.getId())
							.as("저장전에 빈값 맞는지 추가 확인")
							.isNull(),
					()-> assertThat(category.getCreatedAt())
							.as("저장전에 빈값 맞는지 추가 확인")
							.isNull(),
					()-> assertThat(category.getUpdatedAt())
							.as("저장전에 빈값 맞는지 추가 확인")
							.isNull()
			);


			// Test # When
			categoryRepository.save(category);

			// Test # Then
			assertAll(
					()-> assertThat(category.getId())
							.as("저장 후에 id 값 자동 생성되어야 한다.")
							.isNotNull(),
					()-> assertThat(category.getCreatedAt())
							.as("저장 후에 createdAt 자동 생성되어야 한다.")
							.isNotNull(),
					()-> assertThat(category.getUpdatedAt())
							.as("저장 후에 updatedAt 자동 생성되어야 한다.")
							.isNotNull()
			);

		}
		@Test
		@DisplayName("연관관계 매핑 테스트 ")
		void test_categoryRelationship() {
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


}