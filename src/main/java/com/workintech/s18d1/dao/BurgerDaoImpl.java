package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BurgerDaoImpl implements BurgerDao{
    private EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public List<Burger> findAll() {

       TypedQuery<Burger> foundAll= entityManager.createQuery("SELECT B FROM Burger b",Burger.class);

        return foundAll.getResultList();
    }

    @Override
    public Burger findById(long id) {
       Burger burger= entityManager.find(Burger.class,id);
        if(burger==null){
            throw new BurgerException("Burger with given id is not exist : "+id, HttpStatus.NOT_FOUND);
        }
            return burger;

    }

    @Transactional
    @Override
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }

    @Transactional
    @Override
    public Burger remove(long id) {
       Burger foundBurger= findById(id);
       entityManager.merge(foundBurger);
        return foundBurger;
    }

    @Override
    public List<Burger> findByPrice(Double price) {
      TypedQuery<Burger> foundListQuery=  entityManager.createQuery("SELECT b FROM Burger WHERE=b.price> :price ORDER BY b.price desc", Burger.class);
      foundListQuery.setParameter("price",price);
        return foundListQuery.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
       TypedQuery<Burger> query= entityManager.createQuery("SELECT b FROM Burger b where b.breadType= :breadType ORDER BY b.name desc", Burger.class);
        query.setParameter("breadType",breadType);
        return query.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {

      TypedQuery<Burger> query=  entityManager.createQuery("SELECT b FROM Burger b WHERE b.contents like CONCAT('%',:content,'%') ORDER BY b.name", Burger.class);
     query.setParameter("Content",content);
     return query.getResultList();
    }
}
