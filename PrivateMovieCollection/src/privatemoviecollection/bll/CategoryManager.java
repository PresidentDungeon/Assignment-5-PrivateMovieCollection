/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.dal.dbmanagers.dbdao.CategoryDBDAO;
import privatemoviecollection.dal.dbmanagers.facades.CategoryDalFacade;
import privatemoviecollection.dal.dbmanagers.mockmanagers.MockCategoryManager;

/**
 *
 * @author Bruger
 */
public class CategoryManager
{

    private final CategoryDalFacade categoryManager;

    public CategoryManager()
    {
        categoryManager = new CategoryDBDAO();
    }

    public List<Category> getAllCategories()
    {
        List<Category> sortedList = categoryManager.readAllCategories();
        sortedList.sort((c1, c2) -> {return c1.getName().compareToIgnoreCase(c2.getName());});
        return sortedList;
    }

   /**
     * Checks the category Id.If the category has an Id of zero, a new category will be
     * added to the database. If the category already exists in the database and has
     * an Id, the categories instance variables will be updated in the database.
     *
     * @param category the category to be added or updated.
     * @return true if the category was added or updated
     */
    public boolean saveCategory(Category category)
    {
        if (category.getId() == 0)
        {
            return categoryManager.createCategory(category);
        } else
        {
            return categoryManager.updateCategory(category);
        }
    }

    /**
     * Deletes the selected category from the database
     *
     * @param category The category to be deleted
     * @return true if the category was deleted
     */
    public boolean deleteCategory(Category category)
    {
        return categoryManager.deleteCategory(category);
    }
}
