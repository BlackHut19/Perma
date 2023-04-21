package com.example.perma.services;

import com.example.perma.models.Day;
import com.example.perma.models.User;
import com.example.perma.repositories.DayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayService {

    private final DayRepository dayRepository;

    public Day loadDayByGroupIdAndDayname(Integer groupId, String dayname) {
        return dayRepository.findDayByGroupIdAndDayname(groupId, dayname);
    }

    public List<Day> loadDaysByGroupId(Integer groupId){
        return dayRepository.findDaysByGroup_Id(groupId);
    }

    public void save(Day day){
        dayRepository.save(day);
    }

    public void delete(Day day){
        dayRepository.delete(day);
    }

    public void addUser(User user, Day day){

    }

    public Day loadDayByDayId(Integer dayId){
        return dayRepository.findDayById(dayId);
    }


}
