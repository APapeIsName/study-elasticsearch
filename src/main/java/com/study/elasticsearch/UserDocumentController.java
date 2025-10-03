package com.study.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserDocumentController {

    private final UserDocumentRepository userDocumentRepository;

    public UserDocumentController(UserDocumentRepository userDocumentRepository) {
        this.userDocumentRepository = userDocumentRepository;
    }

    @PostMapping()
    public UserDocument createUser(@RequestBody UserCreateRequestDto requestBody) {
        UserDocument user = new UserDocument(
                requestBody.getId(),
                requestBody.getName(),
                requestBody.getAge(),
                requestBody.getIsActive()
        );

        return userDocumentRepository.save(user);
    }

    @GetMapping()
    public Page<UserDocument> findUsers() {
        return userDocumentRepository.findAll(PageRequest.of(0, 10));
    }

    @GetMapping("/{id}")
    public UserDocument findUserById(@PathVariable String id) {
        return userDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }

    @PutMapping("/{id}")
    public UserDocument updateUser(
            @PathVariable String id, @RequestBody UserUpdateRequestDto requestDto
    ) {
        UserDocument existingUser = userDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        existingUser.setName(requestDto.getName());
        existingUser.setAge(requestDto.getAge());
        existingUser.setIsActive(requestDto.getIsActive());

        return userDocumentRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        UserDocument user = userDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        userDocumentRepository.delete(user);
    }
}
