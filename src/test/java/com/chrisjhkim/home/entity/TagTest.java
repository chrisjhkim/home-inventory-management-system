package com.chrisjhkim.home.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TagTest {

	@Nested
	@DisplayName("changeParentTag - 연관관계 편의 메서드 테스트 ")
	class MappingMethodTest{


		@Test
		@DisplayName("부모 Tag 변경")
		void test_changeParentTag() {
			// Given
			Tag child = Tag.builder()
					.name("coke")
					.build();

			Tag originalParent = Tag.builder()
					.name("beverage")
					.children(List.of(child))
					.build();

			Tag changedParent = Tag.builder()
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
			child.changeParentTag(changedParent);

			// Then
			assertAll(
					()-> assertThat(child.getParent())
							.as("부모 Tag 변경되어야 함")
							.isEqualTo(changedParent),
					()-> assertThat(originalParent.getChildren())
							.as("기존 부모 Tag 의 자식 Tag 가 없어져야 함")
							.doesNotContain(child),
					()-> assertThat(changedParent.getChildren())
							.as("신규 부모 Tag 의 자식 Tag 에 추가 되어야 함")
							.contains(child)
			);


		}

		@Test
		@DisplayName("부모 Tag null -> 있는 상태 변경")
		void test_changeParentTag_from_null() {
			// Given
			Tag child = Tag.builder()
					.name("coke")
					.build();

			Tag parent = Tag.builder()
					.name("beverage")
					.build();

			assertAll(
					()-> assertThat(parent.getChildren())
							.as("Child 세팅 없어야 함")
							.doesNotContain(child),
					()-> assertThat(child.getParent())
							.as("부모 세팅 없어야 함")
							.isNull()
			);


			// When
			child.changeParentTag(parent);

			// Then
			assertAll(
					()-> assertThat(parent.getChildren())
							.as("Child 세팅 되어 있어야 함")
							.contains(child),
					()-> assertThat(child.getParent())
							.as("부모 세팅 되어있어야 함")
							.isEqualTo(parent)
			);

		}
		@Test
		@DisplayName("부모 Tag null 로 변경")
		void test_changeParentTag_to_null() {
			// Given
			Tag child = Tag.builder()
					.name("coke")
					.build();

			Tag parent = Tag.builder()
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
			child.changeParentTag(null);

			// Then
			assertAll(
					()-> assertThat(child.getParent())
							.as("부모 Tag 없어져야 함")
							.isNull(),
					()-> assertThat(parent.getChildren())
							.as("자식 Tag 없어져야 함")
							.isEmpty()
			);

		}
	}

}
