package com.juliandev.techie.springblog.repository;

import com.juliandev.techie.springblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
