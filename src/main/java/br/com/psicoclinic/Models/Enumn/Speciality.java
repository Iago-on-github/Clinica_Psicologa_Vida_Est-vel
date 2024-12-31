package br.com.psicoclinic.Models.Enumn;

public enum Speciality {
    CHILD_PSYCHOLOGY(1),
    ADULT_PSYCHOLOGY(2),
    ELDERLY_PSYCHOLOGY(3);
    private int code;

    Speciality(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
