import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class HippodromeTest {
    @Test
    public void TestNullInArg() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void TestNullInArgMess() {
        try {
            new Hippodrome(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    public void testEmptyListInArg() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void testEmptyListInArgMess() {
        try {
            new Hippodrome(new ArrayList<>());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void testEqualsListInArg() {
        List<Horse> horses = new ArrayList<>(Arrays.asList(new Horse[30]));
        Collections.fill(horses, new Horse("name", Math.random() * 3, Math.random()));

        assertEquals(horses, new Hippodrome(horses).getHorses());
    }

    @Test
    public void eachHorsWasCalledMove() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        new Hippodrome(horses).move();

        for (Horse horse : horses){
            verify(horse).move();
        }
    }

    @Test
    public void checkWinner(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            horses.add(new Horse("name", 0, 0));
        }
        Horse horse = new Horse("winner", 1, 1);
        horses.add(horse);

        assertEquals(horse, new Hippodrome(horses).getWinner());
    }

    @Test
    @Disabled
    @Timeout(value = 22)
    public void checkMainWork() throws Exception {
        Main.main(null);
    }
}
