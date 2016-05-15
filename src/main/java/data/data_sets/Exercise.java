package data.data_sets;

/**
 * Created by victor on 15.05.16.
 */
public class Exercise {

    private static int ID;

    private long id;
    private String exerciseName;

    public Exercise() {

    }

    public Exercise(String exerciseName) {
        id = ++ID;
        this.exerciseName = exerciseName;
    }

    public Exercise(long id, String exerciseName) {
        this.id = id;
        this.exerciseName = exerciseName;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (id != exercise.id) return false;
        return exerciseName != null ? exerciseName.equals(exercise.exerciseName) : exercise.exerciseName == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (exerciseName != null ? exerciseName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", exerciseName='" + exerciseName + '\'' +
                '}';
    }
}
