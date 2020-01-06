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
    private int IMDBRating;

    public Rating(int IMDBRating)
    {
        this.IMDBRating = IMDBRating;
    }

    public int getUserRating()
    {
        return userRating;
    }

    public void setUserRating(int userRating)
    {
        this.userRating = userRating;
    }

    public int getIMDBRating()
    {
        return IMDBRating;
    }

    public void setIMDBRating(int IMDBRating)
    {
        this.IMDBRating = IMDBRating;
    }

}
