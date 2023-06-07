package cli;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.*;

import org.w3c.dom.svg.SVGDocument;
import models.CircleFigure;
import models.Figure;
import models.LineFigure;
import models.RectangleFigure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SVGParser {
    public List<Figure> readFile(File file) throws Exception {

        List<Figure> figures=new ArrayList<>();

        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

        SVGDocument svgDocument;
        try {
            svgDocument = (SVGDocument) factory.createDocument(file.toURI().toString());
        } catch (IOException e) {
            throw new Exception("Cannot open: "+file.getAbsolutePath());
        }

        // Traverse the SVG document and create Figure objects
        NodeList children = svgDocument.getRootElement().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element element) {
                Figure figure = createFigureFromSVGElement(element);
                if (figure != null) {
                    figure.setId(i+1);
                    figures.add(figure);
                }
            }
        }

        return figures;
    }

    private Figure createFigureFromSVGElement(Element element) {
        // Parse the SVG element and create a Figure object
        String tagName = element.getTagName();
        if (tagName.equalsIgnoreCase("rect")) {
            // Parse the rect attributes and create a RectFigure
            float x = Float.parseFloat(element.getAttribute("x"));
            float y = Float.parseFloat(element.getAttribute("y"));
            float width = Float.parseFloat(element.getAttribute("width"));
            float height = Float.parseFloat(element.getAttribute("height"));
            String fill = element.getAttribute("fill");

            return new RectangleFigure(x, y, height, width, fill);
        } else if (tagName.equalsIgnoreCase("circle")) {
            // Parse the circle attributes and create a CircleFigure
            float cx = Float.parseFloat(element.getAttribute("cx"));
            float cy = Float.parseFloat(element.getAttribute("cy"));
            float radius = Float.parseFloat(element.getAttribute("r"));
            String fill = element.getAttribute("fill");

            return new CircleFigure(cx, cy, radius, fill);
        }else if(tagName.equalsIgnoreCase("line")){
            // Parse the circle attributes and create a CircleFigure
            float x1 = Float.parseFloat(element.getAttribute("x1"));
            float y1 = Float.parseFloat(element.getAttribute("y1"));
            float x2 = Float.parseFloat(element.getAttribute("x2"));
            float y2 = Float.parseFloat(element.getAttribute("y2"));
            String stroke = element.getAttribute("stroke");

            return new LineFigure(x1, y1, x2, y2,stroke);
        }


        return null; // Return null for unsupported SVG elements
    }

    public void writeFile(List<Figure> figures ,File file) throws Exception {
        // Създаване на DOMImplementation и SVGDocument
        DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
        SVGDocument document = (SVGDocument) domImpl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);

        // Създаване на коренов елемент <svg>
        Element svgRoot = document.getDocumentElement();
        svgRoot.setAttributeNS(null, "width", "1000");
        svgRoot.setAttributeNS(null, "height", "1000");

        for(Figure figure:figures){
            Element figureElement = createSvgElementFromFigure(document, figure);
            svgRoot.appendChild(figureElement);
        }

        // Записване на SVG файла
        try {
            FileWriter writer = new FileWriter("output.svg");
            DOMUtilities.writeDocument(document, writer);
            writer.close();
        } catch (IOException e) {
            throw new Exception("Cannot open: "+file.getAbsolutePath());
        }
    }

    private Element createSvgElementFromFigure(Document document, Figure figure) {
        // Create an SVG element based on the figure type
        if (figure instanceof RectangleFigure) {
            return createRectSvgElement(document, (RectangleFigure) figure);
        } else if (figure instanceof CircleFigure) {
            return createCircleSvgElement(document, (CircleFigure) figure);
        }else if(figure instanceof LineFigure){
            return createLineSvgElement(document, (LineFigure) figure);
        }
        // Add conditions for other figure types if needed

        return null; // Return null for unsupported figure types
    }

    private Element createRectSvgElement(Document document, RectangleFigure rectangleFigure) {
        // Create a rect SVG element and set its attributes based on the rect figure
        Element rectElement = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
        rectElement.setAttributeNS(null, "x", String.valueOf(rectangleFigure.getX()));
        rectElement.setAttributeNS(null, "y", String.valueOf(rectangleFigure.getY()));
        rectElement.setAttributeNS(null, "width", String.valueOf(rectangleFigure.getWidth()));
        rectElement.setAttributeNS(null, "height", String.valueOf(rectangleFigure.getHeight()));
        rectElement.setAttributeNS(null, "fill", rectangleFigure.getFill());

        return rectElement;
    }

    private Element createCircleSvgElement(Document document, CircleFigure circleFigure) {
        // Create a circle SVG element and set its attributes based on the circle figure
        Element circleElement = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "circle");
        circleElement.setAttributeNS(null, "cx", String.valueOf(circleFigure.getCx()));
        circleElement.setAttributeNS(null, "cy", String.valueOf(circleFigure.getCy()));
        circleElement.setAttributeNS(null, "r", String.valueOf(circleFigure.getRadius()));
        circleElement.setAttributeNS(null, "fill", circleFigure.getFill());

        return circleElement;
    }

    private Element createLineSvgElement(Document document, LineFigure circleFigure) {
        // Create a circle SVG element and set its attributes based on the circle figure
        Element circleElement = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "line");
        circleElement.setAttributeNS(null, "x1", String.valueOf(circleFigure.getX1()));
        circleElement.setAttributeNS(null, "y1", String.valueOf(circleFigure.getY1()));
        circleElement.setAttributeNS(null, "x2", String.valueOf(circleFigure.getX2()));
        circleElement.setAttributeNS(null, "y2", String.valueOf(circleFigure.getY2()));
        circleElement.setAttributeNS(null, "stroke", circleFigure.getStroke());

        return circleElement;
    }
}
