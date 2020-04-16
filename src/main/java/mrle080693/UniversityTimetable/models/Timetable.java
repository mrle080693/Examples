package mrle080693.UniversityTimetable.models;

import java.util.List;

public class Timetable {
    private List<DayTimetable> daysTimetable;

    public Timetable() {
    }

    public Timetable(List<DayTimetable> daysTimetable) {
        this.daysTimetable = daysTimetable;
    }

    public List<DayTimetable> getDaysTimetable() {
        return daysTimetable;
    }

    public void setDaysTimetable(List<DayTimetable> daysTimetable) {
        this.daysTimetable = daysTimetable;
    }
}
