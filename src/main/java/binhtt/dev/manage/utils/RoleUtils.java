package binhtt.dev.manage.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class RoleUtils {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String LEADER = "ROLE_LEADER";
    public static final String MEMBER = "ROLE_MEMBER";

    public static boolean isAdmin(Authentication authentication){
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return roles.get(0).equals(ADMIN);
    }

    public static boolean isLeader(Authentication authentication){
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return roles.get(0).equals(LEADER);
    }

    public static boolean isMember(Authentication authentication){
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return roles.get(0).equals(MEMBER);
    }

    public static boolean isMySelf(Authentication authentication, String studentID){
        return authentication.getName().equals(studentID);
    }
}
