package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.util.BurgerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/burger")
public class BurgerController {
    private final BurgerDao burgerDao;

    @Autowired
    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }



    @PostMapping
    public Burger save(@RequestBody Burger burger ){
        BurgerValidation.checkName(burger.getName());
        return burgerDao.save(burger);
    }

    @PutMapping
    public Burger burger(@RequestBody Burger burger){
        return burgerDao.update(burger);
    }

    @GetMapping
    public List<Burger> findAll(){
        return burgerDao.findAll();
    }
    @GetMapping("/{id}")
    public Burger getById(@PathVariable long id){
        return burgerDao.findById(id);
    }

    @GetMapping("/price/{price}")
    public List<Burger> findByPrice(@PathVariable double price){
        return burgerDao.findByPrice(price);
    }
    @GetMapping("/content/{content}")
    public List<Burger> findByPrice(@PathVariable String content) {
        return burgerDao.findByContent(content);
    }
    @GetMapping("/breadType/{breadType}")
    public List<Burger> findByBreadType(@PathVariable String breadType){
     BreadType bt =  BreadType.valueOf(breadType);//Stringi breadType çevirme
        return burgerDao.findByBreadType(bt);
    }
    @DeleteMapping("/{id}")
    public Burger findByBreadType(@PathVariable long id){
        return burgerDao.remove(id);
    }

}
