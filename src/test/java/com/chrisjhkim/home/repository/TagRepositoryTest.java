package com.chrisjhkim.home.repository;

import com.chrisjhkim.home.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class TagRepositoryTest {
	@Autowired
	private TagRepository tagRepository;


	@Nested
	@DisplayName("Entity 테스트 용")
	class EntityTest{

		@Test
		@DisplayName("저장 시점에 생성되야 할 값들 테스트")
		void test_save(){
			// Test # Given

			Tag tag = Tag.builder()
					.name("test-name")
					.build();
			System.out.println("tag = " + tag);
			assertAll(
					()-> assertThat(tag.getId())
							.as("저장전에 빈값 맞는지 추가 확인")
							.isNull(),
					()-> assertThat(tag.getCreatedAt())
							.as("저장전에 빈값 맞는지 추가 확인")
							.isNull(),
					()-> assertThat(tag.getUpdatedAt())
							.as("저장전에 빈값 맞는지 추가 확인")
							.isNull()
			);


			// Test # When
			tagRepository.save(tag);

			// Test # Then
			assertAll(
					()-> assertThat(tag.getId())
							.as("저장 후에 id 값 자동 생성되어야 한다.")
							.isNotNull(),
					()-> assertThat(tag.getCreatedAt())
							.as("저장 후에 createdAt 자동 생성되어야 한다.")
							.isNotNull(),
					()-> assertThat(tag.getUpdatedAt())
							.as("저장 후에 updatedAt 자동 생성되어야 한다.")
							.isNotNull()
			);

		}
		@Test
		@DisplayName("연관관계 매핑 테스트 ")
		void test_tagRelationship() {
			// Given
			Tag coke = Tag.builder()
					.name("coke")
					.build();

			Tag beverage = Tag.builder()
					.name("beverage")
					.children(List.of(coke))
					.build();


			// When
			tagRepository.save(coke);
			tagRepository.save(beverage);

			// Then
			// 1. 부모 카테고리 검증
			Tag savedParent = tagRepository.findById(beverage.getId())
					.orElseThrow(() -> new AssertionError("Parent tag not found"));

			assertAll(
					() -> assertThat(savedParent.getName()).isEqualTo(beverage.getName()),
					() -> assertThat(savedParent.getChildren()).contains(coke)
			);

			// 2. 자식 카테고리 검증
			Tag savedChild = tagRepository.findById(coke.getId())
					.orElseThrow(() -> new AssertionError("Child tag not found"));

			assertThat(savedChild.getParent()).isEqualTo(beverage);

		}
	}


}