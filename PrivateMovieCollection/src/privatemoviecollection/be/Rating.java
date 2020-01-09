/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.be;

/**
 *
 * @author Bruger
 */
public class Rating
{

    private int userRating;
    private float IMDBRating;

    public int getUserRating()
    {
        return userRating;
    }

    public void setUserRating(int userRating)
    {
        this.userRating = userRating;
    }

    public float getIMDBRating()
    {
        return IMDBRating;
    }

    public void setIMDBRating(float IMDBRating)
    {
        this.IMDBRating = IMDBRating;
    }

    @Override
    public String toString()
    {
        return "Rating{" + "userRating=" + userRating + ", IMDBRating=" + IMDBRating + '}';
    }

    
}
