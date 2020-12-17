package binhtt.dev.manage.auth;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account user = accountRepository.findById(s).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("Username or password is incorrect");
        }
        return new UserPrincipal(user);
    }
}
