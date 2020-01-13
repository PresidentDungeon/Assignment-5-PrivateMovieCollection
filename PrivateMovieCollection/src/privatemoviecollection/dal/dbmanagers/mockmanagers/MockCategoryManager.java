/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.dal.dbmanagers.mockmanagers;

import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.dal.dbmanagers.facades.CategoryDalFacade;

/**
 *
 * @author Bruger
 */
public class MockCategoryManager implements CategoryDalFacade
{

    ArrayList<Category> categories = new ArrayList<>();

    public MockCategoryManager()
    {
        Category cat1 = new Category("Drama");
        Category cat2 = new Category("Action");
        Category cat3 = new Category("Thriller");
        Category cat4 = new Category("Comedy");

        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        categories.add(cat4);
        
        cat1.setId(1);
        cat2.setId(2);
        cat3.setId(3);
        cat4.setId(4);
    }

    /**
     * adds a category to a list of categories
     *
     * @param category the category that you want to add to the list
     * @return boolean to test if the category Was succesfully added
     */
    @Override
    public boolean createCategory(Category category)
    {
        if (category != null)
        {
            categories.add(category);
            return true;
        }
        return false;
    }

    /**
     * Returns the categories arraylist containing all the category mockdata objects.
     *
     * @return a list containing all the movie mockdata.
     */
    @Override
    public List<Category> readAllCategories()
    {
        return categories;
    }

    /**
     * Attempts to find a category in the list with similar id as the input category, then
     * sets the other parameters of the category in the list to that of the input category
     *
     * @return true if a category was updated, else false
     */
    @Override
    public boolean updateCategory(Category category)
    {
        for (Category c : categories)
        {
            if (c.getId() == category.getId())
            {
                c.setId(category.getId());
                c.setName(category.getName());
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the specified category from the list of categories.
     *
     * @return true if the category was removed, else false
     */
    @Override
    public boolean deleteCategory(Category category)
    {
        return categories.remove(category);
    }

}
