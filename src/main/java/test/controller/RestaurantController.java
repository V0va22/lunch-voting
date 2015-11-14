package test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import test.model.Restaurant;
import test.repositories.RestaurantRepository;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);


    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Add restaurant
     *
     * @param restaurant to be added
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Restaurant restaurant){
        LOGGER.debug("Processing add request for restaurant");
        restaurantRepository.save(restaurant);
    }

    /**
     * retrieve all restaurants
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Iterable<Restaurant> findAll(){
        LOGGER.debug("Retrieving all restaurants");
        return restaurantRepository.findAll();
    }
}
