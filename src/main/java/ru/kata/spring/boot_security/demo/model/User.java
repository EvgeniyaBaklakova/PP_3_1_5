package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "users")
@Component
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    public String name;
    @Column(name = "surname")
    public String surName;
    @Column(name = "age")
    public int age;
    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "authorities",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(int id, String name,
                String surName,
                int age,
                boolean isActive,
                String userName,
                String password,
                Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.isActive = isActive;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public void addRoles(Role role) {
        roles.add(role);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (id == null ? 0 : (int) id >>> 16);
        hash = 31 * hash + (userName == null ? 0 : userName.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}