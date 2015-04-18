package alexiuscrow.diploma.Fragments;

/**
 * Created by Alexiuscrow on 18.04.2015.
 */
public class FragmentsFactory {
    private static FragmentsFactory factory = null;
    private static DiscountsFragment discountsFragment = null;
    private static ShopsFragment shopsFragment = null;
    private static CategoriesFragment categoriesFragment = null;
    private static FavoritesFragment favoritesFragment = null;
    private static SettingsFragment settingsFragment = null;

    public static synchronized FragmentsFactory getInstance(){
     if (factory == null) {
         factory = new FragmentsFactory();
     }
     return factory;
    }

    public DiscountsFragment getDiscountsFragment(){
        if (discountsFragment == null){
            discountsFragment = new DiscountsFragment();
        }
        return discountsFragment;
    }

    public ShopsFragment getShopsFragment(){
        if (shopsFragment == null){
            shopsFragment = new ShopsFragment();
        }
        return shopsFragment;
    }

    public CategoriesFragment getCategoriesFragment(){
        if (categoriesFragment == null){
            categoriesFragment = new CategoriesFragment();
        }
        return categoriesFragment;
    }

    public FavoritesFragment getFavoritesFragment(){
        if (favoritesFragment == null){
            favoritesFragment = new FavoritesFragment();
        }
        return favoritesFragment;
    }

    public SettingsFragment getSettingsFragment(){
        if (settingsFragment == null){
            settingsFragment = new SettingsFragment();
        }
        return settingsFragment;
    }

}
