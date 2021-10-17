package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User user1 = new User("Harry", "Potter", "potter@hogwarts.com");
      User user2 = new User("Hermione", "Granger", "granger@hogwarts.com");
      User user3 = new User("Ron", "Weasley", "weasley@hogwarts.com");
      User user4 = new User("Remus", "Lupin", "lupin@hogwarts.com");

      Car car1 = new Car("Lightning", 2021);
      Car car2 = new Car("Nimbus", 1001);
      Car car3 = new Car("Cleanliness", 7);
      Car car4 = new Car("Comet", 290);

      userService.add(user1.setCar(car1).setUser(user1));
      userService.add(user2.setCar(car2).setUser(user2));
      userService.add(user3.setCar(car3).setUser(user3));
      userService.add(user4.setCar(car4).setUser(user4));

      // 1. Пользователи с машинами
      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
         System.out.println("1. _____________________________________________");
      }

      // 2. Выбрать пользователя, владеющего машиной (по ее модели и серии)
      System.out.println(userService.getUserByCar("Nimbus", 1001));
      System.out.println("2. _____________________________________________");

      // Нет такого пользователя с такой машиной
      try {
         User notFoundUser = userService.getUserByCar("Broom", 90);
      } catch (NoResultException e) {
         System.out.println("User not found");
         System.out.println("3. _____________________________________________");
      }

      context.close();
   }
}