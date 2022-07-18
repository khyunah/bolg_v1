package com.tencoding.blog.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ReplyCountOfBoardDto {
	
	private int id;
	private String content;
	private int replyCount;
	
	// JpaResultMapper와 동일한 컬럼을 맞추는 방법 
//	private Integer id;
//	private String content;
//	private BigInteger replyCount;
	
	// 직접 커스텀 하는 방법
	public ReplyCountOfBoardDto(Object[] objs) {
		this.id = ((Integer)objs[0]).intValue();
		this.content = ((String)objs[1]);
		this.replyCount = ((BigInteger)objs[2]).intValue();
	}
	
	public ReplyCountOfBoardDto(Integer id, String content, BigInteger replyCount) {
		this.id = id.intValue();
		this.content = content;
		this.replyCount = replyCount.intValue();
	}
}
