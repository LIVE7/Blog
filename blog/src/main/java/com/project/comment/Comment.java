package com.project.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.project.post.Post;
import com.project.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@ToString(exclude = {"post"})
@EqualsAndHashCode(exclude = {"post"})
public class Comment {
  @Id
  @GeneratedValue
  private Long id;

  private String content;

  private LocalDateTime regDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "POST_ID")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  public Comment(String content, Post post, User user) {
    this.content = content;
    this.post = post;
    this.user = user;
  }

  Comment() {

  }
}
