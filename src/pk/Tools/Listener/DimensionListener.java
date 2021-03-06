package pk.Tools.Listener;


/**
 * Listener for DimansionChange
 * Whenever the dimensions are changed dimensionChanged() is called with param w,h as the new width and height
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public interface DimensionListener
{
   
    public void dimensionChanged(int w,int h);
}
