package rob.sample.authenticatorapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class BookTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private  AddBookingDetails addBookingDetails;

    @Before
    public void setup(){
        addBookingDetails = new AddBookingDetails();
    }

    @Test
    public void testTotal(){
        //give right output
        Boolean result1 = addBookingDetails.checkNo("2","2");
        assertEquals(true,result1);
        Boolean result2 = addBookingDetails.checkNo("5","5");
        assertEquals(false,result2);
        Boolean result3 = addBookingDetails.checkNo("5","1");
        assertEquals(false,result3);
        Boolean result4 = addBookingDetails.checkNo("2","7");
        assertEquals(false,result4);

        //give wrong output
        Boolean result5 = addBookingDetails.checkNo("2","2");
        assertEquals(false,result5);
        Boolean result6 = addBookingDetails.checkNo("7","7");
        assertEquals(true,result5);
    }
}
