package models;


import java.util.List;

public class FigureFactory {

    private int figureCounter;

    public Figure getFigure(List<String> instrunctionList) throws Exception {
        Figure figure;
        switch (instrunctionList.get(0)){
            case "line" -> figure=createLineFigure(instrunctionList);

            case "rectangle" -> figure=createRectangleFigure(instrunctionList);

            case "circle" -> figure=createCircleFigure(instrunctionList);

            default -> throw new Exception("not supported by the program.");
        }
        figure.setId(++figureCounter);
        return figure;
    }


    private CircleFigure createCircleFigure(List<String> instructionList){
        float cx= Float.parseFloat(instructionList.get(1));
        float cy= Float.parseFloat(instructionList.get(2));
        float radius= Float.parseFloat(instructionList.get(3));
        String fill;
        if(instructionList.size()==5)
            fill= instructionList.get(4);
        else
            fill="black";

        return new CircleFigure(cx,cy,radius,fill);
    }

    private LineFigure createLineFigure(List<String> instructionList){
        float x1= Float.parseFloat(instructionList.get(1));
        float y1= Float.parseFloat(instructionList.get(2));
        float x2= Float.parseFloat(instructionList.get(3));
        float y2= Float.parseFloat(instructionList.get(4));
        String stroke;
        if(instructionList.size()==6)
            stroke= instructionList.get(5);
        else
            stroke="black";

        return new LineFigure( x1,y1,x2,y2,stroke);
    }

    private RectangleFigure createRectangleFigure(List<String> instructionList){

        float x= Float.parseFloat(instructionList.get(1));
        float y= Float.parseFloat(instructionList.get(2));
        float height= Float.parseFloat(instructionList.get(3));
        float width= Float.parseFloat(instructionList.get(4));
        String fill;
        if(instructionList.size()==6)
            fill= instructionList.get(5);
        else
            fill="black";

        return new RectangleFigure( x,y,height,width,fill);
    }
}
