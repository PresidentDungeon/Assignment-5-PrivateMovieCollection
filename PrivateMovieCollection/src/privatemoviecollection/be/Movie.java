/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.be;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javafx.util.Duration;

/**
 *Getters and Setters for the Movie class
 * 
 * @author Bruger
 */
public class Movie
{

    private int id;
    private String title;
    private int seconds;
    private int year;
    private String filePath;
    private String IMDbLink;
    private LocalDate lastView;
    private Rating rating;
    private ArrayList<Category> categories;
    private String summaryText;
    private String imageLink;

    public Movie(String title, int seconds, int year, String filePath)
    {
        this.title = title;
        this.seconds = seconds;
        this.filePath = filePath;
        this.year = year;
        this.categories = new ArrayList<>();
        this.rating = new Rating();
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

    public int getSeconds()
    {
        return seconds;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
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

        public LocalDate getLastView()
    {
        return lastView;
    }

    public void setLastView(LocalDate lastView)
    {
        this.lastView = lastView;
    }

    public String getIMDbLink()
    {
        return IMDbLink;
    }

    public void setIMDbLink(String IMDbLink)
    {
        this.IMDbLink = IMDbLink;
    }

    public String getSummaryText()
    {
        return summaryText;
    }

    public void setSummaryText(String summaryText)
    {
        this.summaryText = summaryText;
    }

    public String getImageLink()
    {
        return imageLink;
    }

    public void setImageLink(String imageLink)
    {
        this.imageLink = imageLink;
    }

    /**
     * Transforms the seconds int variable into a String to better reflect the duration of
     * the movie.
     *
     * @return String containing the formatted duration
     */
    public String formatSeconds()
    {
        Duration duration = Duration.seconds(getSeconds());
        String durationString = String.format("%02d:%02d:%02d",
                (int) duration.toHours() % 60,
                (int) duration.toMinutes() % 60,
                (int) duration.toSeconds() % 60);
        return durationString;
    }
    
    /**
     * Formats the categories associated to the movie to a string split by a comma.
     * @return the string of all categories split by comma
     */
    public String formatCategories()
    {
        ArrayList<Category> movieCategories = getCategories();
        String allCategories = "";
        int loopPosition = 1;

        if (!movieCategories.isEmpty())
        {
            for (Category category : movieCategories)
            {
                allCategories += category.getName();

                if (loopPosition != movieCategories.size())
                {
                    allCategories += ", ";
                }
                loopPosition++;
            }
        } else
        {
            allCategories = "";
        }
        return allCategories;
    }
    
    @Override
    public String toString()
    {
        return "\n" + title + " (" + year + ")\nPersonal rating: " + getRating().getUserRating();
    }
    
    /**
     * Checks when a movie was last viewed, or when it was added to the database and never viewed
     * 
     * @return long with the amount of days between the two dates that are being compared
     */
    public Long compareLastView()
    {
        LocalDate p = LocalDate.now();
        LocalDate d = getLastView();
        long days = ChronoUnit.DAYS.between(d, p);
        
        return days;
    }

    @Override
    public boolean equals(Object obj) {
        Movie movie = (Movie) obj;
        return this.id == movie.id;
    }
    
    
    
    
    
    
    
    
    public static void main(String[] args) throws IOException
    {

//        File file = new File("Movie\\Nature short clip video.mp4");
//        Desktop.getDesktop().open(file);
//        LocalDate p = LocalDate.now();
//        LocalDate d = LocalDate.of(getLastView);
//        long days = ChronoUnit.DAYS.between(d, p);
        
        
                
                

    }

}
