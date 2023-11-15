package com.sparta.myselectshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderService {

	private final FolderRepository folderRepository;
	public void addFolders(List<String> folderNames, User user) {
		List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);

		List<Folder> folderList = folderNames.stream()
			.filter(folderName -> existFolderList.stream().noneMatch(folder -> folder.getName().equals(folderName)))
			.map(folderName -> new Folder(folderName, user))
			.collect(Collectors.toList());

		folderRepository.saveAll(folderList);
	}

	public List<FolderResponseDto> getFolders(User user) {
		return folderRepository.findAllByUser(user).stream().map(FolderResponseDto::new).collect(Collectors.toList());
	}
}
