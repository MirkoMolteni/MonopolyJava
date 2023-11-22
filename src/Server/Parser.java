package src.Server;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import src.Server.Carte.*;

/**
 * La classe Parser è responsabile del parsing del file XML contenente i dati di
 * gioco e del popolamento delle strutture dati necessarie.
 */
public class Parser {

    /**
     * Parsa il file XML contenente le carte di gioco e popola gli array forniti con
     * i dati parsati.
     *
     * @param mappa  Un array di Casella objects rappresentanti le caselle del
     *               tabellone
     * @param imprev Un ArrayList di Imprevisto objects rappresentanti le carte
     *               imprevisto
     * @param prob   Un ArrayList di Probabilita objects rappresentanti le carte
     *               probabilità
     * @throws Exception Se si verifica un errore durante il parsing del file XML
     */
    public static void parseCarteXml(Carta[] mappa, ArrayList<Imprevisto> imprev,
            ArrayList<Probabilita> prob)
            throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("src/Data/carte.xml");
        Element root = document.getDocumentElement();
        NodeList place = root.getElementsByTagName("Place");
        NodeList imprevisti = root.getElementsByTagName("Imprevisti");
        NodeList probabilita = root.getElementsByTagName("Probabilita");

        // parse delle caselle
        for (int j = 0; j < place.getLength(); j++) {
            Element e = (Element) place.item(j);
            NodeList caselle = e.getElementsByTagName("Casella");
            for (int i = 0; i < caselle.getLength(); i++) {
                e = (Element) caselle.item(i);
                String ID = e.getAttribute("ID");
                String nome = e.getElementsByTagName("Nome").item(0).getTextContent();
                int prezzo = Integer.parseInt(e.getElementsByTagName("PrezzoAcquisto").item(0).getTextContent());
                int pos = Integer.parseInt(ID.split("#")[1]);
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
                    mappa[pos] = new Casella(ID, nome, prezzo, gruppo, rendita, prezzoCasa, ipoteca);
                } else if (ID.split("#")[0].equals("R")) {
                    int rendita[] = new int[4];
                    rendita[0] = Integer.parseInt(e.getElementsByTagName("Uno").item(0).getTextContent());
                    rendita[1] = Integer.parseInt(e.getElementsByTagName("Due").item(0).getTextContent());
                    rendita[2] = Integer.parseInt(e.getElementsByTagName("Tre").item(0).getTextContent());
                    rendita[3] = Integer.parseInt(e.getElementsByTagName("Quattro").item(0).getTextContent());
                    int ipoteca = Integer.parseInt(e.getElementsByTagName("Ipoteca").item(0).getTextContent());
                    mappa[pos] = new Railroad(ID, nome, prezzo, rendita, ipoteca);
                } else if (ID.split("#")[0].equals("S")) {
                    int moltiplicatori[] = new int[2];
                    moltiplicatori[0] = Integer.parseInt(e.getElementsByTagName("MoltUno").item(0).getTextContent());
                    moltiplicatori[1] = Integer.parseInt(e.getElementsByTagName("MoltDue").item(0).getTextContent());
                    int ipoteca = Integer.parseInt(e.getElementsByTagName("Ipoteca").item(0).getTextContent());
                    mappa[pos] = new Societa(ID, nome, prezzo, moltiplicatori, ipoteca);
                } else {
                    mappa[pos] = new Carta(ID, nome, prezzo);
                }
            }
        }

        // parse degli imprevisti
        for (int j = 0; j < imprevisti.getLength(); j++) {
            Element e = (Element) imprevisti.item(j);
            NodeList impr = e.getElementsByTagName("Carta");
            for (int i = 0; i < impr.getLength(); i++) {
                e = (Element) impr.item(i);
                String ID = e.getAttribute("ID");
                String nome = e.getElementsByTagName("Nome").item(0).getTextContent();
                int prezzo = Integer.parseInt(e.getElementsByTagName("Valore").item(0).getTextContent());
                int caso = Integer.parseInt(e.getElementsByTagName("Case").item(0).getTextContent());
                String value = e.getElementsByTagName("Value").item(0).getTextContent();
                imprev.add(new Imprevisto(ID, nome, prezzo, caso, value));
            }
        }

        // parse delle probabilità
        for (int j = 0; j < probabilita.getLength(); j++) {
            Element e = (Element) probabilita.item(j);
            NodeList probab = e.getElementsByTagName("Carta");
            for (int i = 0; i < probab.getLength(); i++) {
                e = (Element) probab.item(i);
                String ID = e.getAttribute("ID");
                String nome = e.getElementsByTagName("Nome").item(0).getTextContent();
                int prezzo = Integer.parseInt(e.getElementsByTagName("Valore").item(0).getTextContent());
                int caso = Integer.parseInt(e.getElementsByTagName("Case").item(0).getTextContent());
                String value = e.getElementsByTagName("Value").item(0).getTextContent();
                prob.add(new Probabilita(ID, nome, prezzo, caso, value));
            }
        }
    }
}
