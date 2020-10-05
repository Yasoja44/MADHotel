package rob.sample.authenticatorapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GuideTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    private  ManageGuideDetails manageGuideDetails;

    @Before
    public void setup(){
        manageGuideDetails = new ManageGuideDetails();
    }

    @Test
    public void testTotal(){
        //give right output
        Boolean result1 = manageGuideDetails.checkDes("A veteran");
        assertEquals(true,result1);

        Boolean result2 = manageGuideDetails.checkDes("none");
        assertEquals(false,result2);

        //give wrong output
        Boolean result3 = manageGuideDetails.checkDes("goodasdasdaqaweqweqwesdasdasd");
        assertEquals(false,result3);

        Boolean result4 = manageGuideDetails.checkDes("good");
        assertEquals(true,result4);


    }
}
