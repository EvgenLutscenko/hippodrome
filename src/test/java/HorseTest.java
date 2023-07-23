import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HorseTest {
    @Test
    public void nullName(){
        assertThrows(IllegalArgumentException.class , () -> new Horse(null, 0, 0));
    }

    @Test
    public void nullNameMess(){
        try {
            new Horse(null, 0, 0);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    public void emptyStrName(String str){
        assertThrows(IllegalArgumentException.class, () -> new Horse(str, 0, 0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\t", "\n", "\r", "   "})
    public void emptyStrNameMess(String str){
        try {
            new Horse(str, 0, 0);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, -4})
    public void speedIsNegative(int speed){
        assertThrows(IllegalArgumentException.class, () -> new Horse("name", speed, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, -4})
    public void speedIsNegativeMess(int speed){
        try {
            new Horse("name", speed, 0);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, -4})
    public void distanceIsNegative(int distance){
        assertThrows(IllegalArgumentException.class, () -> new Horse("name", 0, distance));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, -4})
    public void distanceIsNegativeMess(int distance){
        try {
            new Horse("name", 0, distance);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getNameCheck() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("name", 0, 0);

        Field nameField = Horse.class.getDeclaredField("name");
        nameField.setAccessible(true);
        String name = (String) nameField.get(horse);

        assertEquals(name, horse.getName());
    }

    @Test
    public void getSpeedCheck() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("name", 1213213213, 0);

        Field speedField = Horse.class.getDeclaredField("speed");
        speedField.setAccessible(true);
        Double speed = (Double) speedField.get(horse);

        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void getDistanceReturnThirdParametrCheck() throws NoSuchFieldException, IllegalAccessException{
        Horse horse = new Horse("name", 1213213213, 0);

        Field speedField = Horse.class.getDeclaredField("distance");
        speedField.setAccessible(true);
        Double distance = (Double) speedField.get(horse);

        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void getDistanceReturnNull() {
        Horse horse = new Horse("name", 0);

        assertEquals(0, horse.getDistance());
    }

    @Test
    public void checkMoveCallsRandom(){
        try (MockedStatic<Horse> horseMockitoStatic = mockStatic(Horse.class)){
            new Horse("name", 1213213213, 1213213213).move();

            horseMockitoStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({"1, 1, ", "11.3, 5.8", "13.4, 8.7"})
    public void checkCorrectWorkMove(double distance, double speed ){
        try (MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)){
            Horse horse = new Horse("name", speed, distance);
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn((1 * (0.9 - 0.2) + 0.2));

            horse.move();

            assertEquals(horse.getDistance(), distance + (speed * (1 * (0.9 - 0.2) + 0.2)));

        }

    }

}




