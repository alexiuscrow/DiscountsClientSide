package alexiuscrow.diploma.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexiuscrow on 21.04.2015.
 */
public class EntityFactory {
    private static EntityFactory factory = null;
    private static Shops shop = null;
    private static Discounts discount = null;
    private static List<Shops> lLocalityShop = null;
    private static List<Shops> lNearestShop = null;

    public static synchronized EntityFactory getInstance(){
        if (factory == null)
            factory = new EntityFactory();
        return factory;
    }

    public Shops getShop(){
        if (shop == null)
            shop = new Shops();
        return shop;
    }

    public Discounts getDiscount() {
        if (discount == null)
            discount = new Discounts();
        return discount;
    }

    public List<Shops> getLocalShopsList(){
        if (lLocalityShop == null)
            lLocalityShop = new ArrayList<Shops>();
        return lLocalityShop;
    }

    public List<Shops> getNearestShopsList(){
        if (lNearestShop == null)
            lNearestShop = new ArrayList<Shops>();
        return lNearestShop;
    }
}
