package com.example.demo.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class GetMessageChunkService {

    final ChatMessageRepository chatMessageRepository;
    final EntityManager entityManager;

    @Autowired
    public GetMessageChunkService(ChatMessageRepository chatMessageRepository,
                                  EntityManager entityManager) {
        this.chatMessageRepository = chatMessageRepository;
        this.entityManager = entityManager;
    }

    public List<ChatMessage> execute(@Nullable Integer limitId) {

        HibernateCriteriaBuilder builder = (HibernateCriteriaBuilder) entityManager.getCriteriaBuilder();

        CriteriaQuery<ChatMessage> cq = builder.createQuery(ChatMessage.class);

        Root<ChatMessage> root = cq.from(ChatMessage.class);

        Predicate where = builder.conjunction();

        if (!isNull(limitId)) {
            where = builder.and(where, builder.lessThan(root.get("id"), limitId));
        }

        cq.select(root).where(where).orderBy(builder.desc(root.get("id")));

        List<ChatMessage> chunk = entityManager.createQuery(cq).setMaxResults(30).getResultList();

        Collections.reverse(chunk);

        return chunk;
    }
}
