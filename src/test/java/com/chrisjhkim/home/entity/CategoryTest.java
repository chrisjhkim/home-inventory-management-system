package com.chrisjhkim.home.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoryTest {

	@Nested
	@DisplayName("연관관계 편의 메서드 테스트 ")
	class MappingMethodTest{


		@Test
		@DisplayName("부모 Category 변경")
		void test_changeParentCategory() {
			// Given
			Category child = Category.builder()
					.name("coke")
					.build();

			Category originalParent = Category.builder()
					.name("beverage")
					.children(List.of(child))
					.build();

			Category changedParent = Category.builder()
					.name("탄산")
					.build();

			assertAll(
					()-> assertThat(originalParent.getChildren())
							.as("Child 세팅 되어 있어야 함")
							.contains(child),
					()-> assertThat(child.getParent())
							.as("부모 세팅 되어있어야 함")
							.isEqualTo(originalParent)
			);

			// When
			child.changeParentCategory(changedParent);

			// Then
			assertAll(
					()-> assertThat(child.getParent())
							.as("부모 Category 변경되어야 함")
							.isEqualTo(changedParent),
					()-> assertThat(originalParent.getChildren())
							.as("기존 부모 Category 의 자식 Category 가 없어져야 함")
							.doesNotContain(child),
					()-> assertThat(changedParent.getChildren())
							.as("신규 부모 Category 의 자식 Category 에 추가 되어야 함")
							.contains(child)
			);


		}

		@Test
		@DisplayName("부모 Category null 로 변경")
		void test_changeParentCategory_to_null() {
			// Given
			Category child = Category.builder()
					.name("coke")
					.build();

			Category parent = Category.builder()
					.name("beverage")
					.children(List.of(child))
					.build();

			assertAll(
					()-> assertThat(parent.getChildren())
							.as("Child 세팅 되어 있어야 함")
							.contains(child),
					()-> assertThat(child.getParent())
							.as("부모 세팅 되어있어야 함")
							.isEqualTo(parent)
			);


			// When
			child.changeParentCategory(null);

			// Then
			assertAll(
					()-> assertThat(child.getParent())
							.as("부모 Category 없어져야 함")
							.isNull(),
					()-> assertThat(parent.getChildren())
							.as("자식 Category 없어져야 함")
							.isEmpty()
			);

		}
	}

}
