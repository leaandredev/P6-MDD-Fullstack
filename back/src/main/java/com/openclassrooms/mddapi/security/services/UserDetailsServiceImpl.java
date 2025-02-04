package com.openclassrooms.mddapi.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  UserDetailsServiceImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String identifier) throws UsernameNotFoundException {
    final User user = userRepository.findByUserName(identifier)
        .or(() -> userRepository.findByEmail(identifier))
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with identifier: " + identifier));

    return UserDetailsImpl
        .builder()
        .id(user.getId())
        .username(user.getUserName())
        .email(user.getEmail())
        .password(user.getPassword())
        .build();
  }

}
