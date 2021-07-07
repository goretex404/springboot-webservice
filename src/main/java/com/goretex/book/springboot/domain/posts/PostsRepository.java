package com.goretex.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    //SpringDataJpa에서 제공하지 않는 메소드는 직접 쿼리로도 작성이 가능하다 (가독성이 좋다.)
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC ")
    List<Posts> findAllDesc();
}
