package in.co.gorest.allusersinfo;

import in.co.gorest.testbase.TestBase;
import in.co.gorest.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class AllUsersCRUDTest extends TestBase {
    static String name = "Monica Singh" + TestUtils.getRandomValue();
    static String email = "mon.singh34@200ce.com" + TestUtils.getRandomValue();
    static String gender = "female";
    static String status = "active";
    static int userId;

    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test001() {
        userSteps.createUser(name,email,gender,status).statusCode(201).log().all();
    }

    @Title("Verify if the user was added to the application for name = Dexter Maniac")
    @Test
    public void test002() {
        name = "Dexter Maniac";
        HashMap<String, Object> userMap = userSteps.getUserInfoByFirstName(name);
        Assert.assertThat(userMap, hasValue(name));
        userId = (int) userMap.get("id");
        System.out.println(userId);
    }

    @Title("Update the user information and verify the updated information for ID = 11017")
    @Test
    public void test003() {
        name = "Dexter Maniac";
        gender="male";
        email="laboratory23@ace.com";
        status="active";
        userId = 11017;
        userSteps.updateUser(userId,name,email,gender,status).statusCode(200).log().body().body("name", equalTo(name), "email", equalTo(email));
    }

    @Title("Delete the user and verify if the user is deleted! for ID = 10960")
    @Test
    public void test004() {
        userId = 10960;
        userSteps.deleteUser(userId).statusCode(204).log().status();
        userSteps.getUserById(userId).statusCode(404).log().status();
    }


}
