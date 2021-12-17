package recipes.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import recipes.config.RecipeUserDetails;
import recipes.domain.entities.User;
import recipes.domain.repositories.UserRepository;

@Service
public class RecipeUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public RecipeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException(email + " NOT FOUND."));
        return new RecipeUserDetails(user);
    }
}
