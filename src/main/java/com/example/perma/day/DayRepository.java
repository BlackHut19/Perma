package com.example.perma.day;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {

    List<Day> findByGroup_Id(Integer group_id);

    Day findDayByGroupIdAndDayname(Integer groupId, String dayname);
}
