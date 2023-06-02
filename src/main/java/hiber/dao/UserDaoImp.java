package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user, Car car) {
      sessionFactory.getCurrentSession().save(user);
      sessionFactory.getCurrentSession().save(car);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User findUserById(String model, int series) {
      TypedQuery<User> query=sessionFactory.getCurrentSession()
              .createQuery("from User user where user.car.model = :m and user.car.series = :s");
      query.setParameter("m", model);
      query.setParameter("s", series);
      return query.setMaxResults(1).getSingleResult();
   }

}
