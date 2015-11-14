package test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.model.Menu;
import test.model.Vote;
import test.repositories.MenuRepository;
import test.repositories.VoteRepository;
import test.utils.TimeUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/vote")
public class VoteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private TimeUtil timeUtil;

    /**
     * vote for particular menu
     *
     * @param menuId chosen menu
     * @return not found if menu does not exists, bed request status if too late to vote otherwise no content status
     */
    @RequestMapping(value = "/{menuId}", method = RequestMethod.POST)
    public ResponseEntity vote(@PathVariable Long menuId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.debug("Processing vote request for user {} ", user.getUsername());
        Menu menu = menuRepository.findOne(menuId);
        if (menu == null) {
            LOGGER.error("Menu with id {} not exists", menuId);
            return ResponseEntity.notFound().build();
        }
        if (!validateDate(menu.getDate())) {
            LOGGER.error("It is too late...");
            return ResponseEntity.badRequest().build();
        }
        voteRepository.save(new Vote.Builder().setMenu(menu).setUserName(user.getUsername()).build());
        LOGGER.debug("user {} voted for restaurant {}", user.getPassword(), menu.getRestaurant().getName());
        return ResponseEntity.noContent().build();
    }

    private boolean validateDate(Date date) {
        Calendar menuCalendar = timeUtil.getCalendar();
        menuCalendar.setTime(date);
        menuCalendar.set(Calendar.HOUR, 11);
        return menuCalendar.after(timeUtil.getCalendar());
    }

    /**
     * Generate report for particular date
     *
     * @param date date of report
     * @return report in json object format e.g.
     *  {
     *      "KFC": 55,
     *      "McDonalds": 2,
     *      ...
     *  }
     */
    @RequestMapping(value = "/report/{date}", method = RequestMethod.GET)
    public Map<String, Long> results(@PathVariable Long date) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("creating report for ", new Date(date).toString());
        }
        return StreamSupport.stream(voteRepository.findByDate(new Date(date)).spliterator(), false)
                .collect(Collectors.groupingBy(v -> v.getMenu().getRestaurant().getName(), Collectors.counting()));
    }
    /**
     * Retrieves all votes
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Vote> findAll() {
        LOGGER.debug("Retrieving all restaurants");
        return voteRepository.findAll();
    }
}
