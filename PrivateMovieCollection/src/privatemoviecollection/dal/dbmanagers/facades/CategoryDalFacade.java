/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.dal.dbmanagers.facades;

import java.util.List;
import privatemoviecollection.be.Category;

/**
 *
 * @author Bruger
 */
public interface CategoryDalFacade
{

    /**
     * creates a new category and adds it to the database
     *
     * @param category the category to be added
     * @return true if the category was added
     */
    public boolean createCategory(Category category);

    /**
     * Reads all categories from the database
     *
     * @return a List of categories
     */
    public List<Category> readAllCategories();

    /**
     * updates a category variable
     *
     * @param category the category to be updated
     * @return true if the category was updated
     */
    public boolean updateCategory(Category category);

    /**
     * Deletes a category from the database
     *
     * @param category the category to be deleted
     * @return true if the category was deleted
     */
    public boolean deleteCategory(Category category);
}
