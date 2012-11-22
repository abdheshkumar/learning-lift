package de.java.services;

import de.java.entities.Article;
import de.java.entities.ArticlePreview;
import de.java.entities.Category;
import de.java.entities.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: christian
 * Date: 11/20/12
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BlogJavaService {

	/**
	 * simply create for this example some entity classes to provide some content for the
	 * web client...
	 */
	private static List<Category> categories = new ArrayList<>();
	private static List<Article> articles = new ArrayList<>();
	private static List<ArticlePreview> articlePreviews = new ArrayList<>();
	private static List<Tag> tags = new ArrayList<>();

	static {
		long id = 0;
		Category admin = new Category(id++, "Administration");
		Category dev = new Category(id++, "Development");

		Category lift = new Category(id++, "Lift Framework", dev);
		Category scala = new Category(id++, "Scala", dev);

		Category linux = new Category(id++, "Linux", admin);

		categories.add(admin);
		categories.add(dev);
		categories.add(lift);
		categories.add(scala);
		categories.add(linux);

		Tag hello = new Tag(id++, "hello");
		Tag world = new Tag(id++, "World");

		tags.add(hello);
		tags.add(world);

		createArticleAndPreview(id++, "Learning lift", getContent(), lift, hello);
		createArticleAndPreview(id++, "Asking the friendly and very helpful Lift googlegroup for support", getContent(), lift, world);
		createArticleAndPreview(id++, "Learning Scala", getContent(), scala, world);
	}

	private static void createArticleAndPreview(Long articleId, String title, String content, Category category, Tag tag) {
		Article article = new Article(articleId, category, content, title, tag);
		ArticlePreview preview = new ArticlePreview(articleId, category, tag, title);
		articles.add(article);
		articlePreviews.add(preview);
	}

	private static String getContent() {
		return "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore" +
				" et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum." +
				" Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit " +
				"amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam " +
				"erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, " +
				"no sea takimata sanctus est Lorem ipsum dolor sit amet.";
	}

	public static List<Category> getMainCategories() {
		List<Category> main = new ArrayList<>();
		for (Category category : categories) {
			if (category.getParent() == null) main.add(category);
		}
		return main;
	}

	public static List<Category> getSubCategories(Long parentId) {
		List<Category> main = new ArrayList<>();
		for (Category category : categories) {
			Category parent = category.getParent();
			if (parent != null && parent.getId().equals(parentId)) main.add(category);
		}
		return main;
	}

	public static List<Tag> getTags() {
		return tags;
	}

	public static List<ArticlePreview> getAllArticlePreviews() {
		return articlePreviews;
	}

	public static List<ArticlePreview> getArticlePreviewsOfCategory(Long id) {
		List<ArticlePreview> previews = new ArrayList<>();
		for (ArticlePreview preview : articlePreviews) {
			if (preview.getCategory().getId().equals(id)) previews.add(preview);
		}
		return previews;
	}

	public static List<ArticlePreview> getArticlePreviewsOfCategory(String name) {
		List<ArticlePreview> previews = new ArrayList<>();
		for (ArticlePreview preview : articlePreviews) {
			if (preview.getCategory().getName().equals(name)) previews.add(preview);
		}
		return previews;
	}

	public static List<ArticlePreview> getArticlePreviewsOfTag(String name) {
		List<ArticlePreview> previews = new ArrayList<>();
		for (ArticlePreview preview : articlePreviews) {
			Tag tag = preview.getTag();
			if (tag == null) continue;
			if (tag.getName().equals(name)) previews.add(preview);
		}
		return previews;
	}

	public static Article getArticle(Long articleId) {
		for (Article article : articles) {
			if (article.getId().equals(articleId)) return article;
		}
		return null;
	}
}
