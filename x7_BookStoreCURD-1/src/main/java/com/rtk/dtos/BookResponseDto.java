package com.rtk.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @AllArgsConstructor

public class BookResponseDto {
	String bookname;
	String author;
	Double price;
}
