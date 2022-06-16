package com.tencoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentNumber;
	
	@Column(nullable = false, length = 20)
	private String name;
	
	private int age;
	
	@Column(nullable = false)
	private int grade;
	private String address;
	
	@Column(nullable = false)
	private String major;
	
	@CreationTimestamp
	private Timestamp enterData;
}
