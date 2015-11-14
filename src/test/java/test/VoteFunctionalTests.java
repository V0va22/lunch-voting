package test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import test.model.Vote;
import test.model.VotePK;
import test.repositories.MenuRepository;
import test.repositories.VoteRepository;
import test.utils.TimeUtil;
import test.utils.TimeUtilMock;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class VoteFunctionalTests extends AbstractFunctionalTests {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private TimeUtil timeUtil;

    @Test
    public void voteTest() {
        ((TimeUtilMock)timeUtil).setPast();
        send("/vote/2", HttpMethod.POST,
                Object.class,
                ROLE.USER
        );
        Vote vote = voteRepository.findOne(new VotePK("user", new Date(1447279200000L)));
        Assert.assertNotNull(vote);
        voteRepository.delete(vote.getId());
    }

    @Test (expected = HttpClientErrorException.class)
    public void voteTestWithWrongMenu() {
        send("/vote/999", HttpMethod.POST,
                Object.class,
                ROLE.USER
        );
    }

    @Test (expected = HttpClientErrorException.class)
    public void voteTestWhenTooLate() {
        ((TimeUtilMock)timeUtil).setFuture();
        send("/vote/2", HttpMethod.POST,
                Object.class,
                ROLE.USER
        );
    }

    @Test
    public void reportTest() {
        voteRepository.save(Arrays.asList(
                new Vote.Builder().setMenu(menuRepository.findOne(1L)).setUserName("admin").build(),
                new Vote.Builder().setMenu(menuRepository.findOne(2L)).setUserName("user").build()
        ));

        ResponseEntity<HashMap> response = send("/vote/report/1447279200000", HttpMethod.GET,
                HashMap.class,
                ROLE.USER
        );
        Assert.assertEquals(2, response.getBody().size());
    }
}
