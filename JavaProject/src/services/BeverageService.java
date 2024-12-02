package services;

import dao.BeverageDAO;
import models.Beverage;

import java.util.List;

public class BeverageService {
    private BeverageDAO beverageDAO = new BeverageDAO();

    public List<Beverage> getAllBeverages() {
        return beverageDAO.getAllBeverages();
    }

    public List<Beverage> getBeveragesByCategoryName(String categoryName){
        return beverageDAO.getBeveragesByCategoryName(categoryName);
    }

    public void addBeverage(Beverage beverage) {
        beverageDAO.addBeverage(beverage);
    }
}
