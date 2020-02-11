package org.sid.jwtspringsec.service;

import org.sid.jwtspringsec.entity.AppRole;
import org.sid.jwtspringsec.entity.AppUser;

public interface AccountService {
    public AppUser saveUser(String username, String password, String confirmedPassword);
    public AppRole saveRole(AppRole role);
    public void addRoleToUse(String username, String roleName);
    public AppUser findUserByUsername(String username);
}
