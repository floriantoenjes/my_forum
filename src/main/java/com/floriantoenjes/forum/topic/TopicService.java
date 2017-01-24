package com.floriantoenjes.forum.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;

    public List<Topic> findAll() {
        return (List<Topic>) topicRepository.findAll();
    }

    public List<Topic> findAll(PageRequest pageRequest) {
        return topicRepository.findAll(pageRequest).getContent();
    }

    public Topic findOne(Long id) {
        return topicRepository.findOne(id);
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }
}
