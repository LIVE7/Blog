package com.project.category;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CategoryDto {

  private Long id;
  @NotBlank
  private String name;
}
