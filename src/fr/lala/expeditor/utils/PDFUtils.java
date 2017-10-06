package fr.lala.expeditor.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Customer;
import fr.lala.expeditor.models.Order;



public class PDFUtils {
	static Document document ;
    static int boxWeight = 0 ;
    static String codeString;
	
    public static String createDeliveryNote(Order order, String realPath) throws IOException, DocumentException {
        Document document = new Document();
        String nomFichier = "order"+order.getId()+".pdf" ;
        String path = realPath+nomFichier ;
        codeString = "Order NC"+order.getId()+"-"+order.getCustomer().getName()+"-"+order.getCustomer().getZipCode()+"-"+order.getCustomer().getCity();
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
     * Mï¿½thode fournissant l'affichage de l'expï¿½diteur
     * @param canvas
     */
    public static void setSenderBlock(PdfContentByte canvas){
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("EXPEDITEUR"), 50, 800, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Notre SA"), 50, 780, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("123 rue du Poney qui Hurle"), 50, 760, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("75000 Paris"), 50, 740, 0);
    }
    
    /**
     * Permet d'ajouter le bloc destinataire au PDF, crï¿½e une nouvelle ligne pour chaque String de la liste en paramï¿½tres/
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
     * Ajoute la ligne nï¿½ de commande
     * @param numFacture
     * @param canvas
     */
    private static void setOrderInfo(Order order, PdfContentByte canvas) {
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Commande n° "+order.getId()), 50, 670, 0);
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
        //Gï¿½nï¿½ration a partir du tableau : 
        i=i-30;
        for (Article c : data.getListArticles()) {
            boxWeight += (c.getWeight()*c.getQuantity());
        	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getId())), 50, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(c.getLabel()), 150, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getWeight())), 350, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getQuantity())), 480, i, 0);
            i = i-20 ;
		}
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("__________________________________________________________________________"), 50, i, 0);
        i = i-40;
        int weightKg = boxWeight/1000 ;
        int weightG = boxWeight%1000;
        String display = String.valueOf(weightKg) + ","+String.valueOf(weightG)+" Kg";
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("POIDS COLIS : "+ display), 420, 670, 0);
        
    }
    


}
