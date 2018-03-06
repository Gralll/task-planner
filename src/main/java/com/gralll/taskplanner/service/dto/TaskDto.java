package com.gralll.taskplanner.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaskDto {

    private Long id;

    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    private String category;

    @NotNull
    private String status;

    @NotNull
    private Boolean isActive;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TaskDto taskDto = (TaskDto) o;

        return new EqualsBuilder()
                .append(id, taskDto.id)
                .append(name, taskDto.name)
                .append(category, taskDto.category)
                .append(status, taskDto.status)
                .append(isActive, taskDto.isActive)
                .append(userId, taskDto.userId)
                .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(category)
                .append(status)
                .append(isActive)
                .append(userId)
                .toHashCode();
    }
}
