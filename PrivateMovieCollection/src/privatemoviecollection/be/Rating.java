/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.be;

/**
 *Getters and Setters for the Rating class
 * 
 * @author Bruger
 */
public class Rating
{

    private int userRating;
    private double IMDBRating;

    public int getUserRating()
    {
        return userRating;
    }

    public void setUserRating(int userRating)
    {
        this.userRating = userRating;
    }

    public double getIMDBRating()
    {
        return IMDBRating;
    }

    public void setIMDBRating(double IMDBRating)
    {
        this.IMDBRating = IMDBRating;
    }

    @Override
    public String toString()
    {
        return "Rating{" + "userRating = " + userRating + ", IMDBRating = " + IMDBRating + "}";
    }

    
}
