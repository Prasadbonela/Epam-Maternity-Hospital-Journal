package com.epam.rd.autotasks.collections;

class WeekDay {
    private final int day;

    private WeekDay(int day) {
        this.day = day;
    }
    public static final WeekDay MONDAY = new WeekDay(0);
    public static final WeekDay TUESDAY = new WeekDay(1);
    public static final WeekDay WEDNESDAY = new WeekDay(2);
    public static final WeekDay THURSDAY = new WeekDay(3);
    public static final WeekDay FRIDAY = new WeekDay(4);
    public static final WeekDay SATURDAY = new WeekDay(5);
    public static final WeekDay SUNDAY = new WeekDay(6);

    static final WeekDay[] values = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

    public int day() { return day;}
    public static WeekDay fromValue(int value) {
        return values[value];
    }
    @Override
    public String toString() {
        return "" + day;
    }

    static WeekDay[] values() {
        return values;
    }
}
