package model;

public enum EStatus {
    PAID(1,"PAID"),UNPAID(2,"UNPAID");
    private int id;
    private String name;
    EStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
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
    public static EStatus getStatusById(int id) {
        for (EStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }
    public static EStatus getStatusByName(String name) {
        for (EStatus Status : values()) {
            if (Status.getName().equals(name)) {
                return Status;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
