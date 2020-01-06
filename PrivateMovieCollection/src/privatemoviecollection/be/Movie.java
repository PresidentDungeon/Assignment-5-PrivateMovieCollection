/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.be;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Bruger
 */
public class Movie
{

    private int id;
    private String title;
    private int length;
    private int year;
    private String filePath;
    private Date lastView;
    private Rating rating;
    private ArrayList<Category> categories;

    public Movie(String title, int length, int year, String filePath, Rating rating, ArrayList<Category> categories)
    {
        this.title = title;
        this.length = length;
        this.year = year;
        this.filePath = filePath;
        this.rating = rating;
        this.categories = categories;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public Date getLastView()
    {
        return lastView;
    }

    public void setLastView(Date lastView)
    {
        this.lastView = lastView;
    }

    public Rating getRating()
    {
        return rating;
    }

    public void setRating(Rating rating)
    {
        this.rating = rating;
    }

    public ArrayList<Category> getCategories()
    {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories)
    {
        this.categories = categories;
    }

}