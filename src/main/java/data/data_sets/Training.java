package data.data_sets;

/**
 * Created by victor on 15.05.16.
 */
public class Training {

    private static int ID;

    private long id;
    private String trainingName;

    private Client client;

    public Training() {

    }

    public Training(String trainingName, Client client) {
        id = ++ID;
        this.trainingName = trainingName;
        this.client = client;
    }

    public Training(long id, String trainingName, Client client) {
        this.id = id;
        this.trainingName = trainingName;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Training training = (Training) o;

        if (id != training.id) return false;
        if (trainingName != null ? !trainingName.equals(training.trainingName) : training.trainingName != null)
            return false;
        return client != null ? client.equals(training.client) : training.client == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (trainingName != null ? trainingName.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainingName='" + trainingName + '\'' +
                ", client=" + client +
                '}';
    }
}
