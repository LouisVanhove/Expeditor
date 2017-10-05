package fr.lala.expeditor.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Customer;
import fr.lala.expeditor.models.Order;



public class PDFUtils {
	static Document document ;
	

	
    public static String createDeliveryNote(Order order, String realPath) throws IOException, DocumentException {
        Document document = new Document();
        String nomFichier = "order"+order.getId()+".pdf" ;
        String path = realPath+nomFichier ;
        System.out.println("Chemin absolu sur le serveur : "+path);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        
        setSenderBlock(canvas);
        setReceiverBlock(order.getCustomer(), canvas);
        setOrderInfo(order, canvas);
        setOrderDetail(order, canvas);

        document.close();
        return nomFichier ;
    }
    
	/**
     * Méthode fournissant l'affichage de l'expéditeur
     * @param canvas
     */
    public static void setSenderBlock(PdfContentByte canvas){
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("EXPEDITEUR"), 50, 800, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Notre SA"), 50, 780, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("123 rue du Poney qui Hurle"), 50, 760, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("75000 Paris"), 50, 740, 0);
    }
    
    /**
     * Permet d'ajouter le bloc destinataire au PDF, crée une nouvelle ligne pour chaque String de la liste en paramètres/
     * @param data
     * @param canvas
     */
    public static void setReceiverBlock(Customer customer, PdfContentByte canvas){
    	int i = 800 ;
    	int x = 420 ;
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("DESTINATAIRE"), x, i, 0);
		i = i-20;
	
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(customer.getName()), x, i, 0);
		i = i-20;
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(customer.getAddress()), x, i, 0);
		i = i-20;
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(customer.getZipCode() + " " + customer.getCity()), x, i, 0);
    }
    
    /**
     * Ajoute la ligne n° de commande
     * @param numFacture
     * @param canvas
     */
    private static void setOrderInfo(Order order, PdfContentByte canvas) {
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Commande n° "+sixDigitsOrderNumber(order)), 50, 670, 0);	
	}
    


	/**
     * Permet d'afficher la liste des articles dans le tableau
     * @param data
     * @param canvas
     */
    public static void setOrderDetail(Order data, PdfContentByte canvas){
    	int i = 630 ;
    	//En tete
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("REF"), 50, i, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("DESIGNATION"), 150, i, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("POIDS"), 350, i, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("QUANTITE"), 480, i, 0);
        i=i-20;
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("__________________________________________________________________________"), 50, i, 0);
        //Génération a partir du tableau : 
        i=i-30;
        for (Article c : data.getListArticles()) {
        	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getId())), 50, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(c.getLabel()), 150, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getWeight())), 350, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getQuantity())), 480, i, 0);
            i = i-20 ;
		}
        
    }
    
    /**
     * Méthode en charger de transformer l'id de la commande 
     * en cnuméro de commande suffisamment long pour le code barre
     * 
     * @param order
     * @return
     */
    private static String sixDigitsOrderNumber(Order order) {
		String result = "1" ;
    	int zeroToAdd = 0 ;
		int id = order.getId();
		
		if (id < 10) 
			zeroToAdd = 4 ;
		else if (id < 100) 
			zeroToAdd = 3 ;
		else if (id < 1000) 
			zeroToAdd = 2 ;
		else if (id < 10000) 
			zeroToAdd = 1 ;
		else if (id < 100000) 
			zeroToAdd = 0 ;
		else
			result = String.valueOf(id);
	
		if(zeroToAdd > 0){
			for (int i = 0 ;  i < zeroToAdd; i++){
				result = result +"0";
			}
		}
		
		return result;
		

	}

}
