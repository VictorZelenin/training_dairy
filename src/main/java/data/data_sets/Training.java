package data.data_sets;

/**
 * Created by victor on 15.05.16.
 */
public class Training {

    private static int ID;

    private long id;
    private String trainingName;


    public Training() {
    }

    public Training(String trainingName) {
        id = ++ID;
        this.trainingName = trainingName;
    }

    public Training(long id, String trainingName) {
        this.id = id;
        this.trainingName = trainingName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Training training = (Training) o;

        if (id != training.id) return false;
        return trainingName != null ? trainingName.equals(training.trainingName) : training.trainingName == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (trainingName != null ? trainingName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainingName='" + trainingName + '\'' +
                '}';
    }
}
