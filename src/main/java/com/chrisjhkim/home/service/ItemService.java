package com.chrisjhkim.home.service;

import jakarta.transaction.Transactional;

public interface ItemService {



	@Transactional
	ItemDto.Response createItem(ItemDto.CreateRequest createRequest);
}
