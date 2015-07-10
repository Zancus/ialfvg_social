package it.barker.barker;

import java.util.Comparator;

import it.barker.models.Bark;

public class DateComparator implements Comparator<Bark> {
    @Override
    public int compare(Bark o1, Bark o2) {
        return o1.date.compareTo(o2.date) * -1;
    }
}