package pack.dto;

import lombok.Getter;
import pack.domain.Article;

@Getter
public class ArticleListViewResponse {
	private final Long id;
	private final String titleString;
	private final String content;
	
	public ArticleListViewResponse(Article article) {
		this.id = article.getId();
		this.titleString = article.getTitle();
		this.content = article.getContent();
	}
}
