package com.goretex.book.springboot.service.posts;

import com.goretex.book.springboot.domain.posts.Posts;
import com.goretex.book.springboot.domain.posts.PostsRepository;
import com.goretex.book.springboot.web.dto.PostsListResponseDto;
import com.goretex.book.springboot.web.dto.PostsResponseDto;
import com.goretex.book.springboot.web.dto.PostsSaveRequestDto;
import com.goretex.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

//생성자로 Bean 객체를 받도록 하면 @Autowired와 동일한 효과를 볼 수 있다.
//또한 lombok 어노테이션 (@RequiredArgsConstructor)을 사용하면 생성자 코드를 계속해서 수정 할 필요가 없어진다.
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // readOnly = ture는 트랜잭션 범위는 유지하고 조회기능만 남겨 조회 속도 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                //.map(posts -> new PostsListResponseDto(posts)) 와 동일.
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" +id));

        //JpaRepository에서 delete 메소드를 지원하고 있다.
        postsRepository.delete(posts);
    }

}
