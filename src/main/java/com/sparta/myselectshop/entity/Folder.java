package com.sparta.myselectshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "folder")
public class Folder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY) // 폴더를 조회할 때 항상 user를 조회할 필요는 없음
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Folder(String name, User user) {
		this.name = name;
		this.user = user;
	}
}