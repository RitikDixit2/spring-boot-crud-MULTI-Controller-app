package com.rtk.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Contains Data Transfer Objects used for request and response payloads.
 */

@Getter
@Setter
@NoArgsConstructor
public class BookRequestDTO {

	String bookname;
	String author;
	Double price;
}
