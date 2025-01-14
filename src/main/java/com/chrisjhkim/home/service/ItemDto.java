package com.chrisjhkim.home.service;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ItemDto {

	@Getter
	@Builder
	public static class CreateRequest{
		private final String name;
		private final String description;
		private final Long tagId;
	}

	@Getter
	@Builder
	public static class Response {
		private final Long id;
		private final String name;
		private final String description;
		private final LocalDateTime createdAt;
		private final LocalDateTime updatedAt;
	}

}
