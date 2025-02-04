package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPost(final Post post);
}
