package data.data_sets;

import java.sql.Date;

/**
 * Created by victor on 15.05.16.
 */
public class TrainingsResult {

    private static int ID;

    private long id;
    private Date date;
    private Client client;
    private Exercise exercise;
    private int approaches;
    private int repetitions;
    private int expectedTrainingWeight;
    private int realTrainingWeight;

    public TrainingsResult() {

    }

    public TrainingsResult(Date date, Client client, Exercise exercise, int approaches,
                           int repetitions, int expectedTrainingWeight, int realTrainingWeight) {
        id = ++ID;
        this.date = date;
        this.client = client;
        this.exercise = exercise;
        this.approaches = approaches;
        this.repetitions = repetitions;
        this.expectedTrainingWeight = expectedTrainingWeight;
        this.realTrainingWeight = realTrainingWeight;
    }

    public TrainingsResult(long id, Date date, Client client, Exercise exercise, int approaches,
                           int repetitions, int expectedTrainingWeight, int realTrainingWeight) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.exercise = exercise;
        this.approaches = approaches;
        this.repetitions = repetitions;
        this.expectedTrainingWeight = expectedTrainingWeight;
        this.realTrainingWeight = realTrainingWeight;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getApproaches() {
        return approaches;
    }

    public void setApproaches(int approaches) {
        this.approaches = approaches;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getExpectedTrainingWeight() {
        return expectedTrainingWeight;
    }

    public void setExpectedTrainingWeight(int expectedTrainingWeight) {
        this.expectedTrainingWeight = expectedTrainingWeight;
    }

    public int getRealTrainingWeight() {
        return realTrainingWeight;
    }

    public void setRealTrainingWeight(int realTrainingWeight) {
        this.realTrainingWeight = realTrainingWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingsResult that = (TrainingsResult) o;

        if (id != that.id) return false;
        if (approaches != that.approaches) return false;
        if (repetitions != that.repetitions) return false;
        if (expectedTrainingWeight != that.expectedTrainingWeight) return false;
        if (realTrainingWeight != that.realTrainingWeight) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        return exercise != null ? exercise.equals(that.exercise) : that.exercise == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
        result = 31 * result + approaches;
        result = 31 * result + repetitions;
        result = 31 * result + expectedTrainingWeight;
        result = 31 * result + realTrainingWeight;
        return result;
    }

    @Override
    public String toString() {
        return "TrainingsResult{" +
                "id=" + id +
                ", date=" + date +
                ", client=" + client +
                ", exercise=" + exercise +
                ", approaches=" + approaches +
                ", repetitions=" + repetitions +
                ", expectedTrainingWeight=" + expectedTrainingWeight +
                ", realTrainingWeight=" + realTrainingWeight +
                '}';
    }
}
