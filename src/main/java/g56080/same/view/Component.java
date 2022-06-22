package g56080.same.view;

import java.util.stream.IntStream;

import g56080.same.Config;
import g56080.same.model.Board;
import g56080.same.model.GridCell;
import g56080.same.model.Model;
import g56080.same.util.Util;

/**
 * Terminal component that can be displayed to the screen.
 */
public abstract class Component{
    
    /**
     * Displays the component on the standard output.
     */
    public abstract void display();

    /**
     * Gets the description of the component.
     *
     * @return the component's description.
     */
    public abstract String getDescription();

    /**
     * The intro component.
     */
    public static class INTRO extends Component{
        
        @Override
        public void display(){
            System.out.println("Welcome into the Same Game !");
        }

        @Override
        public String getDescription(){
            return "Intro message";
        }
    }

    /**
     * The end component.
     */
    public static class END extends Component{
        
        @Override
        public void display(){
            System.out.println("End of the game. Goodbye !");
        }

        @Override
        public String getDescription(){
            return "End message";
        }
    }

    /**
     * The score component.
     */
    public static class SCORE extends Component{
        
        private final Model model;

        /**
         * Creates a new score component using the given model.
         *
         * @param model the model to be used to retrieve the score informations.
         */
        public SCORE(Model model){
            this.model = model;
        }

        @Override
        public void display(){
            System.out.printf("Score: %s - Current click score: %s - Remaining cells: %s\n", 
                    AnsiColor.colorText(model.getScore() + "", AnsiColor.BOLD), 
                    AnsiColor.colorText(model.getCurrentScore() + "", AnsiColor.BOLD),
                    AnsiColor.colorText(model.getBoard().countCells() + "", AnsiColor.BOLD));
        }
       
        @Override
        public String getDescription(){
            return "Actual score of the game";
        }
    }

    /**
     * The hcell board shape component. A board with horizontal lines around the board's cells.
     */
    @Displayable
    public static class HCELL extends BOARD{
        
        /**
         * Constructs a new hcell board shape component using the given model.
         *
         * @param model the model to be used
         */
        public HCELL(Model model){
            super(model);
        }

        @Override
        public void display(){
            System.out.println(makeBoard());
        }

        @Override
        protected String makeBoard(){
            StringBuilder builder = new StringBuilder();
            String HLine = " ".repeat(NByWidth) + " ".repeat(WHAroundBoard) + HOUTLINE_SYMBOL.repeat((NBxWidth + WHIndex) * board.getWidth() - WHIndex + 1) + "\n";
            
            builder.append(makeBoardHead(board.getWidth(), NBxWidth, NByWidth));
            for(int i = 0; i < board.getHeight(); i++){
                String VTlIndexes = String.format("%-" + NByWidth + "d" + " ".repeat(WHAroundBoard) + VOUTLINE_SYMBOL, i + 1);
                String VTrIndexes = String.format(VOUTLINE_SYMBOL + " ".repeat(WHAroundBoard) + "%" + NByWidth + "d", i + 1);
                builder.append(HLine);
                builder.append(VTlIndexes);
                for(int j = 0; j < board.getWidth(); j++){
                    GridCell cell = board.getCell(j, i);
                    AnsiColor color = cell.getColorConsole().getColor();
                    if(cell.isFilled()){
                        builder.append(AnsiColor.colorText(BOARD_SYMBOL, color, true) + " ".repeat(WHCell));
                    } else builder.append(" " + " ".repeat(WHCell));
                }
                builder.delete(builder.length() - WHCell, builder.length());
                builder.append(VTrIndexes + "\n");
            }
            builder.append(HLine);

            return builder.toString();
        }

        @Override
        protected String makeBoardHead(int bw, int nbxw, int nbyw){
            // the below formula represent the total number of characters needed for the string
            StringBuilder builder = new StringBuilder(nbyw + WHAroundBoard + (nbxw + WHIndex) * bw - WHIndex + 1);

            builder.append(" ".repeat(nbyw) + " ".repeat(WHAroundBoard) + " ");
            IntStream
                .iterate(1, i -> i <= bw, i -> i + 1)
                .forEach(i -> builder.append(String.format("%-" + nbxw + "d" + " ".repeat(WHIndex), i)));
            builder.delete(builder.length() - WHIndex, builder.length());
            builder.append('\n');

            return builder.toString();
        }

        @Override
        public String getDescription(){
            return "Board variation with outlines around cells horizontally";
        }
    }

    /**
     * The vcell board shape component. A board with vertical lines around the board's cells.
     */
    @Displayable
    public static class VCELL extends FCELL{
        
        /**
         * Constructs a new vcell board shape component using the given model.
         *
         * @param model the model to be used
         */
        public VCELL(Model model){
            super(model);
        }

        @Override
        public void display(){
            System.out.println(makeBoard());
        }

        @Override
        public String makeBoard(){
            StringBuilder builder = new StringBuilder();
            String HLine = " ".repeat(NByWidth) + " ".repeat(WHAroundBoard) + HOUTLINE_SYMBOL.repeat((NBxWidth + WHIndex + 2) * board.getWidth() - WHIndex - 1) + "\n";

            builder.append(makeBoardHead(board.getWidth(), NBxWidth, NByWidth));
            builder.append(HLine);
            for(int i = 0; i < board.getHeight(); i++){
                String VTlIndexes = String.format("%-" + NByWidth + "d" + " ".repeat(WHAroundBoard), i + 1);
                String VTrIndexes = String.format(" ".repeat(WHAroundBoard) + "%" + NByWidth + "d", i + 1);
                builder.append(VTlIndexes);
                for(int j = 0; j < board.getWidth(); j++){
                    GridCell cell = board.getCell(j, i);
                    AnsiColor color = cell.getColorConsole().getColor();
                    builder.append(VOUTLINE_SYMBOL);
                    if(cell.isFilled()){
                        builder.append(AnsiColor.colorText(BOARD_SYMBOL, color, true) + VOUTLINE_SYMBOL + " ".repeat(WHCell));
                    } else builder.append(" " + VOUTLINE_SYMBOL + " ".repeat(WHCell));
                }

                builder.delete(builder.length() - WHCell, builder.length());
                builder.append(VTrIndexes + "\n");
            }
            builder.append(HLine);

            return builder.toString();
        }

        @Override
        public String getDescription(){
            return "Board variation with outlines around cells vertically";
        }
    }

    /**
     * The fcell board shape component. A board with vertical and horizontal lines around cells.
     */
    @Displayable
    public static class FCELL extends BOARD{
        
        /**
         * Constructs a new fcell board shape component using the given model.
         *
         * @param model the model to be used
         */
        public FCELL(Model model){
            super(model);
        }
        
        @Override
        public void display(){
            System.out.println(makeBoard());
        }

        @Override
        protected String makeBoard(){
            StringBuilder builder = new StringBuilder();
            String HLine = " ".repeat(NByWidth) + " ".repeat(WHAroundBoard) + HOUTLINE_SYMBOL.repeat((NBxWidth + WHIndex + 2) * board.getWidth() - WHIndex - 1) + "\n";

            builder.append(makeBoardHead(board.getWidth(), NBxWidth, NByWidth));
            for(int i = 0; i < board.getHeight(); i++){
                String VTlIndexes = String.format("%-" + NByWidth + "d" + " ".repeat(WHAroundBoard), i + 1);
                String VTrIndexes = String.format(" ".repeat(WHAroundBoard) + "%" + NByWidth + "d", i + 1);
                builder.append(HLine);
                builder.append(VTlIndexes);
                for(int j = 0; j < board.getWidth(); j++){
                    GridCell cell = board.getCell(j, i);
                    AnsiColor color = cell.getColorConsole().getColor();
                    builder.append(VOUTLINE_SYMBOL);
                    if(cell.isFilled()){
                        builder.append(AnsiColor.colorText(BOARD_SYMBOL, color, true) + VOUTLINE_SYMBOL + " ".repeat(WHCell));
                    } else builder.append(" " + VOUTLINE_SYMBOL + " ".repeat(WHCell));
                }
                builder.delete(builder.length() - WHCell, builder.length());
                builder.append(VTrIndexes + "\n");
            }
            builder.append(HLine);

            return builder.toString();
        }

        @Override
        protected String makeBoardHead(int bw, int nbxw, int nbyw){
            // the below formula represent the total number of characters needed for the string
            StringBuilder builder = new StringBuilder(nbyw + WHAroundBoard + (nbxw + WHIndex) * bw - WHIndex + 1);

            builder.append(" ".repeat(nbyw) + " ".repeat(WHAroundBoard) + " ");
            IntStream
                .iterate(1, i -> i <= bw, i -> i + 1)
                .forEach(i -> builder.append(String.format("%-" + nbxw + "d" + " ".repeat(WHIndex) + "  ", i)));
            builder.delete(builder.length() - WHIndex, builder.length());
            builder.append('\n');

            return builder.toString();
        }

        @Override
        public String getDescription(){
            return "Board variation with outlines around cells vertically and horizontally";
        }
    }

    /**
     * The default board shape component.
     */
    @Displayable
    public static class BOARD extends Component{
        
        protected final Model model;
        protected final Board board;

        /**
         * White spaces around board and between indexes repsectively.
         **/
        protected final static int WHAroundBoard = 1, WHIndex = 2;

        /**
         * White spaces between cells, number width for column and row indexes respectively;
         **/
        protected final int WHCell, NBxWidth, NByWidth;

        protected final static String BOARD_SYMBOL = ((char) Config.DEFAULT_GRID_SYMBOL.getValue()) + "";
        protected final static String HOUTLINE_SYMBOL = "-", VOUTLINE_SYMBOL = "|";

        /**
         * Constructs a new default board shape component using the given model.
         *
         * @param model the model to be used
         */
        public BOARD(Model model){
            this.model = model;
            board = model.getBoard();
            NBxWidth = Util.digits(board.getWidth());
            NByWidth = Util.digits(board.getHeight());
            WHCell = NBxWidth + 1;
        }

        @Override
        public void display(){
            System.out.println(makeBoard());
        }

        protected String makeBoard(){
            StringBuilder builder = new StringBuilder(((NByWidth + WHAroundBoard) * 2 + (1 + WHIndex) * board.getWidth() + 1) * board.getHeight());

            builder.append(makeBoardHead(board.getWidth(), NBxWidth, NByWidth));
            for(int i = 0; i < board.getHeight(); i++){
                String VTlIndexes = String.format("%-" + NByWidth + "d" + " ".repeat(WHAroundBoard), i + 1);
                String VTrIndexes = String.format(" ".repeat(WHAroundBoard) + "%" + NByWidth + "d", i + 1);
                builder.append(VTlIndexes);
                for(int j = 0; j < board.getWidth(); j++){
                    GridCell cell = board.getCell(j, i);
                    AnsiColor color = cell.getColorConsole().getColor();
                    if(cell.isFilled()){
                        builder.append(AnsiColor.colorText(BOARD_SYMBOL, color, true) + " ".repeat(WHCell));
                    } else builder.append(" " + " ".repeat(WHCell));
                }

                builder.delete(builder.length() - WHCell, builder.length());
                builder.append(VTrIndexes + "\n");
            }

            return builder.toString();
        }

        protected String makeBoardHead(int bw, int nbxw, int nbyw){
            // the below formula represent the total number of characters needed for the string
            StringBuilder builder = new StringBuilder(nbyw + WHAroundBoard + (nbxw + WHIndex) * bw - WHIndex + 1);

            builder.append(" ".repeat(nbyw) + " ".repeat(WHAroundBoard));
            IntStream
                .iterate(1, i -> i <= bw, i -> i + 1)
                .forEach(i -> builder.append(String.format("%-" + nbxw + "d" + " ".repeat(WHIndex), i)));
            builder.delete(builder.length() - WHIndex, builder.length());
            builder.append('\n');

            return builder.toString();
        }

        @Override
        public String getDescription(){
            return "Classical board with top, left and right indexes";
        }
    }
}
