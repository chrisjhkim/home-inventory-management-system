package com.chrisjhkim.home.repository;

import com.chrisjhkim.home.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;



@DataJpaTest
@Transactional
class ItemJpaRepositoryTest {
	@Autowired private ItemJpaRepository jpaRepository;

	@Nested
	@DisplayName("단순 Entity 테스트 용 ")
	class EntityTest{

		@Test
		@DisplayName("save")
		void test_save(){
			// Test # Given
			Item item = Item.builder().build();
			assertThat(item.getId()).isNull();

			// Test # When
			jpaRepository.save(item);

			// Test # Then
			assertThat(item.getId()).isNotNull();

			System.out.println("item = " + item);
			System.out.println("item.getMemo() = " + item.getMemo());
			System.out.println("item.getMemo() = " + item.getMemo());
			System.out.println("item.getCreatedAt() = " + item.getCreatedAt());
			System.out.println("item.getUpdatedAt() = " + item.getUpdatedAt());
		}
		@Test
		@DisplayName("save & findAll")
		void test_save_and_find_all(){
			// Test # Given
			Item item = Item.builder()
					.name("testName")
					.memo("testMemo")
					.build();
			assertThat(item.getId()).isNull();
			jpaRepository.save(item);

			// Test # When
			List<Item> result = jpaRepository.findAll();

			// Test # Then
			assertThat(result).contains(item);
			assertAll(
					()-> assertThat(item.getName()).isEqualTo("testName"),
					()-> assertThat(item.getMemo()).isEqualTo("testMemo")
			);



		}
	}


}