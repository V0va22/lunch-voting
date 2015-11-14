package test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import test.model.Dish;
import test.model.Menu;
import test.repositories.MenuRepository;
import test.repositories.RestaurantRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

public class MenuFunctionalTests extends AbstractFunctionalTests{
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void addMenuTest(){
        Date now = new Date();
        send("/menu", HttpMethod.POST, new Menu.Builder()
                .setRestaurant(restaurantRepository.findOne(2L))
                .setDate(now)
                .build(),
                Object.class,
                ROLE.ADMIN
        );

        Iterator<Menu> menus = menuRepository.findByDate(now).iterator();
        Assert.assertTrue(menus.hasNext());
        Assert.assertEquals(2L, menus.next().getRestaurant().getId().longValue());
    }

    @Test
    public void addDishTest(){
        send("/menu/2/addDish", HttpMethod.PUT, new Dish.Builder()
                        .setName("McChicken")
                        .setPrice(new BigDecimal(12))
                        .build(),
                Object.class,
                ROLE.ADMIN
        );

        Menu menus = menuRepository.findOne(2L);
        Assert.assertEquals(1, menus.getDishes().size());
        Assert.assertEquals("McChicken", menus.getDishes().get(0).getName());
    }

    @Test (expected = HttpClientErrorException.class)
    public void addDishNegativeTest(){
        send("/menu/2/addDish", HttpMethod.PUT, new Dish.Builder()
                        .setName("McChicken")
                        .setPrice(new BigDecimal(12))
                        .build(),
                Object.class,
                ROLE.USER
        );
    }
}
