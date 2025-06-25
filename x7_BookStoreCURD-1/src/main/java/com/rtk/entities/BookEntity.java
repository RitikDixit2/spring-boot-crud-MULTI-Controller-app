package com.rtk.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String activeSw = "T";
    
	// name autheror price activesw
	String bookname;
	String author;
	Double price;

	
}
