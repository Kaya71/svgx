package cli;

import models.Figure;
import models.FigureFactory;

import java.io.File;
import java.util.*;

public class CommandLineInterface {

    private final SVGParser svgParser;
    private File file;
    private List<Figure> figures;
    private List<String> inputString;
    private List<String> instructions;
    private final FigureFactory factory;

    private final static Scanner scanner=new Scanner(System.in);



    public CommandLineInterface(){
        figures =new ArrayList<>();
        svgParser=new SVGParser();
        inputString=new ArrayList<>();
        instructions=new ArrayList<>();
        factory=new FigureFactory();
    }


    public void start() {
        boolean flag=true;
        while(flag) {
            System.out.print(">");

            String input= scanner.nextLine();

            if(!checkInput(input)) {
                continue;
            }


            try {
                switch (inputString.get(0)) {
                    case "exit" -> flag = false;
                    case "help" -> {
                        System.out.println("open <file>         Opens <file>.");
                        System.out.println("close               Closes currently opened file.");
                        System.out.println("save                Saves the currently open file.");
                        System.out.println("saveas <file>       saves the currently open file in <file>.");
                        System.out.println("help                Prints this information.");
                        System.out.println("exit                Exists the program.");
                        System.out.println("print               Prints all figures on screen.");
                        System.out.println("create              ");
                        System.out.println("erase <n>           ");
                        System.out.println("translate [<n>] <horizontal> <vertical> ");
                        System.out.println("within <option>                         ");

                    }
                    case "close" -> {
                        if(file==null){
                            System.out.println("There is  no currently opened file!");
                            continue;
                        }
                        System.out.println("File successfully closed " + file.getAbsolutePath());
                        figures.clear();
                        file = null;
                    }
                    case "save" ->{ svgParser.writeFile(figures,file);
                        if(file==null){
                            System.out.println("There is no currently opened file!");
                            continue;
                        }
                        System.out.println("File successfully saved. " + file.getAbsolutePath());
                    }
                    case "saveas" -> {
                        if(file==null){
                            System.out.println("There is  no currently opened file!");
                            continue;
                        }
                        String newFileDirectory=instructions.get(0);

                        if(!newFileDirectory.endsWith(".svg"))
                            newFileDirectory+=".svg";

                        file=new File(newFileDirectory);
                        svgParser.writeFile(figures,file);

                        System.out.println("File saved as " + file.getAbsolutePath());
                    }
                    case "open" -> {
                        if(file!=null){
                            System.out.println("There is currently opened file!");
                            continue;
                        }

                        String fileDirectory=instructions.get(0);
                        if(!fileDirectory.endsWith(".svg")) {
                            fileDirectory += ".svg";
                        }

                        file=new File(fileDirectory);
                        figures=new ArrayList<>();

                        if(file.exists()) {
                            figures=svgParser.readFile(file);
                            System.out.println("File successfully opened: " + fileDirectory);
                        }else {
                            System.out.println("File not found.");
                        }
                    }
                    case "print" -> {
                        if(file==null){
                            System.out.println("There is  no currently opened file!");
                            continue;
                        }
                        for(Figure figure:figures){
                            System.out.println(figure);
                        }
                    }
                    case "within" -> {
                        if(file==null){
                            System.out.println("There is  no currently opened file!");
                            continue;
                        }
                        Figure figureToSearch=factory.getFigure(instructions);
                        for(Figure figure:figures){
                            if(figureToSearch.areBoundsWithinFigure(figure)){
                                System.out.println(figure);
                            }
                        }
                    }
                    case "erase" -> {
                        if(file==null){
                            System.out.println("There is  no currently opened file!");
                            continue;
                        }
                        int idToErase= Integer.parseInt(instructions.get(0));
                        figures.removeIf(figure -> figure.getId() == idToErase);
                        System.out.println("Figure erased successfully!");
                    }
                    case "create" -> {
                        if(file==null){
                            System.out.println("There is  no currently opened file!");
                            continue;
                        }
                        Figure figure=factory.getFigure(instructions);
                        figure.setId(figures.size()+1);
                        figures.add(figure);
                        System.out.println("Figure successfully added!");
                    }
                    case "translate" -> {
                        if(file==null){
                            System.out.println("There is  no currently opened file!");
                            continue;
                        }
                        int idToChange;
                        float vertical;
                        float horizontal;
                        if(instructions.size()==3){
                            idToChange = Integer.parseInt(instructions.get(0));
                            vertical = Float.parseFloat(instructions.get(1));
                            horizontal = Float.parseFloat(instructions.get(2));
                        }else {
                            idToChange = 0;
                            vertical = Float.parseFloat(instructions.get(0));
                            horizontal = Float.parseFloat(instructions.get(1));
                        }
                        if(idToChange==0){
                            for(Figure figure:figures){
                                figure.translate(vertical,horizontal);
                            }
                            System.out.println("All figures were successfully translated.");
                        }else {
                            for(Figure figure:figures){
                                if(idToChange==figure.getId()){
                                    figure.translate(vertical,horizontal);
                                    System.out.println("Figure successfully translated.");
                                    break;
                                }
                            }
                        }
                    }
                    default -> System.out.println("Current operation not found!");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean checkInput(String input){
        if(input.equals(""))
            return false;

        String regex="\\s+";
        inputString = Arrays.asList(input.split(regex));

        if(inputString.isEmpty())
            return false;

        instructions = new ArrayList<>(inputString.subList(1,inputString.size()));

        return true;
    }

}
