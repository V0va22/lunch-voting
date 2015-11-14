package test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import test.model.Restaurant;
import test.repositories.RestaurantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RestaurantFunctionalTests extends AbstractFunctionalTests{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void addRestaurantTest(){
        send("/restaurant", HttpMethod.POST, new Restaurant.Builder()
                .setName("test-restaurant")
                .build(),
                Object.class,
                ROLE.ADMIN
        );

        Iterable<Restaurant> restaurantIterator = restaurantRepository.findAll();
        List<Restaurant> restaurants =  StreamSupport.stream(restaurantIterator.spliterator(), false)
                .collect(Collectors.toList());
        Assert.assertEquals(4, restaurants.size());
        Optional<Restaurant> restaurant = restaurants.stream().filter(r -> "test-restaurant".equals(r.getName())).findFirst();
        Assert.assertTrue(restaurant.isPresent());
    }
}
