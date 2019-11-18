package com.example.blog.api

import com.example.blog.domain.Article
import com.example.blog.domain.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ArticleController(private val articleRepository: ArticleRepository) {

    @GetMapping("/articles")
    fun getAllArticles() : List<Article> =
            articleRepository.findAll()

    @PostMapping("/articles")
    fun createArticle(@Valid article: Article): Article =
            articleRepository.save(article)

    @GetMapping("/articles/{id}")
    fun updateArticle(@PathVariable(value = "id") articleId: Long,
                      @Valid @RequestBody newArticle: Article): ResponseEntity<Article> {

        return articleRepository.findById(articleId)
                .map { existingArticle ->
                    val updatedArticle: Article = existingArticle
                            .copy(title = newArticle.title, content = newArticle.content)
                    ResponseEntity.ok().body(articleRepository.save(updatedArticle))
                }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/articles/{id}")
    fun deleteArticleById(@PathVariable(value = "id") aricleId: Long): ResponseEntity<Void> {

        return articleRepository.findById(aricleId).map { article ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}