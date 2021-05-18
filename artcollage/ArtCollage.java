/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage Flo2.jpeg
 *
 *  @author: Ashwin Anand aa2041 aa2041@scarletmail.rutgers.edu
 *
 *************************************************************************/

import java.awt.Color;

public class ArtCollage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename) 
    {

        // Step 1 
        collageDimension = 4;
        tileDimension = 100;

        // Step 2 
        original = new Picture(filename);

        // Step 3 
        collage = new Picture((tileDimension*collageDimension),(tileDimension * collageDimension));

        // Step 4
        for (int col = 0; col < (collageDimension*tileDimension); col++)
        {
            for (int row = 0; row < (collageDimension*tileDimension); row++)
            {
                int col1 = col * original.width() / (collageDimension*tileDimension);
                int row1 = row * original.height() / (collageDimension*tileDimension);
                Color color = original.get(col1, row1);
                collage.set(col, row, color);
            }
        }

    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename, int td, int cd) 
    {
    
        // Step 1 
        collageDimension = cd;
        tileDimension = td;

        // Step 2 
        original = new Picture(filename);

        // Step 3
        collage = new Picture((tileDimension*collageDimension),(tileDimension*collageDimension));
     
        // Step 4 
        for (int col = 0; col < (collageDimension*tileDimension); col++)
        {
            for (int row = 0; row < (collageDimension*tileDimension); row++)
            {
                int col1 = col * original.width() / (collageDimension*tileDimension);
                int row1 = row * original.height() / (collageDimension*tileDimension);
                Color color = original.get(col1, row1);
                collage.set(col, row, color);
            }
        }
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() 
    {
    
        return collageDimension;
    
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() 
    {
    
        return tileDimension;
    
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() 
    {
    
        return original;
    
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() 
    {
    
        return collage;
    
    }
    
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() 
    {

        original.show();

    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() 
    {
    
        collage.show();
    
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) 
    {
        
        // Make an input picture from filename using constructor of image from String filename
        Picture input = new Picture(filename);

        // Sets the height and width based off tile dimension
        int height = this.tileDimension;
        int width  = this.tileDimension;
        
        // Makes a picture of tile dimension's that will replace the tile with a new tile
        Picture scale = new Picture(width, height);
        
        // Uses 2 for loops to get a color from the input picture and fill out scale the replacement 
        // tile with new colors 
        for (int coll = 0; coll < width; coll++)
        {
            for (int ro = 0; ro < height; ro++)
            {
                int col1 = coll * input.width() / width;
                int row1 = ro * input.height() / height;
                Color color = input.get(col1,row1);
                scale.set(coll, ro, color);
            }
        }

        // makes input a downscaled version of what it was to a tile to replace the tile in a collage
        input=scale;

        // Uses 4 for loops to reset the collage with new tile inputs color which uses 
        // the get method from Picture class
        for (int col = 0; col < width; col++) 
        {
            for (int row = 0; row < height; row++) 
            {
                Color color = input.get(col, row);
                for (int i = collageCol; i < collageCol + 1; i++) 
                {
                    for (int j = collageRow; j < collageRow + 1; j++) 
                    {
                        this.collage.set(width*i + col, height*j + row, color);
                    }
                }
            }
        }    
    }
    
    /*
     * Makes a collage of tiles from original Picture
     * original will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () 
    {
        // initalizes the height and width variables with tile dimension
        int height = this.tileDimension;
        int width  = this.tileDimension;

        // makes a new picture based of tile dimension x tile dimension
        Picture scale = new Picture(width, height);

        // 2 for loops to receive the color from the original picture and set the same color and pixels 
        // into the new picture created called scale
        for (int coll = 0; coll < height; coll++)
        {
            for (int ro = 0; ro < width; ro++)
            {
                int col1 = coll * this.original.width() / width;
                int row1 = ro * this.original.height() / height;
                Color color = this.original.get(col1, row1);
                scale.set(coll, ro, color);
            }
        }

        // initalizes 2 new variables with collage dimension to later use to scale the minage to 
        // a certain width and height 
        int cDim1= this.collageDimension;
        int cDim2= this.collageDimension;

        // makes collage based off new dimensions
        collage = new Picture(cDim2 * scale.width(), cDim1* scale.height());

        // 4 for loops to go through both the tile and collage dimensions wise 
        // to get the color from an input pic to set the color and color in the pixel for the collage
        for (int col = 0; col < width; col++) 
        {
            for (int row = 0; row < height; row++) 
            {
                Color color = scale.get(col, row);
                for (int i = 0; i < cDim1; i++) 
                {
                    for (int j = 0; j < cDim2; j++) 
                    {
                        this.collage.set(height*j + col, width*i + row, color);
                    }
                }
            }
        } 

    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see CS111 Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) 
    {

        // gets the collage picture from get collage picture to use in this method
        Picture originPic = getCollagePicture();

        // initializes tile with tile's dimension
        int tileDim = getTileDimension();

        // for loop to go through origin picture and recolor pixels to a certain color based off 
        // input like freen or red or blue 
        for(int h = collageCol * tileDim; h < tileDim + (collageCol * tileDim); h++)
        {
            for (int x = collageRow * tileDim; x < tileDim + (collageRow * tileDim); x++)
            {
                Color color = originPic.get(h, x);
                int temp = 0;

                // if loop to recolor a tile to be red
                if (component.equals("red"))
                {
                    temp = color.getRed();
                    originPic.set(h, x, new Color(temp, 0, 0));
                }

                // if loop to recolor a tile to be green
                if (component.equals("green"))
                {
                    temp = color.getGreen();
                    originPic.set(h, x, new Color(0, temp, 0));
                }

                // if loop to recolor a tile to be blue
                if (component.equals("blue"))
                {
                    temp = color.getBlue();
                    originPic.set(h, x, new Color(0, 0, temp));
                }
                
            }
        }

        // sets the collage to be origin after color changes made
        collage = originPic;
    
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     * (see CS111 Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */

    public void grayscaleTile (int collageCol, int collageRow) 
    {
        // gets the collage picture from get collage picture to use in this method
        Picture originPic = getCollagePicture();

        // initializes tile with tile's dimension
        int tileDim = getTileDimension();

        // for loop to go through origin picture and recolor pixels to a gray color to make the picture fully gray
        for (int h = (collageCol * tileDim); h < (tileDim + (collageCol * tileDim)); h++)
        {
            for (int x = collageRow * tileDim; x < tileDim + (collageRow * tileDim); x++)
            {
                Color color = originPic.get(h, x);
                Color gray = Luminance.toGray(color);
                originPic.set(h, x, gray);
            }
        }

        // sets the collage to be origin after color changes made
        collage = originPic;
    }


    /*
     *
     *  Test client: use the examples given on the assignment description to test your ArtCollage
     */
    public static void main (String[] args) {
        //ArtCollage art = new ArtCollage(args[0]);
        ArtCollage art = new ArtCollage(args[0],200,2); 
        art.makeCollage();
        art.replaceTile(args[1],0,1);
        art.replaceTile(args[2],1,0);
        art.replaceTile(args[3],1,1);
        //art.replaceTile(args[4],2,1);
        //art.grayscaleTile(0,0);
        art.colorizeTile("green",0,0);
        art.showCollagePicture();
    }
}
