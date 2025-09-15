package pack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pack.Dto;
import pack.domain.Article;
import pack.dto.AddArticleRequest;
import pack.repository.BlogRepository;

@RequiredArgsConstructor
@Service
public class BlogService {
	@Autowired
	BlogRepository blogRepository;
	
	// 블로그 글 추가 메서드
	public Article save(AddArticleRequest request) {
		return blogRepository.save(request.toEntity());
	}
	
	// 데이터베이스에 저장되어 있는 글을 모두 가져오는 메서드
	public List<Article> findAll(){
		return blogRepository.findAll();
	}
	
}
