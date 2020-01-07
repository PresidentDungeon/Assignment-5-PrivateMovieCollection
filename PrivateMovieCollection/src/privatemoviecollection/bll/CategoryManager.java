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
        return categoryManager.readAllCategories();
    }

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

    public boolean deleteCategory(Category category)
    {
        return categoryManager.deleteCategory(category);
    }
}
