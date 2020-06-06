package com.project.index;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.config.Navigation;
import com.project.config.Section;
import com.project.post.Post;
import com.project.post.PostRepository;
import com.project.post.PostStatus;

import static org.springframework.data.domain.ExampleMatcher.matching;


@Controller
@RequiredArgsConstructor
@Navigation(Section.HOME)
public class IndexController {

  private final PostRepository postRepository;

  @GetMapping("/")
  public String home(@RequestParam(required = false) String q, Model model, @PageableDefault(size = 5, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable){
    Example<Post> post = Example.of(new Post(q, PostStatus.Y),
      matching()
        .withMatcher("title", ExampleMatcher.GenericPropertyMatcher::contains));
    model.addAttribute("posts", postRepository.findAll(post, pageable));
    return "index";
  }
}
