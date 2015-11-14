package test.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="MENU")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="MENU_SEQ", allocationSize=1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="MENU_ID")
    private List<Dish> dishes;

    @Column(name = "DATE", columnDefinition="DATE NOT NULL")
    private Date date;

    public Menu(Long id, Restaurant restaurant, List<Dish> dishes, Date date) {
        this.id = id;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.date = date;
    }

    public Menu() {
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public static class Builder {
        private Long id;
        private Restaurant restaurant;
        private List<Dish> dishes;
        private Date date;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setRestaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
            return this;
        }

        public Builder setDishes(List<Dish> dishes) {
            this.dishes = dishes;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Menu build() {
            Menu menu = new Menu();
            menu.id = this.id;
            menu.restaurant = this.restaurant;
            menu.dishes = this.dishes;
            menu.date = this.date;
            return menu;
        }
    }

}
