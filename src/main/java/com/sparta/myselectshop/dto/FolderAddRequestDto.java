package com.sparta.myselectshop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // Protected로 하면 안된다. 왜?
public class FolderAddRequestDto {
	private List<String> folderNames;
}
