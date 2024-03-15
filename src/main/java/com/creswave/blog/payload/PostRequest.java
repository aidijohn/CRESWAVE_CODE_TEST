package com.creswave.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {

	@NotBlank
	@Size(min = 8)
	private String title;

	@NotBlank
	@Size(min = 20)
	private String body;

}
