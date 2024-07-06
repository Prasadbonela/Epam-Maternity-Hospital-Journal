package com.epam.rd.autotasks.collections;

import java.util.*;

public class BirthJournalManagementImpl implements BirthJournalManagement {

    Map<WeekDay, List<Baby>> book = new TreeMap<>();
    List<Baby> babies = new ArrayList<>();

    @Override
    public boolean addEntryOfBaby(WeekDay day, Baby baby) {

        if (day != null && baby.getName().length() > 0
                && baby.getHeight() > 0
                && baby.getWeight() > 0
                && baby.getGender().length() > 0) {
            babies.add(baby);

            book.put(day, babies);
            return true;
        }
        return false;
    }

    @Override
    public void commit() {
    }

    @Override
    public int amountBabies() {
        return babies.size();
    }

    @Override
    public List<Baby> findBabyWithHighestWeight(String gender) {
        List<Baby> result = new ArrayList<>();
        double maxWeight = Double.MIN_VALUE;
        babies.sort(new Comparator<Baby>() {
            @Override
            public int compare(Baby o1, Baby o2) {
                return Double.compare(o2.getWeight(), o1.getWeight());
            }
        });

        for (List<Baby> babies : book.values()) {
            for (Baby baby : babies) {
                if (baby.getGender().equals(gender)) {
                    if (baby.getWeight() > maxWeight) {
                        maxWeight = baby.getWeight();
                        result.add(baby);
                    } else if (baby.getWeight() == maxWeight) {
                        result.add(baby);
                    }
                }
            }
            break;
        }
        if (result.size() > 1) {
            result.sort(new Comparator<Baby>() {
                @Override
                public int compare(Baby b1, Baby b2) {
                    return b1.getName().compareTo(b2.getName());
                }
            });
        }


        return Collections.unmodifiableList(result);
    }

    @Override
    public List<Baby> findBabyWithSmallestHeight(String gender) {
        List<Baby> result = new ArrayList<>();
        double minHeight = Double.MAX_VALUE;
        babies.sort(new Comparator<Baby>() {
            @Override
            public int compare(Baby b1, Baby b2) {
                return b1.getHeight() - b2.getHeight();
            }
        });

        for (List<Baby> babies : book.values()) {
            for (Baby baby : babies) {
                if (baby.getGender().equals(gender)) {
                    if (baby.getHeight() < minHeight) {
                        minHeight = baby.getHeight();
                        result.add(baby);
                    } else if (baby.getHeight() == minHeight) {
                        result.add(baby);
                    }
                }
            }
            break;
        }
        if (result.size() > 1) {
            sortOfIncreasingWeight(result);
        }
        return Collections.unmodifiableList(result);
    }

    private void sortOfIncreasingWeight(List<Baby> result) {
        result.sort(new Comparator<Baby>() {
            @Override
            public int compare(Baby w1, Baby w2) {
                return Double.compare(w1.getWeight(), w2.getWeight());
            }
        });
    }

    @Override
    public Set<Baby> findBabiesByBirthTime(String from, String to) {
        Set<Baby> result = new HashSet<>();
        boolean isNoon = false;
        for (List<Baby> babies : book.values()) {

            for (Baby baby : babies) {
                String birthTime = baby.getTime();
                if (birthTime.equals("0:0") || birthTime.equals("00:00")) {
                    isNoon = true;
                }

                String[] split = birthTime.split(":");
                if (split[0].length() == 1 && ! isNoon) {
                    birthTime = "0" + birthTime;
                }
                if (split[1].length() == 1 && ! isNoon) {
                    birthTime = birthTime + "0";
                }

                if (birthTime.compareTo(from) >= 0 && birthTime.compareTo(to) <= 0 || isNoon) {
                    result.add(baby);
                    isNoon = false;
                }
            }
            break;
        }
        return result;
    }
}