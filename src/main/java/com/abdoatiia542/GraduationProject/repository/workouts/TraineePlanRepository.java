package com.abdoatiia542.GraduationProject.repository.workouts;

import com.abdoatiia542.GraduationProject.model.Trainee;
import com.abdoatiia542.GraduationProject.model.exercises.TraineePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TraineePlanRepository extends JpaRepository<TraineePlan, Long> {

    @Query("SELECT tp FROM TraineePlan tp " +
            "JOIN FETCH tp.plan p " +
            "WHERE tp.trainee.id = :traineeId " +
            "AND tp.startDate <= :currentDate " +
            "ORDER BY tp.startDate DESC")
    Optional<TraineePlan> findActiveTraineePlanWithDetails(@Param("traineeId") Long traineeId,
                                                           @Param("currentDate") LocalDate currentDate);

    @Query("SELECT tp FROM TraineePlan tp " +
            "WHERE tp.trainee.id = :traineeId " +
            "AND tp.startDate <= :currentDate " +
            "ORDER BY tp.startDate DESC")
    Optional<TraineePlan> findActiveTraineePlan(@Param("traineeId") Long traineeId,
                                                @Param("currentDate") LocalDate currentDate);


    List<TraineePlan> findAllByTrainee(Trainee traineeId);


}
