package com.dlrs.dlrsdemo.repository;

import com.dlrs.dlrsdemo.model.AppUser;
import com.dlrs.dlrsdemo.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(value = "SELECT * FROM app_user t WHERE t.surveyors = :teamId", nativeQuery = true)
    List<AppUser> findTeamWithSurveyors(@Param("teamId") Team teamId);

}
