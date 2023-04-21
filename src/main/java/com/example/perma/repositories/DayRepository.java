package com.example.perma.repositories;

import com.example.perma.models.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {

    List<Day> findDaysByGroup_Id(Integer group_id);

    Day findDayById(Integer id);

    Day findDayByGroupIdAndDayname(Integer groupId, String dayname);
}
