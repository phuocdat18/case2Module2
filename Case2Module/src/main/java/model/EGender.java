package model;

public enum EGender {
    male(1,"male"), female(2,"female");
    private int id;
    private String name;

    EGender(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static EGender toGender(int id) {
        for (EGender eGender : values()) {
            if (eGender.id == id) {
                return eGender;
            }
        }
        return null;
    }
    public static EGender getEGenderByName(String name) {
        for (EGender eGender : values()) {
            if (eGender.getName().equals(name)) {
                return eGender;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
