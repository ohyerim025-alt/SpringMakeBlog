package pack.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import pack.dto.ArticleListViewResponse;
import pack.service.BlogService;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
	private final BlogService blogService;
	
	@GetMapping("/articles")
	public String getArticles(Model model) {
		List<ArticleListViewResponse> articles = blogService.findAll().stream()
				.map(ArticleListViewResponse::new)
				.toList();
		model.addAttribute("articles", articles);
		
		return "articleList";
	}
}
