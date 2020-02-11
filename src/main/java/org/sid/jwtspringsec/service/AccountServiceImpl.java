package org.sid.jwtspringsec.service;

import org.sid.jwtspringsec.entity.AppRole;
import org.sid.jwtspringsec.entity.AppUser;
import org.sid.jwtspringsec.repository.RoleRepository;
import org.sid.jwtspringsec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
        AppUser user = userRepository.findByUsername(username);
        if (user != null) throw new RuntimeException("User already exists");
        if (!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        String hashPW = bCryptPasswordEncoder.encode(password);
        appUser.setPassword(hashPW);
        userRepository.save(appUser);
        addRoleToUse(username, "USER");
        return appUser;
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUse(String username, String roleName) {
        AppRole role = roleRepository.findByRoleName(roleName);
        AppUser user = userRepository.findByUsername(username);
        user.getRoles().add(role);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
