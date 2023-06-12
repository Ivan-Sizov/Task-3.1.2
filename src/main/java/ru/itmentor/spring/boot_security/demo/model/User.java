package ru.itmentor.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name= "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private int age;
    @Column
    private String email;
    @Column
    private boolean enabled = true;
    @Column
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<UserRole> roles;
    public User() {}

    public User(String username, String name, String surname, int age, String email, boolean enabled, String password, Set<UserRole> roles) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.roles = roles;
    }

    public void addRole(UserRole role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
        role.setUser(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<UserRole> roles = getRoles();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (UserRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
        for (UserRole role : roles) {
            role.setUser(this);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"username\":\"" + username + "\"" +
                ", \"name\":\"" + name + "\"" +
                ", \"surname\":\"" + surname + "\"" +
                ", \"age\":" + age +
                ", \"email\":\"" + email + "\"" +
                ", \"password\":\"" + password + "\"" +
                ", \"roles\":" + rolesToString(roles) +
                '}';
    }

    private String rolesToString(Set<UserRole> roles) {
        if (roles == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (UserRole role : roles) {
            sb.append("\"").append(role.getRole()).append("\", ");
        }
        if (!roles.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }
}
