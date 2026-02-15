package com.example.dto.filter;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.QueryValue;

public class RequesterFiltersDTO implements Pageable {
    @Nullable
    @QueryValue
    private Long id;
    
    @Nullable
    @QueryValue
    private String fullName;
    
    @Nullable
    @QueryValue
    private String email;
    
    @Nullable
    @QueryValue
    private String department;
    
    @Nullable
    @QueryValue
    private String userName;
    
    @Nullable
    @QueryValue
    private String phoneNumber;
    
    @Nullable
    @QueryValue
    private Integer size;
    
    @Nullable
    @QueryValue
    private Integer page;

    public RequesterFiltersDTO() {
    }

    public RequesterFiltersDTO(Long id, String fullName, String email, String department, String userName, String phoneNumber, Integer size, Integer page) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.size = size;
        this.page = page;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public int getNumber() {
        return page != null ? page : 0;
    }

    @Override
    public int getSize() {
        return size != null ? size : 10;
    }
}
