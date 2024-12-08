package com.elmeftouhi.expensez.expensetag;

import com.elmeftouhi.expensez.exception.TagNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagResponse> getAll() {
        return tagRepository.findAll().stream().map(tag -> new TagResponse(tag.getId(), tag.getTagName(), tag.getColor(), Set.of())).toList();
    }

    @Override
    public TagResponse getById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isEmpty())
            throw new TagNotFoundException();

        return new TagResponse(tag.get().getId(), tag.get().getTagName(), tag.get().getColor(), tag.get().getExpenses());
    }

    @Override
    public void updateTag(TagResource tagResource, Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isEmpty())
            throw new TagNotFoundException();

        Tag tagToUpdate = new Tag(
                id,
                tagResource.tag(),
                tagResource.color()
        );
        tagRepository.save(tagToUpdate);
    }

    @Override
    public void deleteTag(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isEmpty())
            throw new TagNotFoundException();

        tagRepository.delete(tag.get());
    }

    @Override
    public void create(TagResource tagResource) {
        tagRepository.save(new Tag(
                    tagResource.tag(),
                    tagResource.color()
                )
        );
    }
}
