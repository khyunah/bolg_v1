package com.tencoding.blog.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import com.tencoding.blog.dto.ReplyCountOfBoardDto;

import lombok.RequiredArgsConstructor;

// new 해도되지만 이녀석이 알아서 메모리에 올려줌
@RequiredArgsConstructor
@Repository
public class ReplyCountOfBoardRepository {
	
	// 엔티티에 맴핑해줘야 한다. 
	private final EntityManager em;
	
	public List<ReplyCountOfBoardDto> getReplyCount() {
		List<ReplyCountOfBoardDto> list = new ArrayList<ReplyCountOfBoardDto>();
		
		// 1. 직접 쿼리문 만들기 
		// 주의사항 : 각 줄에 마지막 한칸씩 띄어주기, 마지막에 세미콜론;은 지워주고 한칸 띄어주기 ! ! ! 
		String queryStr = "SELECT a.id, a.content, (SELECT COUNT(boardId) "
				+ "			FROM reply AS b "
				+ "            WHERE b.boardId = a.id) AS replyCount "
				+ "FROM board AS a ";
		Query nativeQuery = em.createNativeQuery(queryStr);
		
		// 여기서 두가지 방법 
		// 1. 직접 문자열을 컨트롤 해서 object 맵핑 방식
		List<Object[]> resultList = nativeQuery.getResultList();
		System.out.println(resultList.toString());
		resultList.forEach(t -> {
			System.out.println(t.toString());
		});
		
		// 2. QLRM 라이브러리 사용해서 object 맵핑 방식
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		list = jpaResultMapper.list(nativeQuery, ReplyCountOfBoardDto.class);
		// 여기까지하면 오류난다 마리아디비는 count함수쓸때 int로 들어오고 
		// MySql은 bigInteger로 들어온다. 
		return list;
	}
}
