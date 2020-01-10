/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.dal.dbmanagers.dbdao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import privatemoviecollection.be.Category;
import privatemoviecollection.dal.DBAccess.DBSettings;
import privatemoviecollection.dal.dbmanagers.facades.CategoryDalFacade;

/**
 *
 * @author Bruger
 */
public class CategoryDBDAO implements CategoryDalFacade
{
    private DBSettings dbs;
    
    public CategoryDBDAO() {
        try {
            dbs = new DBSettings();
        } catch (IOException e) {

        }
    }

    /**
     * Creates the specified category in the database.
     *
     * @param category
     * @return True if creation performed, else false
     */
    @Override
    public boolean createCategory(Category category)
    {
        try (Connection con = dbs.getConnection()) {
            String sql = "INSERT INTO Categories (Name) VALUES (?);";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, category.getName());

            int updatedRows = stmt.executeUpdate();

            return updatedRows > 0;

        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Retrieves all categories in the database and puts them in a list.
     *
     * @return List of all categories
     */
    @Override
    public List<Category> readAllCategories()
    {
        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Categories;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");

                Category c = new Category(name);
                c.setId(id);
                categories.add(c);
            }
            return categories;
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Updates a category with matching id in the database.
     *
     * @param category the category to be updated
     * @return True if update performed, else false
     */
    @Override
    public boolean updateCategory(Category category)
    {
        try (Connection con = dbs.getConnection()) {
            String sql = "UPDATE Categories SET name = ? WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Deletes a category with matching id from the database.
     *
     * @param category the category to be deleted
     * @return True if deletion performed, else false
     */
    @Override
    public boolean deleteCategory(Category category)
    {
        try (Connection con = dbs.getConnection()) {
            String sql = "DELETE FROM Categories WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, category.getId());
            stmt.executeUpdate();
            sql = "DELETE FROM CatMovies WHERE CategoryID = ?;";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, category.getId());
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
