package ee.icd0004_2020f_weatherproject.enums;

/**
 * DAYTIME - take into account only daytime hours (6.00 – 21.00 included)
 * FULL_DAY - take into account whole day (night hours included).
 *
 * Used in each day average weather calculatins for forecast report
 */
public enum TimeRange {
    DAYTIME(6, 21),
    FULL_DAY(0, 23);

    private int lowerBound;
    private int upperBound;

    private TimeRange(int low, int high) {
        lowerBound = low;
        upperBound = high;
    }

    public boolean isInTimeRange(int hour) {
        return hour >= lowerBound && hour <= upperBound;
    }
}
