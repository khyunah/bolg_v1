package com.tencoding.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 같은 변수의 이름으로 데이터 타입을 다르게 사용해야 될때는 제네릭 프로그래밍 을 생각하자.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

	private int status;
	private T data;
}
