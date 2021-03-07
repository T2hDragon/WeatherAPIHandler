package ee.icd0004_2020f_weatherproject.enums;

public enum TemperatureUnit {
    KELVIN("standard"),
    CELSIUS("metric"),
    FAHRENHEIT("imperial");

    private final String unit;

    TemperatureUnit(String unitSystem) {
        this.unit = unitSystem;
    }

    public String getUnitString() {
        return unit;
    }

    public String getStringRepresentation() {
        switch (this) {
            case KELVIN: return "Kelvin";
            case CELSIUS: return "Celsius";
            case FAHRENHEIT: return "Fahrenheit";
        }
        throw new RuntimeException("Should not be here");
    }
}
