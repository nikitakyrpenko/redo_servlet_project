package entity;

import enums.Role;

import java.util.Objects;

public class UserEntity {

    private final Integer id;
    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    private final String telephone;
    private final Role role;


    private UserEntity(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.password = builder.password;
        this.telephone = builder.telephone;
        this.role = builder.role;

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getTelephone() {
        return telephone;
    }

    public Role getRole() {
        return role;
    }



    public static Builder builder(){ return new Builder();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id) &&
                Objects.equals(name, userEntity.name) &&
                Objects.equals(surname, userEntity.surname) &&
                Objects.equals(email, userEntity.email) &&
                Objects.equals(password, userEntity.password) &&
                Objects.equals(telephone, userEntity.telephone) &&
                role == userEntity.role ;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", role=" + role +
                '}'+"\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, telephone, role);
    }

    public static class Builder{
        private Integer id;
        private String name;
        private String surname;
        private String email;
        private String password;
        private String telephone;
        private Role role;

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }
        public Builder withName(String name){
            this.name = name;
            return this;
        }
        public Builder withSurname(String surname){
            this.surname = surname;
            return this;
        }
        public Builder withEmail(String email){
            this.email = email;
            return this;
        }
        public Builder withPassword(String password){
            this.password = password;
            return this;
        }
        public Builder withTelephone(String telephone){
            this.telephone = telephone;
            return this;
        }
        public Builder withRole(Role role){
            this.role = role;
            return this;
        }

        public UserEntity build(){
            return new UserEntity(this);
        }
    }
}
