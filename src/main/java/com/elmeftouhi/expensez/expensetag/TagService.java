package com.elmeftouhi.expensez.expensetag;

import java.util.List;

public interface TagService {

    List<TagResponse> getAll();

    TagResponse getById(Long id);

    void updateTag(TagResource tagResource, Long id);

    void deleteTag(Long id);

    void create(TagResource tagResource);
}
