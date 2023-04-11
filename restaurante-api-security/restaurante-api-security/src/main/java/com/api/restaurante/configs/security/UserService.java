package com.api.restaurante.configs.security;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import com.api.parkingcontrol.models.Usuario;
//import com.api.parkingcontrol.repositories.UserRepository;
//@Service
//public class UserService implements UserDetailsService {
//	
//	@Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Usuario usuario = userRepository.findByLogin(username);
////                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
//
//        return new org.springframework.security.core.userdetails.User(usuario.getLogin(),
//                usuario.getPassword(),
//                usuario.isAtivo(),
//                true,
//                true,
//                true,
//                usuario.getAuthorities());
//    }
//}
