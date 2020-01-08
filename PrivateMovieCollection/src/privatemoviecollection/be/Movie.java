/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.be;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;

/**
 *
 * @author Bruger
 */
public class Movie
{

    private int id;
    private String title;
    private String director;
    private int seconds;
    private int year;
    private String filePath;
    private Date lastView;
    private Rating rating;
    private ArrayList<Category> categories;

    public Movie(String title, String director, int seconds, int year, String filePath, Rating rating, ArrayList<Category> categories)
    {
        this.title = title;
        this.director = director;
        this.seconds = seconds;
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

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }
    
        /**
     * Transforms the seconds int variable into a String.
     * Example: 130 becomes "02:10"
     * @return String with minutes/seconds in format ##.##
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

    @Override
    public String toString()
    {
        ArrayList<Category> movieCategories = this.getCategories();
        String allCategories = "";
        int loopPosition = 1;

        for (Category category : movieCategories)
        {
            allCategories += category.getName();

            if (loopPosition != movieCategories.size())
            {
                allCategories += ", ";
            }

            loopPosition++;

        }
        return title + ", Id: " + id + "- " + allCategories;
    }
    
    public static void main(String[] args) throws IOException {
        
        File file = new File("Movie\\y2mate.com - nature_short_clip_video_07m_bT5_OrU_1080p.mp4");
        Desktop.getDesktop().open(file);
    }

}

              //Getting rating with /10
              
//              
//                     String url = "https://www.imdb.com/title/tt8111088/";
//
//              Document document = Jsoup.connect(url).get();
//              
//              for (Element element : document.select("div.ratingValue"))
//              {
//                            System.out.println(element.select(".ratingValue").text());
//                  
////                            System.out.println(element.text());
//              }



              
                //Getting rating without /10
                
//                       String url = "https://www.imdb.com/title/tt5180504/";
//
//              Document document = Jsoup.connect(url).get();
//              
//              for (Element element : document.select("span[itemprop=ratingValue]"))
//              {
//                            System.out.println(element.text());
//
//              }

                //Getting the summary text
                
//              String url = "https://www.imdb.com/title/tt5180504/";
//
//              Document document = Jsoup.connect(url).get();
//              
//              for (Element element : document.select("div.summary_text"))
//              {
//                            System.out.println(element.text());
//
//              }
       
                //Getting the title
                
//              String url = "https://www.imdb.com/title/tt5180504/";
//
//              Document document = Jsoup.connect(url).get();
//              
//              for (Element element : document.select("h1"))
//              {
//                            System.out.println(element.text());
//
//              }

                //Getting director(s)
                
//              String url = "https://www.imdb.com/title/tt0944947/";

//              Document document = Jsoup.connect(url).get();
//              
//              Element creators = document.getElementsByClass("credit_summary_item").first();
//              Elements creatorName = creators.getElementsByTag("a");
//              
//              for (Element ele : creatorName)
//              {
//                  System.out.println(ele.text());
//              }

            //Save image link
            
//              String url = "https://www.imdb.com/title/tt5180504/";
//
//              Document document = Jsoup.connect(url).get();
//              
//              Elements divs = document.getElementsByClass("poster");
//              Element poster = divs.first();
//              Elements img = poster.getElementsByTag("img");
//              String imageLink = img.attr("src");
//              
//              System.out.println(imageLink);
   