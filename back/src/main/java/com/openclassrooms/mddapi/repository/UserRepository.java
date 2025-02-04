package com.openclassrooms.mddapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(final String email);

    public Optional<User> findByUserName(final String userName);

    public Boolean existsByEmail(final String email);

    public Boolean existsByUserName(final String userName);

    public List<User> findBySubscriptionsContaining(final Topic topic);

    // TODO: Voir pour le tri du feed directement avec @Query et fetch join
}
