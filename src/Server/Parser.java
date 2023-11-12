package src.Server;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import src.Server.Carte.*;

public class Parser {
    public static void parseCarteXml(ArrayList<Carta> mappa, ArrayList<Imprevisto> imprev,
            ArrayList<Probabilita> prob)
            throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("src/Server/Data/carte.xml");
        Element root = document.getDocumentElement();
        NodeList place = root.getElementsByTagName("Place");
        NodeList imprevisti = root.getElementsByTagName("Imprevisti");
        NodeList probabilità = root.getElementsByTagName("Probabilita");

        for (int j = 0; j < place.getLength(); j++) {
            Element e = (Element) place.item(j);
            NodeList caselle = e.getElementsByTagName("Casella");
            for (int i = 0; i < caselle.getLength(); i++) {
                e = (Element) caselle.item(i);
                String ID = e.getAttribute("ID");
                String nome = e.getElementsByTagName("Nome").item(0).getTextContent();
                int prezzo = Integer.parseInt(e.getElementsByTagName("PrezzoAcquisto").item(0).getTextContent());
                if (ID.split("#")[0].equals("PL")) {
                    int gruppo = Integer.parseInt(e.getElementsByTagName("Gruppo").item(0).getTextContent());
                    int rendita[] = new int[6];
                    rendita[0] = Integer.parseInt(e.getElementsByTagName("Terreno").item(0).getTextContent());
                    rendita[1] = Integer.parseInt(e.getElementsByTagName("Uno").item(0).getTextContent());
                    rendita[2] = Integer.parseInt(e.getElementsByTagName("Due").item(0).getTextContent());
                    rendita[3] = Integer.parseInt(e.getElementsByTagName("Tre").item(0).getTextContent());
                    rendita[4] = Integer.parseInt(e.getElementsByTagName("Quattro").item(0).getTextContent());
                    rendita[5] = Integer.parseInt(e.getElementsByTagName("Albergo").item(0).getTextContent());
                    int prezzoCasa = Integer.parseInt(e.getElementsByTagName("PrezzoCasa").item(0).getTextContent());
                    int ipoteca = Integer.parseInt(e.getElementsByTagName("Ipoteca").item(0).getTextContent());
                    mappa.add(new Casella(ID, nome, prezzo, gruppo, rendita, prezzoCasa, ipoteca));
                } else if (ID.split("#")[0].equals("R")) {
                    int rendita[] = new int[4];
                    rendita[0] = Integer.parseInt(e.getElementsByTagName("Uno").item(0).getTextContent());
                    rendita[1] = Integer.parseInt(e.getElementsByTagName("Due").item(0).getTextContent());
                    rendita[2] = Integer.parseInt(e.getElementsByTagName("Tre").item(0).getTextContent());
                    rendita[3] = Integer.parseInt(e.getElementsByTagName("Quattro").item(0).getTextContent());
                    int ipoteca = Integer.parseInt(e.getElementsByTagName("Ipoteca").item(0).getTextContent());
                    mappa.add(new Railroad(ID, nome, prezzo, rendita, ipoteca));
                } else if (ID.split("#")[0].equals("S")) {
                    int moltiplicatori[] = new int[2];
                    moltiplicatori[0] = Integer.parseInt(e.getElementsByTagName("MoltUno").item(0).getTextContent());
                    moltiplicatori[1] = Integer.parseInt(e.getElementsByTagName("MoltDue").item(0).getTextContent());
                    int ipoteca = Integer.parseInt(e.getElementsByTagName("Ipoteca").item(0).getTextContent());
                    mappa.add(new Societa(ID, nome, prezzo, moltiplicatori, ipoteca));
                } else {
                    mappa.add(new Carta(ID, nome, prezzo));
                }
            }
        }

        // TODO: parsare imprevisti e probabilita
    }
}