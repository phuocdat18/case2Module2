package model;

public enum Status {
    FREE(1,"FREE"),BUSY(2,"BUSY");
    private int id;
    private String name;
    Status(int id, String name) {
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
    public static Status getStatusById(int id) {
        for (Status status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }
    public static Status getStatusByName(String name) {
        for (Status status : values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
