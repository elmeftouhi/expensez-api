package com.elmeftouhi.expensez.expensetag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    List<TagResponse> findAll(){
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    TagResponse findById(@PathVariable Long id){
        return tagService.getById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody TagResource tagResource
    ){
        tagService.updateTag(tagResource, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id){
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping()
    ResponseEntity<Void> create(@RequestBody TagResource tagResource){
        tagService.create(tagResource);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
