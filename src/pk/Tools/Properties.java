package pk.Tools;

/**
 * Stores properties of currently selected tools
 * @author Rahul B.
 * @version 0.1.
 */
public class Properties
{

    /**
     * Square Properties 
     */
    public  static class  SquareProp
    {
        final static int POS_BASED   =0; // Positon Based drawing
        final static int SPEED_BASED =1;//  Speed  Based drawing
        final static int NORMAL      =2;//  Normal drawing

        public static boolean filled = false;//filled property
        public static int selected   = 0;// selected drwing type
        public static int x,y;//x and y as initialized from the init(x,y) method of the tool
    }
    public  static class  EraserProp
    {
        public static int size = 18;//size of the eraser
    }
    public  static class  OvalProp
    {
        //Properties same as square
        final static int POS_BASED   =0;
        final static int SPEED_BASED =1;
        final static int NORMAL      =2;

        public static boolean filled = false;
        public static int selected;

        public static int x,y;
    }
    public  static class  MystifyProp
    {
        //x and y as initialzed by the init method of its Tool 
        public static int x  = 0;
        public static int y  = 0;

        public static boolean selected = false;
    }
    public  static class  PencilProp
    {
        // Type of line drawing methods
        final static int NORMAL      =0;
        final static int CALIGRAPHIC =1;
        final static int SPIKE       =2;
        final static int TANGLED     =3;
        final static int WAVY        =4;
        final static int RIBBON      =5;
        final static int SPLOSH      =6;
        final static int GRASS       =7;

        // Type of line drawing method selected
        public static int selected   =0;
    }
    public static class SelectionProp
    {
        public  static boolean selected = false;

        public static int x,y;//x and y as initialzed by the init method of its Tool 
    }
}
