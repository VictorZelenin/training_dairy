package data.data_sets;

import data.enums.Gender;

/**
 * Created by victor on 15.05.16.
 */
public class Client {

    private static int ID;

    private long id;
    private String name;
    private String login;
    private String password;
    private Gender gender;
    private int age;
    private double weight;

    public Client() {

    }

    public Client(String name, String login, String password, Gender gender, int age, double weight) {
        id = ++ID;
        this.name = name;
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
    }

    public Client(long id, String name, String login, String password, Gender gender, int age, double weight) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (age != client.age) return false;
        if (Double.compare(client.weight, weight) != 0) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (login != null ? !login.equals(client.login) : client.login != null) return false;
        if (password != null ? !password.equals(client.password) : client.password != null) return false;
        return gender == client.gender;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + age;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", weight=" + weight +
                '}';
    }
}
