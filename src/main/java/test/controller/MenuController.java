package test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.model.Dish;
import test.model.Menu;
import test.repositories.MenuRepository;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuRepository menuRepository;

    /**
     * Add menu
     *
     * @param menu menu to be saved
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Menu menu){
        LOGGER.debug("Processing add request for menu");
        menuRepository.save(menu);

    }

    /**
     * update menu
     *
     * @param menu new version of menu
     * @param id menu id
     * @return not found status if menu does not exists otherwise no content status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Menu menu, @PathVariable("id")Long id){
        LOGGER.debug("Processing update request for menu with id {}", id);
        if (!menuRepository.exists(id)){
            LOGGER.error("Menu with id {} not exists", id);
            return ResponseEntity.notFound().build();
        }
        menuRepository.save(menu);
        return ResponseEntity.noContent().build();
    }
    /**
     * add dish to existing menu
     *
     * @param dish dish to be added
     * @param id menu id
     * @return not found status if menu does not exists otherwise no content status
     */
    @RequestMapping(value = "/{id}/addDish", method = RequestMethod.PUT)
    public ResponseEntity addDish(@RequestBody Dish dish, @PathVariable("id")Long id){
        LOGGER.debug("Processing addDish request for menu with id {}", id);
        Menu menu = menuRepository.findOne(id);
        if (menu == null){
            LOGGER.error("Menu with id {} not exists", id);
            return ResponseEntity.notFound().build();
        }
        menu.getDishes().add(dish);
        menuRepository.save(menu);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve all menus
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Iterable<Menu> findAll(){
        LOGGER.debug("Retrieving all menus");
        return menuRepository.findAll();
    }

}
