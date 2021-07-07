package com.goretex.book.springboot.web;


import com.goretex.book.springboot.service.posts.PostsService;
import com.goretex.book.springboot.web.dto.PostsResponseDto;
import com.goretex.book.springboot.web.dto.PostsSaveRequestDto;
import com.goretex.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // 인수가없는 생성자, final / @NonNull 각 필드에 또는 모든 필드에 대해 인수를 사용하는 생성자를 생성합니다.
@RestController // Spring MVC Controller에 @ResponseBody가 추가된 것. 당연히 RestController의 주용도는 Json 형태로 객체 데이터를 반환하는 것입니다.
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
