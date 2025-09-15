package pack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.http.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import pack.domain.Article;
import pack.dto.AddArticleRequest;
import pack.repository.BlogRepository;


@SpringBootTest
@AutoConfigureMockMvc  // MockMvc 생성 및 자동 구성
// H2 콘솔에 접속해 쿼리를 입력해 데이터가 저장되는지 확인하는 과정을 줄여주는 테스트 코드
public class BlogApiControllerTest {
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;	// 직렬화, 역직렬화를 위한 클래스
	
	@Autowired
	protected WebApplicationContext context;
	
	@Autowired
	BlogRepository blogRepository;
	
	@BeforeEach  // 테스트 실행 전 실행하는 메서드
	public void mockMvcSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
		blogRepository.deleteAll();
	}
	
	@DisplayName("addArticle: 블로그 글 추가에 성공한다.")
	@Test
	public void addArticle() throws Exception{
		// given: 블로그 글 추가에 필요한 요청 객체를 만듦
		final String url = "/api/articles";
		final String title = "title";
		final String content = "content";
		final AddArticleRequest userRequest = new AddArticleRequest(title, content);
		
		// 객체 JSON으로 직렬화
		final String requestBody = objectMapper.writeValueAsString(userRequest);
		
		// when: 블로그 글 추가 api에 요청을 보냄. given 절에서 미리 만들어둔 객체를 요청 본문으로 함께 보냄
		// 설정한 내용을 바탕으로 요청 전송
		ResultActions result = mockMvc.perform(post(url)
		        .contentType(MediaType.APPLICATION_JSON_VALUE)
		        .content(requestBody));
				
		// then: 응답코드가 201 created 인지 확인. blog를 전체 조회하여 크기가 1인지 확인하고 실제로 저장된 데이터와 요청값을 비교
		result.andExpect(status().isCreated()); 
		List<Article> articles = blogRepository.findAll();
		
		assertThat(articles.size()).isEqualTo(1);		// 크기가 1인지 검증
		assertThat(articles.get(0).getTitle()).isEqualTo(title);
		assertThat(articles.get(0).getContent()).isEqualTo(content);
	}
	
	@DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
	@Test
	public void findAllArticles() throws Exception{
		// given: 블로그 글을 저장
		final String url = "/api/articles";
		final String title = "title";
		final String content = "content";
		
		blogRepository.save(Article.builder()
			.title(title)
			.content(content)
			.build());
		
		// when: 목록 조회 api를 호출
		final ResultActions resultActions = mockMvc.perform(get(url)
				.accept(MediaType.APPLICATION_JSON));
		
		// then: 응답 코드가 200 ok이고, 반환받은 값 중에 0 번째 요소의 content와 title이 저장된 값이 같은지 확인
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].content").value(content))
			.andExpect(jsonPath("$[0].title").value(title));
	
	}
}
