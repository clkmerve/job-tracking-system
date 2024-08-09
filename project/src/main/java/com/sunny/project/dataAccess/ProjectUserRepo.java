package com.sunny.project.dataAccess;

import com.sunny.project.entities.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectUserRepo extends JpaRepository<ProjectUser,Long> {
List<ProjectUser>  findByProjectId(Long projectId);
}
