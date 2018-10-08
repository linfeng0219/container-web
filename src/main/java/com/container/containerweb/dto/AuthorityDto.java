package com.container.containerweb.dto;

import java.util.List;

public class AuthorityDto {

    private Integer subject;

    private List<Integer> authorityIds;

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public List<Integer> getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(List<Integer> authorityIds) {
        this.authorityIds = authorityIds;
    }
}
