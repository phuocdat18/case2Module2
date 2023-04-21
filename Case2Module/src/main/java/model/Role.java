package model;

public enum Role {
    admin(0,"admin"),
    customer(1,"customer");
    private int id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Role getRoleById(int id) {
        for (Role role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        return null;
    }
    public static Role getRoleByName(String name) {
        for (Role role : values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }
}
